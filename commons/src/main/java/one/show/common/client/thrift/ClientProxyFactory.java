
package one.show.common.client.thrift;

import one.show.common.client.thrift.ThriftClientPoolFactory.PoolOperationCallBack;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Haliaeetus leucocephalus  2016年7月1日 上午12:07:18
 *
 * 
 */

public class ClientProxyFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ClientProxyFactory.class);
	
	private String service;

	private Integer maxActive = 32;// 最大活跃连接数
	
	private Integer maxIdle = 10;// 最大空闲连接数

	// //ms,default 3 min,链接空闲时间
	// -1,关闭空闲检测
	private Integer idleTime = 180000;
	// 连接超时时间，毫秒
	private Integer timeout = 2000;
	
	private Integer protocol = 1;
	
	//熔断器配置,熔断时间1秒
	protected int executionTimeoutInMilliseconds = 1000;
	
	protected int circuitBreakerRequestVolumeThreshold = 5000;
	
	

	public int getExecutionTimeoutInMilliseconds() {
		return executionTimeoutInMilliseconds;
	}
	public void setExecutionTimeoutInMilliseconds(int executionTimeoutInMilliseconds) {
		this.executionTimeoutInMilliseconds = executionTimeoutInMilliseconds;
	}
	public int getCircuitBreakerRequestVolumeThreshold() {
		return circuitBreakerRequestVolumeThreshold;
	}
	public void setCircuitBreakerRequestVolumeThreshold(
			int circuitBreakerRequestVolumeThreshold) {
		this.circuitBreakerRequestVolumeThreshold = circuitBreakerRequestVolumeThreshold;
	}
	public Integer getProtocol() {
		return protocol;
	}
	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}
	

	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Integer getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getIdleTime() {
		return idleTime;
	}
	public void setIdleTime(Integer idleTime) {
		this.idleTime = idleTime;
	}



	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	private PoolOperationCallBack callback = new PoolOperationCallBack() {

		@Override
		public void make(TServiceClient client) {
			log.info("create connection["+client.toString()+"]");

		}

		@Override
		public void destroy(TServiceClient client) {
			log.info("destroy connection["+client.toString()+"]");

		}
	};
	

	public GenericObjectPool<TServiceClient> getPool(ClassLoader classLoader, ThriftServerAddressProvider addressProvider) throws Exception{
		// 加载Client.Factory类
		Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader
				.loadClass(getService() + "$Client$Factory");
		
		TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();
		
		ThriftClientPoolFactory clientPool = new ThriftClientPoolFactory(addressProvider, clientFactory, callback, getTimeout(), getProtocol());
		
		GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
		poolConfig.maxActive = getMaxActive();
		poolConfig.maxIdle = getMaxIdle();
		poolConfig.minIdle = 5;	
		//指明若在对象池空时调用borrowObject方法的行为被设定成等待，最多等待多少毫秒。如果等待时间超过了这个数值，则会抛出一个java.util.NoSuchElementException异常。如果这个值不是正数，表示无限期等待
		poolConfig.maxWait = 2000;
		//设定在进行后台对象清理时，视休眠时间超过了多少毫秒的对象为过期。过期的对象将被回收。如果这个值不是正数，那么对休眠时间没有特别的约束
		poolConfig.minEvictableIdleTimeMillis = getIdleTime();
		//设定间隔每过多少毫秒进行一次后台对象清理的行动。如果这个值不是正数，则实际上不会进行后台对象清理
		poolConfig.timeBetweenEvictionRunsMillis = getIdleTime() / 2L;
//				poolConfig.testOnBorrow = true;
		
		return new GenericObjectPool<TServiceClient>(clientPool, poolConfig);
	}


}

