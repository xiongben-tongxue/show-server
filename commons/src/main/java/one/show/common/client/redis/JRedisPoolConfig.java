package one.show.common.client.redis;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class JRedisPoolConfig {
    protected static Logger log = LoggerFactory.getLogger(JRedisPoolConfig.class);
    public static int MAX_ACTIVE;
    public static int MAX_IDLE;
    public static long MAX_WAIT;
    public static boolean TEST_ON_BORROW;
    public static boolean TEST_ON_RETURN;
    public static int SHARD_NUM;
    
    public static Properties p;
    static {
        init();
    }
 
    public static void init() {
        try {
            log.info("loading redis config from redis.properties.......");
            InputStream in = JRedisPoolConfig.class.getResourceAsStream("/redis.properties");
            
            p = new Properties();
            p.load(in);
            MAX_ACTIVE = Integer.parseInt(p.getProperty("redis.pool.maxActive"));
            MAX_IDLE = Integer.parseInt(p.getProperty("redis.pool.maxIdle"));
            MAX_WAIT = Integer.parseInt(p.getProperty("redis.pool.maxWait"));
            TEST_ON_BORROW = Boolean.parseBoolean(p.getProperty("redis.pool.testOnBorrow"));
            TEST_ON_RETURN = Boolean.parseBoolean(p.getProperty("redis.pool.testOnReturn"));
            SHARD_NUM = Integer.parseInt(p.getProperty("redis.shard.num"));
            log.info("redis config load Completed. SHARD_NUM:"+SHARD_NUM+", MAX_ACTIVE:"+MAX_ACTIVE);
            in.close();
            in=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}