package one.show.search.service.impl;

import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import one.show.search.service.IndexService;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.common.Loader;
import one.show.common.exception.ServiceException;
import one.show.user.thrift.iface.UserServiceProxy;
import one.show.user.thrift.view.UserView;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

/**
 * @author Haliaeetus leucocephalus 2015年11月3日
 *
 */
@Component("indexService")
public class IndexServiceImpl implements IndexService {

	private static final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);

	@Autowired
	private UserServiceProxy.Iface userServiceClientProxy;

//	private int[] dbst = { 0, 50 };
//	private int[] dbnum = { 50, 50 };
	

	private int[] dbst = new int[2];
	private int[] dbnum = new int[2];

	@Override
	public void createUserIndex(String indexPath) throws ServiceException {

		String[] dbstArray = Loader.getInstance().getProps("dbst").split(",");
		
		dbst[0] = Integer.parseInt(dbstArray[0]);
		dbst[1] = Integer.parseInt(dbstArray[1]);
		
		String[] dbnumArray = Loader.getInstance().getProps("dbnum").split(",");
		
		dbnum[0] = Integer.parseInt(dbnumArray[0]);
		dbnum[1] = Integer.parseInt(dbnumArray[1]);
		
		log.info("dbst = "+dbst[0]+","+dbst[1]+", dbnum="+dbnum[0]+","+dbnum[1]);
		
		Date date1 = new Date();
		Analyzer analyzer = new ComplexAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = null;
		try {
			Directory userDirectory = FSDirectory.open(Paths.get(indexPath));
			indexWriter = new IndexWriter(userDirectory, config);
			List<UserView> userList = null;
			for (int dbid = 0; dbid < 2; dbid++) {
				for (int i = dbst[dbid]; i < dbst[dbid] + dbnum[dbid]; i++) {
					if (dbid == 0) {
						userList = userServiceClientProxy.findAllUser0(i);
					}
					if (dbid == 1) {
						userList = userServiceClientProxy.findAllUser1(i);
					}
			
					for (UserView user : userList) {
						
						Document doc = new Document();
						doc.add(new StringField("uid", String.valueOf(user.getId()), Store.YES));
						TextField nickname = new TextField("nickName", user.getNickname(), Store.YES);
						nickname.setBoost(80);
						doc.add(nickname);
						
						
						TextField popularNo = new TextField("popularNo", String.valueOf(user.getPopularNo()), Store.YES);
						popularNo.setBoost(20);
						doc.add(popularNo);

						indexWriter.addDocument(doc);
						log.info("add doc uid:"+String.valueOf(user.getId())+" nick:"+user.getNickname()+" pid:"+user.getPopularNo());
					}
				}
			}
			indexWriter.commit();
			indexWriter.close();
			// analyzer.close();//single instance
			userDirectory.close();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		Date date2 = new Date();
		log.info("创建索引耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
	}

	

}
