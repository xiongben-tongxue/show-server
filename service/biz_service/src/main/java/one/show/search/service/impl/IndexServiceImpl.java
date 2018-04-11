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


	@Override
	public void createUserIndex(String indexPath) throws ServiceException {

		
		Date date1 = new Date();
		Analyzer analyzer = new ComplexAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = null;
		try {
			Directory userDirectory = FSDirectory.open(Paths.get(indexPath));
			indexWriter = new IndexWriter(userDirectory, config);
			
			 int pageSize = 1000;
             int start = 0;
             while(true){
            	 	List<UserView> userList = userServiceClientProxy.findAllUserList(start, pageSize);

                     if (userList == null || userList.size() == 0){
                             break;
                     }else{
                             start += pageSize;
                             
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
