package one.show.common.zk;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

import one.show.common.Loader;
import one.show.common.client.thrift.ThriftServerAddressProvider;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Haliaeetus leucocephalus 18-2-24
 * 连接池,zk
 *
 */

public class ZookeeperSessionFactory extends BasePoolableObjectFactory<ZooKeeper>{

	private static final Logger log = LoggerFactory.getLogger(ZookeeperSessionFactory.class);
	  
	private static  String host = Loader.getInstance().getProps("zookeeper.server");
	    
	private static  int sessionTimeOut = 5000;
	
	private ZooKeeper zooKeeper = null;
	

    @Override
    public ZooKeeper makeObject() throws Exception {
    	
    	
    	try {
			zooKeeper = new ZooKeeper(host, sessionTimeOut, new Watcher(){

				@Override
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					
				}});
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
    	
    	if (zooKeeper == null){
        	throw new NoSuchElementException("获取zooKeeper连接失败");
        }
    	
    	return zooKeeper;
    	 
    }

    @Override
    public void destroyObject(ZooKeeper zooKeeper) throws Exception {
    	if (zooKeeper != null){
    		zooKeeper.close();
		}
    }

    @Override
    public boolean validateObject(ZooKeeper zooKeeper) {
    	if (zooKeeper != null){
    		return true;
    	}else{
    		return false;
    	}
    }
    

}
