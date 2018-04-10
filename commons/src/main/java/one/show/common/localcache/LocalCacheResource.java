package one.show.common.localcache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import one.show.common.localcache.ConcurrentLinkedHashMap.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用堆内存的本地缓存
 *
 */
public class LocalCacheResource{
	private static Logger logger=LoggerFactory.getLogger(LocalCacheResource.class);
	
	private static LocalCacheResource instance = new LocalCacheResource();
	
    public static final LocalCacheResource getInstance() {
	    return instance;  
    } 
	
	private ScheduledFuture<?> cleanerThread;
	private ConcurrentLinkedHashMap<String,CacheEntry> cacheMap;
	private ConcurrentHashMap<String,Object>noneExpireMap;
	private static final int DEFAULT_MAX_CACHE_SIZE=65535;
	private static final int DEFAULT_INIT_CACHE_SIZE=1024;
	private static final int DEFAULT_MAX_WEIGHTED_CAPACITY=1024;
	private AtomicLong totalQueryCount;
	private AtomicLong hitQueryCount;
	
	private int maxCacheSize;
	public LocalCacheResource() {
		maxCacheSize=DEFAULT_MAX_CACHE_SIZE;
		cacheMap=new Builder<String,CacheEntry>()
						.initialCapacity(DEFAULT_INIT_CACHE_SIZE)
						.maximumWeightedCapacity(DEFAULT_MAX_WEIGHTED_CAPACITY)
						.build();
		noneExpireMap=new ConcurrentHashMap<String, Object>();
		totalQueryCount=new AtomicLong();
		hitQueryCount=new AtomicLong();
		start();
	}
	//
	public List<String>getKeys(){
		ArrayList<String>result=new ArrayList<String>();
		result.addAll(cacheMap.keySet());
		result.addAll(noneExpireMap.keySet());
	 	return result;
	}
	public int getNoneExpireSize(){
		return noneExpireMap.size();
	}
	public int getExpireSize(){
		return cacheMap.size();
	}
	//
	public List<String>getKeys(String prefix){
		List<String> list=new ArrayList<String>();
		for(String s:getKeys()){
			if(s.startsWith(prefix)){
				list.add(s);
			}
		}
		return list;
	}
	//
	public boolean add(String key, int expireTime, Object obj)
			throws LocalCacheException {
		if(expireTime==0){
			noneExpireMap.put(key,obj);
			return true;
		}
		CacheEntry e=new CacheEntry();
		e.expireTime=expireTime;
		e.createTime=System.currentTimeMillis();
		e.object=obj;
		cacheMap.put(key,e);
		return true;
	}
	public boolean set(String key, int expireTime, Object obj)
			throws LocalCacheException {
		return add(key, expireTime, obj);
	}
	public boolean delete(String key) throws LocalCacheException {
		Object obj=noneExpireMap.remove(key);
		if(obj!=null){
			return true;
		}else{
			return cacheMap.remove(key)!=null;
		}
	}

	public Object get(String key) throws LocalCacheException {
		totalQueryCount.incrementAndGet();
		Object obj=noneExpireMap.get(key);
		if(obj!=null){
			hitQueryCount.incrementAndGet();
			return obj;
		}else{
			CacheEntry ee=cacheMap.get(key);
			if(ee!=null){
				hitQueryCount.incrementAndGet();
				return ee.object;
			}else{
				return null;
			}
		}
	}
	//--------------------------------------------------------------------------
	public int size(){
		return cacheMap.size()+noneExpireMap.size();
	}
	public void clear(){
		cacheMap.clear();
		noneExpireMap.clear();
	}
	
	/**
	 * @return the maxCacheSize
	 */
	public int getMaxCacheSize() {
		return maxCacheSize;
	}
	/**
	 * @param maxCacheSize the maxCacheSize to set
	 */
	public void setMaxCacheSize(int maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
	}
	
	/**
	 * @return the totalQueryCount
	 */
	public long getTotalQueryCount() {
		return totalQueryCount.get();
	}
	/**
	 * @return the hitQueryCount
	 */
	public long getHitQueryCount() {
		return hitQueryCount.get();
	}
	//--------------------------------------------------------------------------
	//life cycle
	//--------------------------------------------------------------------------
	public void start() {
		cacheMap.setCapacity(maxCacheSize);
		cleanerThread=Executors.newSingleThreadScheduledExecutor(
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r,"CacheCleaner");
					}
				}).scheduleAtFixedRate(
				new CacheCleaner(),60,60,TimeUnit.SECONDS);
		
	}
	public void stop() {
		if(cleanerThread!=null){
			cleanerThread.cancel(true);
		}
	}
	//
	public String dump() {
		StringBuilder sb=new StringBuilder();
		sb.append("maxCacheSize:"+maxCacheSize);
		return sb.toString();
	}
	
	//--------------------------------------------------------------------------
	//cache entry
	//--------------------------------------------------------------------------
	static class CacheEntry{
		int expireTime;
		long createTime;
		Object object;
		public CacheEntry() {
			expireTime=-1;//means never expire
		}
	}
	//
	class CacheCleaner implements Runnable{
		//
		@Override
		public void run() {
			if(logger.isDebugEnabled()){
				logger.debug("clean cache.size:"+cacheMap.size());
			}
			long time=System.currentTimeMillis();
			Iterator<Entry<String, CacheEntry>>ei=cacheMap.entrySet().iterator();
			for(;ei.hasNext();){
				Entry<String,CacheEntry>e=ei.next();
				long theTime=(time-e.getValue().createTime);
				long expireTime=e.getValue().expireTime*1000L;
				if(theTime>expireTime){
					//expired
					ei.remove();
				}
			}
			if(logger.isDebugEnabled()){
				logger.debug("clean cache done.size:"+cacheMap.size());
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			LocalCacheResource.getInstance().set("key", 1, "value");
			System.out.println("key="+LocalCacheResource.getInstance().get("key"));
			Thread.sleep(2000L);
			System.out.println("key="+LocalCacheResource.getInstance().get("key"));
		} catch (LocalCacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
