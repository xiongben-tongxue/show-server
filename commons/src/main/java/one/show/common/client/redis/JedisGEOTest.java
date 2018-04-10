package one.show.common.client.redis;

import java.util.List;

import redis.clients.jedis.ShardedJedis;

/***
 * geo测试
 * （位置的服务）
 * 
 * jedis没有提供api，但可以使用lua脚本执行 jedis命令
 * @author wangany
 *
 */
public class JedisGEOTest {

    private ShardedJedis jedis;

    public JedisGEOTest() {
        jedis = JedisUtil.getJedis();
    }
    //

    public static void main(String[] args) throws Exception {
        JedisGEOTest jedisGEOTest = new JedisGEOTest();
//        jedisGEOTest.geoADD("beijing", 116.312014, 39.963019,"haidian");
//        jedisGEOTest.geoADD("beijing", 116.227501, 39.90858,"shijingshan");
//        jedisGEOTest.geoADD("beijing", 116.297641, 39.861631,"fengtai");
//        jedisGEOTest.geoADD("beijing", 116.428146, 39.9316,"dongcheng");
//        jedisGEOTest.geoADD("beijing", 116.375829, 39.920091,"xicheng");
//        jedisGEOTest.geoADD("beijing", 116.110793, 39.943992,"mentougou");
//        jedisGEOTest.geoADD("beijing", 116.480464, 39.95948,"caoyang");
//        jedisGEOTest.geoADD("beijing", 116.663862, 39.916107,"tongzhou");
//        jedisGEOTest.geoADD("beijing", 116.349383, 39.729911,"daxing");
//        jedisGEOTest.geoADD("beijing", 116.157361, 39.748109,"fangshan");
//        jedisGEOTest.geoADD("beijing", 116.662137, 40.134017,"sunyi");
//        jedisGEOTest.geoADD("beijing", 116.2367, 40.224862,"changping");
//        jedisGEOTest.geoADD("beijing", 117.141617, 40.14196,"pinggu");
//        jedisGEOTest.geoADD("beijing", 116.64144, 40.316466,"huairou");
//
//
//         //某成员的坐标
//         System.out.println(jedisGEOTest.geoPos("beijing","haidian"));
//         //两成员之间的距离
//         System.out.println(jedisGEOTest.geoDist("beijing","haidian","shijingshan","km"));
//         //附近的成员
//         System.out.println(jedisGEOTest.geoNearByMembersByDistance("beijing","dongcheng",10,"km",true));
//
//
//         System.out.println(jedisGEOTest.geoRadius("beijing",116.421822, 39.906809,10,"km",false));
//         System.out.println(jedisGEOTest.geoHash("beijing","haidian"));
    }
    /**
     * 添加geo
     * @param key 
     * @param longitude
     * @param latitude
     * @param name 位置名称
     * @return
     */
//    public  Long geoADD(String key,double longitude,double latitude,String eleName){
//        String[] params = new String[]{key,String.valueOf(longitude),String.valueOf(latitude),eleName};
//        int keyCount = 4;
//        return (Long)jedis.eval("return redis.call('GEOADD',KEYS[1],KEYS[2],KEYS[3],KEYS[4])", keyCount,params);
//    }

    /**
     * 查询2位置距离
     * @param key
     * @param d1
     * @param d2
     * @param unit
     * @return
     */
//    public  Double geoDist(String key,String d1,String d2,String unit){
//        return Double.valueOf((String)jedis.eval("return redis.call('GEODIST',KEYS[1],KEYS[2],KEYS[3],KEYS[4])",4, key,d1,d2,unit));
//    }

    /**
     * 查询位置的geohash
     * @param key
     * @param dName
     * @return
     */
//    public  String geoHash(String key,String dName){
//
//         Object data = jedis.eval("return redis.call('GEOHASH',KEYS[1],KEYS[2])", 2, new String[]{key,dName});//GEOPOS 也可以？
//          List resultList = (List)data;
//          if(resultList!=null&&resultList.size() > 0){
//              return (String)resultList.get(0);
//          }
//          return null;
//    }

    /**
     * 查询位置坐标
     * @param key
     * @param dName
     * @return
     */
//    public  List geoPos(String key,String dName){
//         Object data = jedis.eval("return redis.call('GEOPOS',KEYS[1],KEYS[2])", 2, key,dName);
//          List<List> resultList = (List<List>)data;
//          if(resultList!=null&&resultList.size() > 0){
//              return resultList.get(0);
//          }
//          return null;
//    }

    /**
     * 查询附近坐标地址
     * @param key
     * @param longitude
     * @param latitude
     * @param unit
     * @param asc
     * @return
     */
//    public  List geoRadius(String key,double longitude,double latitude,int radius,String unit,boolean asc){
//         Object data = jedis.eval("return redis.call('GEORADIUS',KEYS[1],KEYS[2],KEYS[3],KEYS[4],KEYS[5],KEYS[6])", 6, key,String.valueOf(longitude),
//                 String.valueOf(latitude),String.valueOf(radius),unit,asc?"ASC":"DESC");
//          return (List)data;
//    }

    /**
     * 附近的成员们，根据距离
     * 
     * 根据位置查询附近点
     * @param key
     * @param dName
     * @param unit
     * @param asc
     * @return
     */
//    public  List geoNearByMembersByDistance(String key,String dName,int radius,String unit,boolean asc){
//         Object data = jedis.eval("return redis.call('GEORADIUSBYMEMBER',KEYS[1],KEYS[2],KEYS[3],KEYS[4],KEYS[5])", 5, key,dName,String.valueOf(radius),unit,asc?"ASC":"DESC");
//         return (List)data;
//    }


}
