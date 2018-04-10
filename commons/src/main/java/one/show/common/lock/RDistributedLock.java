/**
 * 
 */
package one.show.common.lock;

import one.show.common.client.redis.JedisUtil;
import one.show.common.exception.LockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Haliaeetus leucocephalus 2018年1月15日 下午4:33:03
 * Redis setNX 分布式锁
 */
public class RDistributedLock {
	
	private static final Logger log = LoggerFactory.getLogger(RDistributedLock.class);
	private String lockName;
    private int lockTimeout = 10; //锁超时时间 s
    
    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
    public RDistributedLock(String lockName){
        this.lockName = "lock_"+lockName;
    }

    /** 
     * 获取锁。 
     * 该获取锁方法有如下特性： 
     * 1.如果获取锁成功，会设置锁的生存时间； 
     * 2.虽然大多数情况下redis的锁都有生存时间， 
     * 但是为了防止在上锁后、设置锁的生存周期 
     * 之前获取锁的方法出现了异常而终止。我们加入如下判断： 
     * 如果获取锁失败，会检查已存在锁是否设置有生存时间， 
     * 如果没有设置生存时间，那么会给锁设置生存时间。 
     * 
     */  
    public void lock() throws LockException{
    	 int timeout = lockTimeout * 1000;
         while (timeout >= 0) {
        	 if (JedisUtil.setNx(this.lockName, "lock") == 1) {  
              	JedisUtil.expire(this.lockName, lockTimeout);  
              	log.info("acquire lock : " + lockName );  
              	break;
              }else{
             	 log.info("wait lock : " + lockName);
                  
                  if (JedisUtil.getTTL(this.lockName) < 0) {  
                	  JedisUtil.expire(this.lockName, lockTimeout);  
                  }  
              }
        	 
        	 timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
        	 try {
				Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
			} catch (InterruptedException e) {
				throw new LockException(e);
			}

         }
    }
 
 
    public void unlock() {
    	
    	JedisUtil.delete(this.lockName);
    	
    	log.info("release lock : " + lockName);  

    }


    private static int num = 0;
	public static void ins(){
		num++;
		System.out.println("=========="+num);
	}
	
	public static void main(String[] args) throws InterruptedException, LockException {
		for (int i=0;  i<10; i++){
			new Thread(new Runnable(){

				@Override
				public void run() {

					RDistributedLock lock = new RDistributedLock("test_xxo");
					try {
						lock.lock();
					} catch (LockException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					Thread.sleep(1000 * 30);
					ins();
					System.out.println("test");
					lock.unlock();
				}
				
			}).start();
			
		}
		
		
	}
 
}



