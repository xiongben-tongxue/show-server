package one.show.common.client.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import one.show.common.Loader;
import one.show.common.TimeUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.util.Hashing;

public class JedisUtil {
	protected static Logger log = LoggerFactory.getLogger(JedisUtil.class);

	private static ShardedJedisPool jedisPool = null;
	private static List<JedisShardInfo> shardInfos;
	/** 缓存生存时间 */
	private final static int expire = 3600 * 24;

	static {
		shardInfos = new ArrayList<JedisShardInfo>();
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(JRedisPoolConfig.MAX_ACTIVE);
		config.setMinIdle(JRedisPoolConfig.MAX_IDLE / 2);
		config.setMaxIdle(JRedisPoolConfig.MAX_IDLE);
		config.setMaxWaitMillis(JRedisPoolConfig.MAX_WAIT);
		config.setTestOnBorrow(JRedisPoolConfig.TEST_ON_BORROW);
		config.setTestOnReturn(JRedisPoolConfig.TEST_ON_RETURN);
		// redis如果设置了密码：
		for (int i = 0 ; i < JRedisPoolConfig.SHARD_NUM; i++){
			String ip = JRedisPoolConfig.p.getProperty("redis.ip.shard"+i);
			Integer  port = Integer.parseInt(JRedisPoolConfig.p.getProperty("redis.port.shard"+i));
			String password = JRedisPoolConfig.p.getProperty("redis.password.shard"+i);
			addShard(ip, port, password);
			log.info("add shard "+i+", ip:"+ip+", port:"+port);
		}
           
		jedisPool = new ShardedJedisPool(config, shardInfos, Hashing.MURMUR_HASH);
	}

	public static void addShard(String host,int port,String password){
		JedisShardInfo info= new JedisShardInfo(host, port);
		info.setPassword(password);
		shardInfos.add(info);
	}
	

