package one.show.common.cache;



import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author weizhangbj8024 
 * May 18, 2012
 * 
 */

public class EhCacheMgr {
	
	private static final Logger logger = LoggerFactory.getLogger(EhCacheMgr.class);
	
	private static EhCacheMgr _instance = new EhCacheMgr();
	private CacheManager cacheManager = null;

	public static EhCacheMgr getInstance() {
		return _instance;
	}

	private EhCacheMgr() {
		cacheManager = new CacheManager();

	}

	public  Cache getCache() {
		Cache cache = cacheManager.getCache("wpCache");
		return cache;
	}
	
	 public Object get(String key) throws Exception {

	        Cache cache = getCache();
	        Element element = cache.get(key);
	        if (element == null) {
	        	logger.info("local cache MISS, key=" + key );
	        	return null;
	        } else {
	        	logger.info("local cache HIT, key=" + key );
	        	return element.getValue();
	        }
	    
	 }

	    
	 public void put(int timeToLive, String key, Object obj) throws Exception {
	    	if (obj == null){
	    		throw new Exception("The value can not be NULL");
	    	}

	        Cache cache = getCache();
	        Element element = new Element(key, obj);
	        element.setEternal(false);
	        element.setTimeToLive(timeToLive);
	        element.setTimeToIdle(timeToLive);
	        cache.put(element);
	    
	}
	
	public static void main(String[] args) throws Exception {
		EhCacheMgr.getInstance().put(10, "test", 1);
		
		EhCacheMgr.getInstance().get("test");
		

        
	}

}
