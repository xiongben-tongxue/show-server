package one.show.common.client.thrift;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import one.show.common.Constant;
import one.show.common.IPUtil;
import one.show.common.JacksonUtil;
import one.show.common.globalconf.GlobalConf;
import one.show.common.zk.BaseZookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
	@author Haliaeetus leucocephalus 18-2-24
	基于zookeeper-watcher机制,获取最新地址
 */
public class DynamicAddressProvider extends BaseZookeeper implements ThriftServerAddressProvider, InitializingBean {


	private static final Logger logger = LoggerFactory.getLogger(DynamicAddressProvider.class);
	  

    
    private String configPath;
    
    private int port;
    
    private  GlobalConf globalConf = null;
    
    //用来保存当前provider所接触过的地址记录

    private final List<InetSocketAddress> container = new ArrayList<InetSocketAddress>();

    private Queue<InetSocketAddress> inner = new LinkedList<InetSocketAddress>();
    
    private Object lock = new Object();
    
    private static final Integer DEFAULT_PRIORITY = 1;

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public void setPort(int port) {
		this.port = port;
	}
    
    
  

	@Override
    public void afterPropertiesSet() throws Exception {
		connect();
    }
    
    
    private void updateServerList() throws Exception {  
    	String confData = getData(Constant.ZK_GLOBAL_CONFIG_PATH_HOME, false);
		globalConf = JacksonUtil.readJsonToObject(GlobalConf.class, confData);
		
        List<InetSocketAddress> current = new ArrayList<InetSocketAddress>();
        // 获取并监听groupNode的子节点变化  
        // watch参数为true, 表示监听子节点变化事件.   
        // 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册  
        List<String> subList = getChildren(Constant.ZK_SERVICE_PATH_HOME+"/"+configPath, true);  
        for (String subNode : subList) {  
            // 获取每个子节点下关联的server地址  
        	
        	if (globalConf != null && globalConf.getEnv() == 0){
        		//测试环境之获取本机ip
        		if (subNode.indexOf(IPUtil.getLocalIP()) >= 0){
           		 	String data = getData(Constant.ZK_SERVICE_PATH_HOME+"/"+configPath + "/" + subNode, false);  
                    InetSocketAddress  inetSocketAddress = new InetSocketAddress(data, port);
                    current.add(inetSocketAddress);  
        		}
        	}else{
        		String data = getData(Constant.ZK_SERVICE_PATH_HOME+"/"+configPath + "/" + subNode, false);  
                InetSocketAddress  inetSocketAddress = new InetSocketAddress(data, port);
                current.add(inetSocketAddress);  
        	}
           
        }  
  
        Collections.shuffle(current);
        
        
        synchronized (lock) {
            container.clear();
            container.addAll(current);
            inner.clear();
            inner.addAll(current);
			
		}
        
        // 替换server列表  
        logger.info(Constant.ZK_SERVICE_PATH_HOME+"/" + configPath + " . server list updated: " + container);  
    }  
  


    @Override
    public List<InetSocketAddress> getAll() {
        return Collections.unmodifiableList(container);
    }

    @Override
    public synchronized InetSocketAddress selector() {
        if (inner.isEmpty()) {
        	if(!container.isEmpty()){
        		inner.addAll(container);
        	}
        }
        return inner.poll();//null
    }


    @Override
    public void close() {
        
    }

	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#nodeChildrenChanged(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void nodeChildrenChanged(WatchedEvent event) {

		if ((Constant.ZK_SERVICE_PATH_HOME+"/"+configPath).equals(event.getPath())){
			 try {  
                  updateServerList();  
              } catch (Exception e) {  
                  e.printStackTrace();  
              }  
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#connected(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void connected(WatchedEvent event) {

		try {  
            updateServerList();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}
}
