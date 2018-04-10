
package one.show.common.zk;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Haliaeetus leucocephalus  2016年7月1日 上午12:07:18
 *
 * 
 */

public class ZookeeperPoolFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ZookeeperPoolFactory.class);
	

	private Integer maxActive = 180;// 最大活跃连接数

	// //ms,default 3 min,链接空闲时间
	// -1,关闭空闲检测
	private Integer idleTime = 180000;
	// 连接超时时间，毫秒
	private Integer timeout = 3000;
	
	private Integer protocol = 1;
	

	public Integer getProtocol() {
		return protocol;
	}
	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
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
	
	private GenericObjectPool<ZooKeeper> pool = null;
	
	private static ZookeeperPoolFactory zookeeperPool = new ZookeeperPoolFactory();

	private ZookeeperPoolFactory(){
		
		ZookeeperSessionFactory factory = new ZookeeperSessionFactory();

		GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
		poolConfig.maxActive = getMaxActive();
		poolConfig.maxIdle = 10;
		poolConfig.minIdle = 5;	
		//指明若在对象池空时调用borrowObject方法的行为被设定成等待，最多等待多少毫秒。如果等待时间超过了这个数值，则会抛出一个java.util.NoSuchElementException异常。如果这个值不是正数，表示无限期等待
		poolConfig.maxWait = 2000;
		//设定在进行后台对象清理时，视休眠时间超过了多少毫秒的对象为过期。过期的对象将被回收。如果这个值不是正数，那么对休眠时间没有特别的约束
		poolConfig.minEvictableIdleTimeMillis = getIdleTime();
		//设定间隔每过多少毫秒进行一次后台对象清理的行动。如果这个值不是正数，则实际上不会进行后台对象清理
		poolConfig.timeBetweenEvictionRunsMillis = getIdleTime() / 2L;
//				poolConfig.testOnBorrow = true;
		
		pool = new GenericObjectPool<ZooKeeper>(factory, poolConfig);
		
	}
	
	public final static ZookeeperPoolFactory getInstance() {
		return zookeeperPool;
	}
	
	public GenericObjectPool<ZooKeeper> getPool(){
		return pool;
	}


}

