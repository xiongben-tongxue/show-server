package one.show.common.server;

import java.util.List;

import one.show.common.Constant;
import one.show.common.IPUtil;
import one.show.common.zk.BaseZookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public  class ServerStart extends BaseZookeeper{

	public static final Logger logger = LoggerFactory.getLogger(ThriftServerProxy.class);

	private String server = null;
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}


	
	public ClassPathXmlApplicationContext startServer(String server){
		
		// 启动SETUP SEERVER
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "applicationContext.xml" });
			
			
			List<ThriftServerProxy> list = ((List<ThriftServerProxy>) context
					.getBean("thriftServerList"));
			
			if (list != null && list.size() > 0) {
				for (ThriftServerProxy userProxy : list) {
					userProxy.start();
				}
				
				//zk注册
				this.setServer(server);
				connect();
				
			}
			
			logger.info("Thrift Server监听接口启动。。。。。。。。。。。");
			
			return context;
		} catch (Exception e) {
			logger.error("Thrift Server监听接口启动错误", e);
			System.exit(-1);
		}
		return null;
		
		/*
		try {
			
			//取配置
			String shard_config = getData(Constant.ZK_SHARD_CONFIG_PATH_HOME+"/"+server+"_service/shard_config.xml", false);  
			
			
			String shard_datasource = getData(Constant.ZK_SHARD_CONFIG_PATH_HOME+"/"+server+"_service/shard_datasource.xml", false);  
			
			WPShardParser.init(shard_config, shard_datasource);
			logger.info("ShardConfig init [shard_config.xml、shard_datasource.xml]");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		*/
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
		
		
		String address =  IPUtil.getLocalIP();
		String createdPath = null;
			try {
				
				 if(!exists(Constant.ZK_SERVICE_PATH_HOME, false)){
		             // 创建根节点
		         	createPersistentNode(Constant.ZK_SERVICE_PATH_HOME, new byte[0]);
		         }
		         
		         if(!exists(Constant.ZK_SERVICE_PATH_HOME+ "/"+this.getServer(), false)){
		             // 创建根节点
		         	createPersistentNode(Constant.ZK_SERVICE_PATH_HOME + "/"+this.getServer(), new byte[0]);
		         }
				
		         
				String path = Constant.ZK_SERVICE_PATH_HOME+ "/"+this.getServer()+"/server_"+address;
				createdPath = createEphemeralNode(path, address.getBytes("utf-8"));
				logger.info("zookeeper 服务注册成功 : " + createdPath);
			} catch (Exception e) {
				logger.error("zookeeper 服务节点注册失败", e);
				System.exit(-1);
			} 
			
			
			
	}

}
