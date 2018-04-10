/**
 * 
 */
package one.show.common.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import one.show.common.Constant;
import one.show.common.Loader;
import one.show.common.exception.LockException;
import one.show.common.zk.BaseZookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Haliaeetus leucocephalus 2018年1月15日 下午4:33:03
 * zk分布式锁
 */
public class DistributedLock extends BaseZookeeper{
	
	private static final Logger log = LoggerFactory.getLogger(DistributedLock.class);
	  
    private String waitNode;//等待前一个锁
    private String lockName;
    private String myZnode;
    private List<Exception> exception = new ArrayList<Exception>();
    private int lockTimeout = 10000;
    private CountDownLatch latch;//计数器
    
    /**
     * 创建分布式锁
     * @param lockName 竞争资源标志,lockName中不能包含单词lock
     */
    public DistributedLock(String lockName){
        this.lockName = lockName;
        // 创建一个与服务器的连接
        try {
			connect();
            if(!exists(Constant.ZK_LOCK_PATH_HOME, false)){
                // 创建根节点
            	createPersistentNode(Constant.ZK_LOCK_PATH_HOME, new byte[0]);
            }
		}  catch (Exception e) {
			exception.add(e);
			log.error(e.getMessage(), e);
		}
    }
 

     
    public void lock() throws LockException{
        if(exception.size() > 0){
            throw new LockException(exception.get(0));
        }
        try {
            if(this.tryLock()){
                log.info("Thread " + Thread.currentThread().getId() + " " + lockName + " get lock true");
                return;
            }
            else{
                waitForLock(waitNode);//等待锁
            }
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        } 
    }
 
    public boolean tryLock() throws LockException{
        try {

        	String splitStr = "_lock_";
            if(lockName.contains(splitStr))
                throw new LockException("lockName can not contains \\u000B");
            //创建临时子节点
            myZnode = createEphemeralSeqNode(Constant.ZK_LOCK_PATH_HOME + "/" + lockName + splitStr, new byte[0]);
            log.info(myZnode + " is created ");
            
            //取出所有子节点
            List<String> subNodes = getChildren(Constant.ZK_LOCK_PATH_HOME, false);
            //取出所有lockName的锁
            List<String> lockObjNodes = new ArrayList<String>();
            for (String node : subNodes) {
                String _node = node.split(splitStr)[0];
                if(_node.equals(lockName)){
                    lockObjNodes.add(node);
                }
            }
            Collections.sort(lockObjNodes);
            if(myZnode.equals(Constant.ZK_LOCK_PATH_HOME+"/"+lockObjNodes.get(0))){
                //如果是最小的节点,则表示取得锁
                return true;
            }
            //如果不是最小的节点，找到比自己小1的节点
            String subMyZnode = myZnode.substring(myZnode.lastIndexOf("/") + 1);
            waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes, subMyZnode) - 1);
        } catch (KeeperException e) {
            throw new LockException(e);
        } catch (InterruptedException e) {
            throw new LockException(e);
        }
        return false;
    }
 

 
    private boolean waitForLock(String lower) throws InterruptedException, KeeperException {
        //判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
        if(exists(Constant.ZK_LOCK_PATH_HOME + "/" + lower, true)){
            log.info("Thread " + Thread.currentThread().getId() + " waiting for " + Constant.ZK_LOCK_PATH_HOME + "/" + lower);
            this.latch = new CountDownLatch(1);
            this.latch.await(lockTimeout, TimeUnit.MILLISECONDS);
            this.latch = null;
        }
        return true;
    }
 
    public void unlock() {
        try {
            log.info("unlock : " + myZnode);
            delete(myZnode,-1);
            myZnode = null;
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }finally{
        	try {
				close();
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
        }
    }


	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#nodeChildrenChanged(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void nodeChildrenChanged(WatchedEvent event) {
		
	}

	/* (non-Javadoc)
	 * @see one.show.common.zk.BaseZookeeper#connected(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void connected(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void nodeDeleted(WatchedEvent event) {

		if(this.latch != null) {  
            this.latch.countDown();  
        }
	}
	
	
	public static void main(String[] args) throws InterruptedException, LockException {
		DistributedLock lock = new DistributedLock("test");
		lock.lock();
//		Thread.sleep(1000 * 30);
		System.out.println("test");
		lock.unlock();
	}
 
}



