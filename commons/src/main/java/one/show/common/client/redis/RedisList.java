package one.show.common.client.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

/**
 * redis list缓存类
 *
 * @author Haliaeetus leucocephalus 2018年1月7日 下午5:23:51
 */

public abstract class RedisList<T> {
    private static Logger logger = LoggerFactory.getLogger(RedisList.class);
    
    
    //最大加载条数
    private int maxCount = 2000;
    

    /**
     * 构建实际列表
     *
     * @return
     */
    public abstract List<T> listBuilder(int start, int count);


    
    /**
     * 
     * @param classz
     * @param key
     * @param start 开始位置
     * @param count 个数
     * @param expire 过期时间
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<T> get(Class<T> classz , String key, int start, int count, int expire) {
    	if (start < 0 || start > maxCount){
    		return null;
    	}
    	
    	if (count < 0){
    		return null;
    	}
    	
    	if (count > 50){
    		count = 50;
    	}
    	
    	List<T> cacheList = JedisUtil.getList(classz, key, start, count);
    	
		if (cacheList == null || cacheList.size() < count){
			
			int length = JedisUtil.getLengthOfList(key);
			

			if (length >= maxCount){
				return null;
			}
			
			if (cacheList != null){
				start = start + cacheList.size();
			}
			
			logger.info("redis list cache MISS, key=" + key + ", start=" + start + ", count=" + count);
			
			List<T> resultList = listBuilder(start, count);
			
			if (resultList != null && resultList.size() > 0){
				ShardedJedis conn = JedisUtil.getJedis();
				if (conn != null) {
					for(T t : resultList){
			        	 try {
			        		 if (start == 0){
			        			 conn.expire(key, expire);
				        	 }
			        		 conn.rpush(SerializeUtil.serialize(key), SerializeUtil.serialize(t));
			        		 JedisUtil.returnJedis(conn);
			             } catch (Exception e) {
			            	 JedisUtil.returnJedis(conn);
			             }
			        }
				}
				
				if (cacheList != null){
					List<T> resList = new ArrayList<T>();
					resList.addAll(cacheList);
					resList.addAll(resultList);
					logger.info("return cacheList["+cacheList.size()+"]+resultList["+resultList.size()+"]");
					return resList;
				}else{
					logger.info("return resultList["+resultList.size()+"]");
					return resultList;
				}
				
				
			}else{
				logger.info("return cacheList["+(cacheList==null?0:cacheList.size())+"]");
				return cacheList;
			}
		}else{
			logger.info("redis list cache HIT, key=" + key + ", start=" + start + ", count=" + count);
            
			return cacheList;
		}
    }
    
    
    public static void main(String[] args) {
//    	System.out.println(JedisUtil.getJedis().flushAll());
    	System.out.println(JedisUtil.getJedis().get("follow_user_video_list_50cf59c3803494517500007e"));
    	
    	
    	/*
    	
		List<TestObj> list = new RedisList<TestObj>(){

			@Override
			public List<TestObj> listBuilder(int start, int count) {
				System.out.println("start="+start+", count="+count);
				List<TestObj> list = new ArrayList<TestObj>();
				for (int i=start; i< (start+10); i++){
					list.add(new TestObj(i,"测试"+i));
				}
				return list;
			}
			
		}.get(TestObj.class, "test", 37, 20, -1);
		
		for(TestObj testObj : list){
			System.out.println(testObj.getId()+","+testObj.getName());
		}
		
		System.out.println(JedisUtil.getLengthOfList("test"));
		*/
		
	}



}
