package one.show.common.cache;

import one.show.common.client.redis.JedisUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Haliaeetus leucocephalus on 18/1/10.
 */
public abstract class RemoteCache<T> {
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
        synchronized (key.intern()) {
            T t = (T) JedisUtil.get(key);
            if (t == null) {
                t = getAliveObject();
                if (t != null) {
                    JedisUtil.set(key, t, timeToLive);
                }else{
                	JedisUtil.set(key, "NONE", timeToLive);
                }
                logger.info("remote cache MISS, key=" + key + ", timeToLive=" + timeToLive );
                return t;
            } else {
                logger.info("remote cache HIT, key=" + key + ", timeToLive=" + timeToLive );
                if (t.equals("NONE")){
                	return null;
                }else{
                	return t;
                }
            }
        }
    };
    /**
     * 踢出缓存
     *
     * @param key
     * @throws Exception
     */

    public static synchronized void remove(String key) throws Exception {
        JedisUtil.delete(key);
        logger.info("remote cache KICK, key=" + key);
    };

}
