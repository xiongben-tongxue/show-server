package one.show.common.client.thrift;

import java.net.InetSocketAddress;
import java.util.NoSuchElementException;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Haliaeetus leucocephalus 18-2-24
 * 连接池,thrift-client for spring
 *
 */

public class ThriftClientPoolFactory extends BasePoolableObjectFactory<TServiceClient>{

	private static final Logger log = LoggerFactory.getLogger(ThriftClientPoolFactory.class);
	  
    private final ThriftServerAddressProvider addressProvider;
    
    private final TServiceClientFactory<TServiceClient> clientFactory;
    
    private PoolOperationCallBack callback;
    
    private int timeout;
    
    private int tp ;

    
    protected ThriftClientPoolFactory(ThriftServerAddressProvider addressProvider,TServiceClientFactory<TServiceClient> clientFactory,PoolOperationCallBack callback, Integer timeout, Integer protocol) throws Exception {
        this.addressProvider = addressProvider;
        this.clientFactory = clientFactory;
        this.callback = callback;
        this.timeout = timeout;
        this.tp = protocol;
    }



    @Override
    public TServiceClient makeObject() throws Exception {
        InetSocketAddress address = addressProvider.selector();
        if (address == null){
        	throw new NoSuchElementException("没用可用服务");
        }
        
        TTransport transport = null;
        TProtocol protocol = null;
        //指定传输方式和数据协议
        
        /*
        if (tp == 0){
        	//二进制数据协议
        	 transport = new THttpClient("http://" + address.getHostName() + ":" + address.getPort() + "/server.php");
             protocol = new TBinaryProtocol(transport);
        }else{
        	//帧传输，压缩数据协议
        	 transport = new TFramedTransport(new TSocket(address.getHostName(), address.getPort(), timeout), 1024*1024);
             protocol = new TCompactProtocol(transport);
        }
        */
        //帧传输，压缩数据协议
   	 	transport = new TFramedTransport(new TSocket(address.getHostName(), address.getPort(), timeout), 4*1024*1024);
        protocol = new TCompactProtocol(transport);

        TServiceClient client = this.clientFactory.getClient(protocol);
        transport.open();
        if(callback != null){
        	try{
        		callback.make(client);
        	}catch(Exception e){
        		//
        	}
        }
        return client;
    }

    @Override
    public void destroyObject(TServiceClient client) throws Exception {
    	if(callback != null){
    		try{
    			callback.destroy(client);
    		}catch(Exception e){
    			//
    		}
        }
    	TTransport transport = client.getInputProtocol().getTransport();
    	transport.close();
    }

    @Override
    public boolean validateObject(TServiceClient client) {
    	if (client == null){
    		return false;
    	}
    	TTransport transport = client.getInputProtocol().getTransport();
    	return transport.isOpen();
    }
    
    static interface PoolOperationCallBack {
    	//销毁client之前执行
    	void destroy(TServiceClient client);
    	//创建成功是执行
    	void make(TServiceClient client);
    }

}
