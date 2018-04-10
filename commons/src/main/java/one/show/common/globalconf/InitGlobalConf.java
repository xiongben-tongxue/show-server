/**
 * 
 */
package one.show.common.globalconf;

import one.show.common.Constant;
import one.show.common.JacksonUtil;
import one.show.common.zk.BaseZookeeper;

import org.apache.zookeeper.WatchedEvent;

/**
 * @author Haliaeetus leucocephalus 2018年1月13日 下午9:37:09
 *
 */
public class InitGlobalConf extends BaseZookeeper{
	
	public static void main(String[] args) throws InterruptedException, Exception {
		InitGlobalConf conf = new InitGlobalConf();
		conf.connect();
		try {
			conf.delete(Constant.ZK_GLOBAL_CONFIG_PATH_HOME, -1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		GlobalConf dsConf = new GlobalConf();
		dsConf.setSelectorThreads(5);
		dsConf.setWorkerThreads(5);
		dsConf.setEnv(0);
		conf.createPersistentNode(Constant.ZK_GLOBAL_CONFIG_PATH_HOME, JacksonUtil.writeToJsonString(dsConf).getBytes());

		/*
		GlobalConf dsConf = new GlobalConf();
		List<String> list = new ArrayList<String>();
		list.add("http://112.124.113.141");
		list.add("http://115.29.226.146");
		list.add("http://112.124.100.169");
		dsConf.setUploadAddress(list);
		dsConf.setSelectorThreads(1000);
		dsConf.setWorkerThreads(5000);
		
		conf.createPersistentNode(Constant.ZK_GLOBAL_CONFIG_PATH_HOME, JacksonUtil.writeToJsonString(dsConf).getBytes());

	*/
		conf.close();
	}

	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#nodeChildrenChanged(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void nodeChildrenChanged(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#connected(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void connected(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}
    
}


