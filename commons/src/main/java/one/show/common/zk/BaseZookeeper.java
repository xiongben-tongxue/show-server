package one.show.common.zk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import one.show.common.Loader;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseZookeeper
{
 
	private static final Logger log = LoggerFactory.getLogger(BaseZookeeper.class);
	  
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    
    private ZooKeeper zooKeeper = null;
    
	private static  String host = Loader.getInstance().getProps("zookeeper.server");
    
	private static  int sessionTimeOut = 5000;
    
    private Stat stat = new Stat();
    
    GenericObjectPool<ZooKeeper> pool = null;
    
    public abstract void nodeChildrenChanged(WatchedEvent event);
    
    public abstract void connected(WatchedEvent event);
    
    public void nodeDeleted(WatchedEvent event){
    	log.info(event.getPath() +" deleted");
    }
    
    public void sessionExpired(){
    	try {
        	this.connect();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    	
    }
    
 
    /**
     * 连接zookeeper
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect() throws Exception,
            InterruptedException
    {
    	
    	zooKeeper = new ZooKeeper(host, sessionTimeOut, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
   			
   				if (event.getState() == KeeperState.SyncConnected){
   					if (event.getType() == EventType.None){
   						log.info("zookeeper connected notice.");
   						countDownLatch.countDown();
   						connected(event);
  						
  						
  					}else if (event.getType() == EventType.NodeChildrenChanged){
  						log.info("zookeeper node changed notice : "+event.getPath());
  						nodeChildrenChanged(event);
  						
  					}
  					else if (event.getType() == EventType.NodeDeleted){
  						log.info("zookeeper node deleted notice : "+event.getPath());
  						nodeDeleted(event);
  					}
   		            
   		        }else if (event.getState() == KeeperState.Disconnected){
   		        	log.error("zookeeper disconnected notice.");
   		        }else if (event.getState() == KeeperState.Expired) {
   		        	sessionExpired();
   		        	log.info("session expired . reconnect...");
   		        }
   			}});
    	
    	countDownLatch.await();
 
    }
    
    
    
   public void setData(String path, byte[] data, Integer version) throws KeeperException, InterruptedException{
	   zooKeeper.setData(path, data, version);
   }
    /**
     * 获取节点数据
     * @param path
     * @param watch
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    public String  getData(String path, boolean watch) throws KeeperException, InterruptedException, UnsupportedEncodingException{
    	 byte[] data = zooKeeper.getData(path, watch, stat);  
    	 return new String(data, "utf-8");
    }
   /**
    * 获取子节点 		
    * @param path
    * @param watch
    * @return
    * @throws KeeperException
    * @throws InterruptedException
    */
   public List<String>  getChildren(String path, boolean watch) throws KeeperException, InterruptedException{
	   List<String> subList = zooKeeper.getChildren(path, watch);
	   return subList;
   }
   
   /**
    * 创建永久编号节点
    * @param path
    * @param data
    * @throws KeeperException
    * @throws InterruptedException
    */
   public String createPersistentSeqNode(String path, byte[] data) throws KeeperException, InterruptedException{
   	if (zooKeeper.exists(path, null) == null){
			String createdPath = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			return createdPath;
		}else{
			return path;
		}
   }
    		
    /**
     * 创建永久节点
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createPersistentNode(String path, byte[] data) throws KeeperException, InterruptedException{
    	if (zooKeeper.exists(path, null) == null){
			String createdPath = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			return createdPath;
		}else{
			return path;
		}
    }
    
    /**
     * 创建临时节点
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createEphemeralNode(String path, byte[] data) throws KeeperException, InterruptedException{
    	if (zooKeeper.exists(path, null) == null){
			String createdPath = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			return createdPath;
		}else{
			return path;
		}
    }
    
    /**
     * 创建临时编号节点
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createEphemeralSeqNode(String path, byte[] data) throws KeeperException, InterruptedException{
    	String createdPath = zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		return createdPath;
    }
    
    
    public boolean exists(String path, boolean watch) throws KeeperException, InterruptedException{
    	if (zooKeeper.exists(path, watch) == null){ 
    		return false;
    	}else{
    		return true;
    	}
    		
    }
    
    
    public void close() throws InterruptedException {
    	if (zooKeeper != null){
    		try {
    			zooKeeper.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
    }
    
    
    public void delete(String path, Integer version) throws InterruptedException, KeeperException{
    	zooKeeper.delete(path, version);
    }
    
    

 
}
