package one.show.common.server;

import java.lang.reflect.Constructor;

import one.show.common.zk.BaseZookeeper;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftServerProxy extends BaseZookeeper{

	public static final Logger logger = LoggerFactory.getLogger(ThriftServerProxy.class);
	
	private int port;// 端口
	private String serviceProxy;
	private Object serviceProxyImpl; 
	
	private int selectorThreads = 100;
	private int workerThreads = 1000;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start() {
		new Thread() {
			public void run() {
				try {
					/*
					TServerSocket serverTransport = new TServerSocket(getPort());
					Class Processor = Class.forName(getServiceProxy()+ "$Processor");
					
					Class Iface = Class.forName(getServiceProxy()+ "$Iface");
					
					Constructor con = Processor.getConstructor(Iface);
					
					TProcessor processor = (TProcessor) con.newInstance(getServiceProxyImpl());
					
					Factory protFactory = new TBinaryProtocol.Factory(true,true);
					
					TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
					args.maxWorkerThreads(1000);
					args.protocolFactory(protFactory);

					args.processor(processor);
					TServer server = new TThreadPoolServer(args);
					logger.info("Starting server on port " + getPort() + " ...");
					server.serve();
					*/
					
			        
					TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(getPort());
					Class Processor = Class.forName(getServiceProxy()+ "$Processor");
					
					Class Iface = Class.forName(getServiceProxy()+ "$Iface");
					
					Constructor con = Processor.getConstructor(Iface);
					
					TProcessor processor = (TProcessor) con.newInstance(getServiceProxyImpl());
					
					TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(serverTransport);
					args.selectorThreads(getSelectorThreads());
					args.workerThreads(getWorkerThreads());
					args.protocolFactory(new TCompactProtocol.Factory());
					args.transportFactory(new TFramedTransport.Factory());
					args.maxReadBufferBytes = 1024*1024;
					args.processor(processor);
					TServer server = new TThreadedSelectorServer(args);
					logger.info("Starting server on port " + getPort() + " , selectorThreads="+getSelectorThreads()+", workerThreads="+getWorkerThreads());
					server.serve();
					
				} catch (TTransportException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	
	
	public int getSelectorThreads() {
		return selectorThreads;
	}



	public void setSelectorThreads(int selectorThreads) {
		this.selectorThreads = selectorThreads;
	}



	public int getWorkerThreads() {
		return workerThreads;
	}



	public void setWorkerThreads(int workerThreads) {
		this.workerThreads = workerThreads;
	}



	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServiceProxy() {
		return serviceProxy;
	}

	public void setServiceProxy(String serviceProxy) {
		this.serviceProxy = serviceProxy;
	}

	public Object getServiceProxyImpl() {
		return serviceProxyImpl;
	}

	public void setServiceProxyImpl(Object serviceProxyImpl) {
		this.serviceProxyImpl = serviceProxyImpl;
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
