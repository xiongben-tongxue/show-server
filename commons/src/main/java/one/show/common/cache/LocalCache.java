package one.show.common.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.statistics.LiveCacheStatisticsData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地缓存类
 *
 * @author weizhangbj8024 May 18, 2012
 */

public abstract class LocalCache<T> {
    private static Logger logger = LoggerFactory.getLogger(LocalCache.class);

    /**
     * 构建实际对象
     *
     * @return
     * @throws Exception
     */
    public abstract T getAliveObject() throws Exception;

    /**
     * 本地缓存
     *
     * @param timeToLive 对象过期时间(秒)
     * @param key
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public T put(int timeToLive, String key) throws Exception {
    	return put(timeToLive, timeToLive, key);
    }

    /**
     * 本地缓存
     *
     * @param timeToLive 对象过期时间(秒)
     * @param timeToIdle 对象钝化时间(秒)
     * @param key
     * @return
     * @throws Exception
     */
    public T put(int timeToLive, int timeToIdle, String key) throws Exception {

    	long beginTime = System.currentTimeMillis();
    	long endTime = 0;
        Cache cache = EhCacheMgr.getInstance().getCache();
        Element element = cache.get(key);
        if (element == null) {
            T t = getAliveObject();
            //long st = System.currentTimeMillis();
            if (t != null) {
                element = new Element(key, t);
                
            }else{
            	element = new Element(key, "NONE");
            }
            element.setEternal(false);
            element.setTimeToLive(timeToLive);
            element.setTimeToIdle(timeToIdle);
            cache.put(element);
            		
            endTime = System.currentTimeMillis();
            logger.info("local cache MISS, key=" + key + ", timeToLive=" + timeToLive + ", timeToIdle=" + timeToIdle+", time="+(endTime-beginTime)+" ms");
            return t;
        } else {
        	endTime = System.currentTimeMillis();
            logger.info("local cache HIT, key=" + key + ", timeToLive=" + timeToLive + ", timeToIdle=" + timeToIdle+", time="+(endTime-beginTime)+" ms");
            if (element.getValue().equals("NONE")){
            	return null;
            }else{
            	return (T) element.getValue();
            }
            
        }
    
    }
    
    public T get(String key) throws Exception {

        Cache cache = EhCacheMgr.getInstance().getCache();
        Element element = cache.get(key);
        if (element == null) {
        	logger.info("local cache MISS, key=" + key );
        	return null;
        } else {
        	logger.info("local cache HIT, key=" + key );
        	return (T) element.getValue();
        }
    
    }

    
    public void put(int timeToLive, String key, T t) throws Exception {
    	if (t == null){
    		throw new Exception("The value can not be NULL");
    	}

        Cache cache = EhCacheMgr.getInstance().getCache();
        Element element = cache.get(key);
        
        element = new Element(key, t);
        element.setEternal(false);
        element.setTimeToLive(timeToLive);
        element.setTimeToIdle(timeToLive);
        cache.put(element);
    
    }

    /**
     * 踢出缓存
     *
     * @param key
     * @throws Exception
     */
    public  void kick(String key) throws Exception {
        Cache cache = EhCacheMgr.getInstance().getCache();
        if (cache.isKeyInCache(key)) {
            cache.remove(key);
            logger.info("local cache KICK, key=" + key);
        }
    }

    public static  void remove(String key) throws Exception {
        Cache cache = EhCacheMgr.getInstance().getCache();
        if (cache.isKeyInCache(key)) {
            cache.remove(key);
            logger.info("local cache KICK, key=" + key);
        }
    }

}