	/**
	 * 从jedis连接池中获取获取jedis对象
	 */
	public static ShardedJedis getJedis() {
		try {
			return jedisPool.getResource();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;

	}

	/**
	 * 回收jedis
	 */
	public static void returnJedis(ShardedJedis jedis) {
		if (jedis != null)
			try {
				jedisPool.returnResource(jedis);
			} catch (Exception e) {
//				log.error(e.getMessage(),e);
			}

	}

	/**
	 * 检查key是否存在缓存
	 * 
	 * @param key
	 * @return
	 */
	public static boolean checkKeyExisted(Object key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return false;
		}
		boolean result = false;
		try {
			if (key instanceof String) {
				if (conn.exists((String) key)) {// 字符串key存在，直接返回
					returnJedis(conn);
					return true;
				}
			}
			result = conn.exists(SerializeUtil.serialize(key));
			returnJedis(conn);
		} catch (Exception e) {
			returnJedis(conn);
		}
		return result;
	}

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public static boolean checkKeyExisted(byte[] key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return false;
		}
		boolean result = false;
		try {
			result = conn.exists(key);
			returnJedis(conn);
		} catch (Exception e) {
			returnJedis(conn);
		}
		return result;
	}

	/**
	 * 加1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public static long increase(String key) {
		
		return increase(key, 1);
	}


	/**
	 * 加操作，指定加的量
	 * 
	 * @param key
	 * @param num
	 * @return
	 */
	public static long increase(String key, int num) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.incrBy(key, num);
			returnJedis(conn);
			return result;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return -1;
	}

	/**
	 * 减1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public static long decrease(String key) {
		return decrease(key, 1);
	}

	/**
	 * 减操作，指定减的值
	 * 
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public static long decrease(String key, int num) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.decrBy(key, num);
			returnJedis(conn);
			return result;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return -1;
	}

	/**
	 * 减1操作
	 * 
	 * @param key
	 * @return 返回操作后的值
	 */
	public static long decrease(byte[] key) {
		return decrease(key, 1);
	}

	/**
	 * 减操作，指定减的值
	 * 
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public static long decrease(byte[] key, int num) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.decrBy(key, num);
			returnJedis(conn);
			return result;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return -1;
	}

	/**
	 * 设置过期时间,先做字符串判断，不存在再对key做序列化处理
	 */
	public static void expire(String key, int seconds) {
		if (seconds <= 0) {
			return;
		}
		ShardedJedis conn = getJedis();

		if (conn == null) {
			return;
		}
		long result = conn.expire(key, seconds);
		
		if (result == 0) {
			result = conn.expire(SerializeUtil.serialize(key), seconds);
		}
		returnJedis(conn);
	}
	

	/**
	 * 设置默认过期时间
	 */
	public static void expire(String key) {
		expire(key, expire);
	}

	/**
	 * 删除缓存记录，先做字符串判断，不存在再对key做序列化处理
	 * 
	 * @param key
	 */
	public static long delete(String key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.del(key);
			if (result == 0) {
				result = conn.del(SerializeUtil.serialize(key));
			}
			returnJedis(conn);
			return result;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return -1;
	}

	/**
	 * 删除缓存记录，直接对key做序列化处理
	 * 
	 * @param key
	 * @return
	 */
	public static long deleteObjectKey(Object key) {
		return delete(SerializeUtil.serialize(key));
	}

	/**
	 * 删除记录
	 * 
	 * @param key
	 * @return
	 */
	public static long delete(byte[] key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return -1;
		}
		try {
			long result = conn.del(key);
			returnJedis(conn);
			return result;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return -1;
	}

	/**
	 * 设置对象类型缓存项，无失效时间
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(Object key, Object value) {
		set(SerializeUtil.serialize(key), SerializeUtil.serialize(value), -1);
	}

	
	/**
	 * SET if Not eXists
	 * 将 key 的值设为 value，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 
	 * 
	 * @param key
	 * @param value
	 * 
	 * 1 if the key was set
	   0 if the key was not set
	   
	    redis> SETNX mykey "Hello"
		(integer) 1
		redis> SETNX mykey "World"
		(integer) 0
		redis> GET mykey
		"Hello"
		redis> 
	 */
	public static long setNx(String key, String value) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return 0l;
		}
		try {
			long result = conn.setnx(key, value);
			returnJedis(conn);
			return result;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return 0l;
	}
	
	
	/**
	 * 设置对象类型缓存项，加入失效时间，单位为秒
	 * 
	 * @param key
	 * @param value
	 * @param exp
	 */
	public static void set(Object key, Object value, int exp) {
		set(SerializeUtil.serialize(key), SerializeUtil.serialize(value), exp);
	}

	/**
	 * 设置key-value项，字节类型
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(byte[] key, byte[] value, int exp) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return;
		}
		try {
			if (exp > 0) {
				conn.setex(key, exp, value);
			} else {
				conn.set(key, value);
			}
			returnJedis(conn);
		} catch (Exception e) {
			returnJedis(conn);
		}
	}

	/**
	 * 获取对象类型
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(Object key) {
		byte[] data = get(SerializeUtil.serialize(key));
		if (data != null) {
			return SerializeUtil.unserialize(data);
		}
		return null;
	}

	/**
	 * 获取对象类型
	 *
	 * @param key
	 * @return
	 */
	public static Long getCount(String key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return -1l;
		}
		try {
			Long result = Long.valueOf(conn.get(key));
			returnJedis(conn);
			return result;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return -1l;
	}
	
	public static Boolean exists(String key) {
		return checkKeyExisted(key);
	}

	/**
	 * 获取对象类型
	 *
	 * @param key
	 * @return
	 */
	public static String setCount(String key, Long count) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return "-1";
		}
		try {
			String code = conn.set(key, String.valueOf(count));
			returnJedis(conn);
			return code;
		} catch (Exception e) {
			returnJedis(conn);
			return "-1";
		}
	}

	/**
	 * 获取对象类型
	 *
	 * @param key
	 * @return
	 */
	public static String setCount(String key, Long count, int expire) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return "-1";
		}
		try {
			String code = conn.setex(key,expire, String.valueOf(count));
			returnJedis(conn);
			return code;
		} catch (Exception e) {
			returnJedis(conn);
			return "-1";
		}
	}

	/**
	 * 获取key value
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.get(key);
			returnJedis(conn);
			return data;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return null;
	}

	/**
	 * 获取列表(默认从左边第一个开始)
	 * 
	 * @param listKey
	 * @return
	 */
	public static <T> List<T> getList(Class<T> classz, Object listKey,
			int start, int count) {
		List<byte[]> data = getList(SerializeUtil.serialize(listKey), start,
				count);
		List<T> result = new ArrayList<T>();
		if (data != null && data.size() > 0) {
			for (byte[] item : data) {
				result.add((T) SerializeUtil.unserialize(item));
			}
			return result;
		}
		return null;
	}

	/**
	 * 获取列表数据
	 * 
	 * @param listKey
	 * @return
	 */
	public static List<byte[]> getList(byte[] listKey, int start, int count) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			List<byte[]> data = conn.lrange(listKey, start, start + count - 1); // 默认设置一个大数
			returnJedis(conn);
			return data;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return null;
	}

	/**
	 * 从左边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public static void addToListLeft(Object listKey, Object value) {
		addToListLeft(SerializeUtil.serialize(listKey),
				SerializeUtil.serialize(value));
	}

	/**
	 * 从左边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public static void addToListLeft(byte[] listKey, byte[] value) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return;
		}
		try {
			conn.lpush(listKey, value);
			returnJedis(conn);
		} catch (Exception e) {
			returnJedis(conn);
		}
	}

    public static void rpush(String listKey, String... value) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return;
        }
        try {
            conn.rpush(listKey, value);
            returnJedis(conn);
        } catch (Exception e) {
            returnJedis(conn);
        }
    }


    public static String lpop(String listKey) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            String value = conn.lpop(listKey);
            returnJedis(conn);
            if (!StringUtils.isBlank(value)) {
                return value;
            }
        } catch (Exception e) {
            returnJedis(conn);
        }
        return null;
    }

	/**
	 * 从右边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public static void addToListRight(Object listKey, Object value) {
		addToListRight(SerializeUtil.serialize(listKey),
				SerializeUtil.serialize(value));
	}

	/**
	 * 从右边添加到list
	 * 
	 * @param listKey
	 * @param value
	 */
	public static void addToListRight(byte[] listKey, byte[] value) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return;
		}
		try {
			conn.rpush(listKey, value);
			returnJedis(conn);
		} catch (Exception e) {
			returnJedis(conn);
		}
	}

	/**
	 * 从左边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public static Object bpopFromListLeft(Object listKey, int timeout) {
		return SerializeUtil.unserialize(bpopFromListLeft(
				SerializeUtil.serialize(listKey), timeout));
	}

	/**
	 * 从左边阻塞移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public static byte[] bpopFromListLeft(byte[] listKey, int timeout) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			List<byte[]> data = conn.blpop(listKey);
			returnJedis(conn);
			if (data != null && data.size() > 1) {
				return data.get(1);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			returnJedis(conn);
		}
		return null;
	}

	/**
	 * 从左边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public static Object popFromListLeft(Object listKey) {
		return SerializeUtil.unserialize(popFromListLeft(SerializeUtil
				.serialize(listKey)));
	}

	/**
	 * 从左边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public static byte[] popFromListLeft(byte[] listKey) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.lpop(listKey);
			returnJedis(conn);
			return data;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return null;
	}

	/**
	 * 从右边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public static Object popFromListRight(Object listKey) {
		return SerializeUtil.unserialize(popFromListRight(SerializeUtil
				.serialize(listKey)));
	}

	/**
	 * 从右边移除一个对象，并返回该对象
	 * 
	 * @param listKey
	 * @return
	 */
	public static byte[] popFromListRight(byte[] listKey) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			byte[] data = conn.rpop(listKey);
			returnJedis(conn);
			return data;
		} catch (Exception e) {
			returnJedis(conn);
		}
		return null;
	}

	/**
	 * 获取列表长度
	 * 
	 * @param listKey
	 * @return
	 */
	public static int getLengthOfList(Object listKey) {
		return getLengthOfList(SerializeUtil.serialize(listKey));
	}

	/**
	 * 获取列表长度
	 * 
	 * @param listKey
	 * @return
	 */
	public static int getLengthOfList(byte[] listKey) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return 0;
		}
		try {
			int length = conn.llen(listKey).intValue();
			returnJedis(conn);
			return length;
		} catch (Exception e) {
			returnJedis(conn);
			return 0;
		}
	}
	
	
	/**
	 * 	当 key 不存在时，返回 -2 。
	 	当 key 存在但没有设置剩余生存时间时，返回 -1 。
		否则，以秒为单位，返回 key 的剩余生存时间。
	 * @param key
	 * @return
	 */
	public static long getTTL(Object key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return 0;
		}
		try {
			long length = conn.ttl((String)key);
			if (length == -2){
				length  = conn.ttl(SerializeUtil.serialize(key));
			}
			returnJedis(conn);
			return length;
		} catch (Exception e) {
			returnJedis(conn);
			return 0;
		}
	}

	/**
	 * 删除列表中数据
	 * 
	 * @param listKey
	 * @return
	 */
	public static void deleteFromList(Object listKey, Object obj) {
		deleteFromList(SerializeUtil.serialize(listKey),
				SerializeUtil.serialize(obj));
	}

	/**
	 * 删除列表中数据
	 * 
	 * @param listKey
	 * @return
	 */
	public static void deleteFromList(byte[] listKey, byte[] value) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return;
		}
		try {
			conn.lrem(listKey, 1, value);
			returnJedis(conn);
		} catch (Exception e) {
			returnJedis(conn);
		}
	}

	/* hash */

	public static <T> T hGet(String key, String field) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}

		try {
			byte[] hget = conn.hget(key.getBytes(), field.getBytes());
			returnJedis(conn);

			return (T) SerializeUtil.unserialize(hget);
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error get hget key : {}, field : {}", key, field, e);
		}
		return null;
	}
    public static String hget(String key, String field) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            String hget = conn.hget(key, field);
            returnJedis(conn);
            return hget;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("hget error! key={}, field={}", key, field, e);
        }
        return null;
    }

	public static void hdel(String key, String field) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return;
        }

        try {
            conn.hdel(key, field);
            conn.hdel(key.getBytes(), field.getBytes());
            returnJedis(conn);
            return;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("hdel error! key={}, field={}", key, field, e);
        }
        return;
    }

	public static <T> Map<String, T> hGetAll(String key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}

		try {
			Map<byte[], byte[]> hgetall = conn.hgetAll(key.getBytes());
			returnJedis(conn);

			Map<String, T> result = new HashMap<>();

            for (Map.Entry<byte[], byte[]> entry : hgetall.entrySet()) {
                result.put(new String(entry.getKey(),"UTF-8"), (T) SerializeUtil.unserialize(entry.getValue()));
            }

			return result;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error hgetall key key : {}", key, e);
		}
		return null;
	}

    public static Map<String, String> hgetAll(String key) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }

        try {
            Map<String,String> result = conn.hgetAll(key);
            returnJedis(conn);
            return result;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("error hgetAll key key : {}", key, e);
        }
        return null;
    }

	public static <T> Long hSet(String key, String field, T value) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}

		try {
			Long hget = conn.hset(key.getBytes(), field.getBytes(),
					SerializeUtil.serialize(value));
			returnJedis(conn);

			return hget;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error hset key : {}, value : {}", key, value, e);
		}
		return null;
	}
    public static Long hset(String key, String field, String value) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }

        try {
            Long hget = conn.hset(key, field, value);
            returnJedis(conn);
            return hget;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("error hset key : {}, value : {}", key, value, e);
        }
        return null;
    }

	public static <T> String hMSet(String key, Map<String, T> map) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}

		Map<byte[], byte[]> byteMap = new HashMap<>();

		for (Map.Entry<String, T> entry : map.entrySet()) {
			byteMap.put(entry.getKey().getBytes(),
					SerializeUtil.serialize(entry.getValue()));
		}

		try {
			String hget = conn.hmset(key.getBytes(), byteMap);
			returnJedis(conn);

			return hget;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error hmset key : {}", key, e);
		}
		return null;
	}

	public static String hmset(String key, Map<String, String> map) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}

		try {
			String hget = conn.hmset(key, map);
			returnJedis(conn);

			return hget;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error hmset key : {}", key, e);
		}
		return null;
	}

    public static Long hincrby(String key, String field, int increment) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }

        try {
            Long hget = conn.hincrBy(key.getBytes(), field.getBytes(),increment);
            returnJedis(conn);
            return hget;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("hincrby error! key={}, field={}, increment={}", key, field, increment, e);
        }
        return null;
    }


	/* sorted set */

	/**
	 * 
	 * @param key
	 * @param scores
	 * @param member
	 * @param <T>
	 * @return
	 */
	public static <T> Long zAdd(String key, double scores, T member) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			Long zadd = conn.zadd(key.getBytes(), scores,
					SerializeUtil.serialize(member));
			returnJedis(conn);
			return zadd;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error zadd key : {}, scores : {}", key, scores, e);
		}
		return null;
	}

    /**
     * @param key
     * @param member
     * @return
     */
    public static Long zrank(String key, String member) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Long zadd = conn.zrank(key.getBytes(), SerializeUtil.serialize(member));
            returnJedis(conn);
            return zadd;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("zrank error! key={}, member={}", key, member, e);
        }
        return null;
    }


    public static Double zscore(String key, String member) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Double zscore = conn.zscore(key.getBytes(), SerializeUtil.serialize(member));
            returnJedis(conn);
            return zscore;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("zscore error! key={}, member={}", key, member, e);
        }
        return null;
    }

	public static long zcount(String key, double max, double min) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return 0;
		}
		try {
            Long zcount = conn.zcount(key.getBytes(), min, max);
			returnJedis(conn);
            return zcount == null || zcount <= 0 ? 0 : zcount.longValue();
		} catch (Exception e) {
			returnJedis(conn);
			log.error("zcount error! key={}, max={}, min={}", key, min, e);
		}
		return 0;
	}

	/**
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> List<T> zRevRange(String key, int start, int end) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			Set<byte[]> zrevrange = conn.zrevrange(key.getBytes(), start, end);
			returnJedis(conn);

			List<T> result = new ArrayList<>();
			for (byte[] item : zrevrange)
				result.add((T) SerializeUtil.unserialize(item));
			return result;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error zrevrange key : {}, start {}, end {}", key, start,
					end, e);
		}
		return null;
	}

	/**
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> List<ItemWithScore<T>> zRevRangeWithScores(String key,
			int start, int end) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			Set<Tuple> zrevrangeWithScores = conn.zrevrangeWithScores(
					key.getBytes(), start, end);
			returnJedis(conn);
			List<ItemWithScore<T>> result = new ArrayList<>();
			for (Tuple tuple : zrevrangeWithScores) {
				result.add(new ItemWithScore<T>(tuple.getBinaryElement(), tuple
						.getScore()));
			}
			return result;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error zRevRangeWithScores key : {}, start {} , end {}",
					key, start, end, e);
		}
		return null;
	}


    public static <T> List<ItemWithScore<T>> zRevrangeByScoreWithScores(String key,double max, double min, int offset, int count) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Set<Tuple> zrevrangeByScoreWithScores = conn.zrevrangeByScoreWithScores(
                    key.getBytes(), max, min, offset, count);
            returnJedis(conn);
            List<ItemWithScore<T>> result = new ArrayList<>();
            for (Tuple tuple : zrevrangeByScoreWithScores) {
                result.add(new ItemWithScore<T>(tuple.getBinaryElement(), tuple
                        .getScore()));
            }
            return result;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("zrevrangeByScoreWithScores error！ key={}, max={}, min={}, offset={}, count={}",
                    key, max, min, offset, count, e);
        }
        return null;
    }

	/**
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long zRemRangeByScore(String key, double start, double end) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			Long ret = conn.zremrangeByScore(key.getBytes(), start, end);
			returnJedis(conn);

			return ret;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error zRemRangeByScore key : {}, start {} , end {}",
					key, start, end, e);
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static <T> Double zIncrBy(String key, Double score, T member) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			Double ret = conn.zincrby(key.getBytes(), score,
					SerializeUtil.serialize(member));
			returnJedis(conn);

			return ret;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error zRemRangeByScore key : {}, score {}", key, score,
					e);
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static Long zcard(String key) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			Long ret = conn.zcard(key.getBytes());
			returnJedis(conn);

			return ret;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("error zcard key : {}, start {} , end {}", key, e);
		}
		return null;
	}

	/* set */
	public static Long sadd(String key, String... members) {
		ShardedJedis conn = getJedis();
		if (conn == null) {
			return null;
		}
		try {
			Long sAdd = conn.sadd(key, members);
			returnJedis(conn);
			return sAdd;
		} catch (Exception e) {
			returnJedis(conn);
			log.error("sadd error! key={}, members={}", key, members, e);
		}
		return null;
	}
    public static Boolean sismember(String key, String member) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Boolean zadd = conn.sismember(key, member);
            returnJedis(conn);
            return zadd.booleanValue();
        } catch (Exception e) {
            returnJedis(conn);
            log.error("sismember error! key={}, member={}", key, member, e);
        }
        return null;
    }
    public static Set<String> smembers(String key) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            Set<String> smembers = conn.smembers(key);
            returnJedis(conn);
            return smembers;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("smembers error! key={}, smembers={}", key, e);
        }
        return null;
    }

    
    public static List<String> srandmember(String key,int count) {
        ShardedJedis conn = getJedis();
        if (conn == null) {
            return null;
        }
        try {
            List<String> srandmember = conn.srandmember(key, count);
            returnJedis(conn);
            return srandmember;
        } catch (Exception e) {
            returnJedis(conn);
            log.error("srandmember error! key={}, srandmember={}", key, e);
        }
        return null;
    }



	public static void main(String[] args) {
//		List o = (List) get("feed_list_5565a6f85a663c79008b4574");
//		System.out.println(o.size());
		System.out.println(JedisUtil.increase("NEW_MESSAGE_TO_UID_PRIFIX_84800054845518eee643200000"));
//		JedisUtil.delete("NEW_MESSAGE_TO_UID_PRIFIX_848000548455186432");
//		Long myMsgCount = JedisUtil.getCount("NEW_MESSAGE_TO_UID_PRIFIX_848000548455186432");
//		System.out.println(myMsgCount);
//		JedisUtil.set("callback10050d395ba54a6a424c981d4fe9c63f00a5",new WCSCallBackResponse(),60);
//		System.out.println(JedisUtil.get("callback10050d395ba54a6a424c981d4fe9c63f00a5"));
		
//		System.out.println(JedisUtil.getCount("cache_work"));
//		System.out.println(JedisUtil.increase("cache_work"));
//		JedisUtil.delete("cache_work_count");

//		String key = "user_table_1_"+String.valueOf(TimeUtil.dateToIntegerDay(new Date()));
//		System.out.println(JedisUtil.delete(key));

//        JedisUtil.zAdd("A2",1,"A21");
//        JedisUtil.zAdd("A2",2,"A22");
//        JedisUtil.zAdd("A2",3,"A23");
//        JedisUtil.zAdd("A2",4,"A24");
//        JedisUtil.zAdd("A2",5,"A25");
//        JedisUtil.zAdd("A2",6,"A26");
//        JedisUtil.zAdd("A2",7,"A27");
//        JedisUtil.zAdd("A2",8,"A28");
//        JedisUtil.zAdd("A2",9,"A29");
//
//        List<ItemWithScore<String>> result = JedisUtil.zRevrangeByScoreWithScores("A2",1000, 2, 3,2);
//        for (ItemWithScore itemWithScore : result) {
//            System.out.println(itemWithScore.getItem() + "   " + itemWithScore.getScore());
//        }

//		List<Long> uids = (List<Long>)JedisUtil.get("feedlist:outnumber:banner:uids");
//		System.out.println(uids);
//		String key = String.format("feedlist:outnumber:banner:video:%s:%s:list",uid,3600 );
//		List<FeedListView> feedListViewList = JedisUtil.getList(FeedListView.class,key, 0, 10);
	}
}
