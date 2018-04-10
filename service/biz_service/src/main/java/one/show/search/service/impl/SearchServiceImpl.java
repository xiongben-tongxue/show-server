package one.show.search.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import one.show.search.service.SearchService;
import one.show.search.thrift.view.UserSearchView;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import one.show.common.exception.ServiceException;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

/**
 * @author Haliaeetus leucocephalus 2015年10月21日
 *
 */
@Component("searchService")
public class SearchServiceImpl implements SearchService {
	
	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

	private String USER_INDEX_DIR = "/data/search/user_index";

//	static int userSearchCount = 0;
	
	private static AtomicInteger userSearchCount = new AtomicInteger(0);
	
	static IndexWriter userIndexWriter;
	static Directory userDirectory;

	static DirectoryReader userReader = null;
	static DirectoryReader videoReader = null;
	IndexSearcher userSearcher = null;
	IndexSearcher videoSearcher = null;
	String[] warmupQuery = {"ShowCoin","广场","红人","美女","性感","约","诱惑","美腿","福利","黑丝","洗澡","胸","内衣","情趣","化妆"};

	public SearchServiceImpl() throws ServiceException, IOException, ParseException {
		userDirectory = FSDirectory.open(Paths.get(USER_INDEX_DIR));
		if(DirectoryReader.indexExists(userDirectory)){
			Analyzer analyzer = new ComplexAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			userIndexWriter = new IndexWriter(userDirectory, config);
			userReader = DirectoryReader.open(userIndexWriter,false);//用false因为buffer中无内容
			userSearcher = new IndexSearcher(userReader);
			for(String query:warmupQuery){
				searchUser(query, 0, 5);
			}
		}else{
			log.warn("index ["+USER_INDEX_DIR+"] not exists.");
		}

	}
	@Override
	public List<Long> searchUser(String keyword, int cursor, int count)
			throws ServiceException, ParseException, IOException {
		Date date1 = new Date();
		List<Long> idList = new ArrayList<Long>();
		
		Analyzer analyzer = new ComplexAnalyzer();
		QueryParser parser = new QueryParser("nickName", analyzer);
		
		BooleanQuery query = new BooleanQuery();
		
		Query pidQuery = new TermQuery(new Term("popularNo",keyword));
		query.add(pidQuery, Occur.SHOULD);
		
		try {
			Query nickQuery  = parser.parse(keyword);
			query.add(nickQuery, Occur.SHOULD);
			
			TopDocs result = null;
			ScoreDoc scoreDoc = null;
			int index = 0;
			while (index <= cursor) {
				result = userSearcher.searchAfter(scoreDoc, query, count);
				if (result.scoreDocs.length == 0) {
					break;
				}
				scoreDoc = result.scoreDocs[result.scoreDocs.length - 1];
				index += result.scoreDocs.length;
			}
			ScoreDoc[] hits = result.scoreDocs;
			log.info("Userquery: "+keyword+" cursor:"+cursor+" count:"+count);
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = null;
				hitDoc = userSearcher.doc(hits[i].doc);
				//String explain = userSearcher.explain(query, hits[i].doc).toString();
				
				idList.add(Long.parseLong(hitDoc.get("uid")));
				
				log.info("Result: uid "+hitDoc.get("uid")+", nickname: "+hitDoc.get("nickName")+", pid: "+hitDoc.get("popularNo")+" got hit!");
				//log.info(explain);
			}
			
			if(userSearchCount.incrementAndGet() > 600){
				if(userIndexWriter.hasUncommittedChanges()){//这一步就是一个提交，我给它在这里提交，其实可以在任何时候提交
					userIndexWriter.commit();
				}
				userReader.close();
				userReader = DirectoryReader.open(userIndexWriter,true);//这一步重启userReader防止频繁使用同一个directory
				userSearcher = new IndexSearcher(userReader);
				userSearchCount.set(0);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		Date date2 = new Date();
		log.info("Userquery["+userSearchCount.get()+"]: "+keyword+" 搜索耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
		return idList;
	}
	
	
	@Override
	public void deleteUser(Long uid) throws ServiceException, IOException {
		Term delTerm = new Term("uid", String.valueOf(uid));
		userIndexWriter.deleteDocuments(delTerm);
		DirectoryReader newReader = DirectoryReader.openIfChanged(userReader, userIndexWriter, true);//如果没有change 返回null
		if(newReader != null){
			userReader.close();
			userReader = newReader;
		}
		userSearcher = new IndexSearcher(userReader);
		log.info("delete user, uid : "+uid);
	}

	@Override
	public void insertUser(UserSearchView user) throws ServiceException, IOException {
		Document doc = createUserDocument(user);
		userIndexWriter.addDocument(doc);
		DirectoryReader newReader = DirectoryReader.openIfChanged(userReader, userIndexWriter, true);//如果没有change 返回null
		if(newReader != null){
			userReader.close();
			userReader = newReader;
		}
		userSearcher = new IndexSearcher(userReader);
		log.info("add user, uid : "+user.getUid()+", nick : "+user.getNickName());
	}


	@Override
	public void updateUser(UserSearchView user) throws ServiceException, IOException {
		deleteUser(user.getUid());
		insertUser(user);
		/*
		Term updateTerm = new Term("uid", String.valueOf(user.getUid()));
		StringField field = new StringField("nickName", user.getNickName(), Store.YES);
		userIndexWriter.updateDocValues(updateTerm, field);
		DirectoryReader newReader = DirectoryReader.openIfChanged(userReader, userIndexWriter, true);//如果没有change 返回null
		if(newReader != null){
			userReader.close();
			userReader = newReader;
		}
		userSearcher = new IndexSearcher(userReader);
		
		*/
		log.info("update user, uid : "+user.getUid()+", nick : "+user.getNickName());
	}

	
	private Document createUserDocument(UserSearchView user){
		Document doc = new Document();
		doc.add(new StringField("uid", String.valueOf(user.getUid()), Store.YES));
		
		TextField nickname = new TextField("nickName", user.getNickName(), Store.YES);
		nickname.setBoost(80);
		doc.add(nickname);
		
		TextField popularNo = new TextField("popularNo", String.valueOf(user.getPopularNo()), Store.YES);
		popularNo.setBoost(20);
		doc.add(popularNo);
		
		return doc;
	}
	
}
