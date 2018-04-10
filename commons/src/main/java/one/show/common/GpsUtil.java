package one.show.common;

import java.util.HashMap;
import java.util.Map;

public class GpsUtil {
	private static double rad(double d)
	{
	 return d * Math.PI / 180.0;
	}
	/**
	 * 获取当前用户的gps信息
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static Map<String,Object> getUserGps(double lat,double lon){
		Map<String,String> map = new HashMap<String,String>();
		map.put("ak","AtyV5LwPHcz8UGRZbeIMvMPrY1bUHr4u");
		map.put("callback", "renderReverse");
		map.put("output","json");
		map.put("location", lat+","+lon);
		map.put("output","json");
		map.put("pois","1");
		//http://api.map.baidu.com/geocoder/v2/?ak=AtyV5LwPHcz8UGRZbeIMvMPrY1bUHr4u&callback=renderReverse&location=39.983424,116.322987&output=json&pois=1
		String jsonStr=new Fetch().post("http://api.map.baidu.com/geocoder/v2/", map);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap=JacksonUtil.readJsonToObject(Map.class,jsonStr);
		return resultMap;
	}
	
	/**  
     * 生成以中心点为中心的四方形经纬度  
     *   
     * @param lat 纬度  
     * @param lon 精度  
     * @param raidus 半径（以米为单位）  
     * @return  
     */    
    public static double[] getAround(double lat, double lon, int raidus) {    
    
        Double latitude = lat;    
        Double longitude = lon;    
    
        Double degree = (24901 * 1609) / 360.0;    
        double raidusMile = raidus;    
    
        Double dpmLat = 1 / degree;    
        Double radiusLat = dpmLat * raidusMile;    
        Double minLat = latitude - radiusLat;    
        Double maxLat = latitude + radiusLat;    
    
        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));    
        Double dpmLng = 1 / mpdLng;                 
        Double radiusLng = dpmLng * raidusMile;     
        Double minLng = longitude - radiusLng;      
        Double maxLng = longitude + radiusLng;      
        return new double[] { minLat, minLng, maxLat, maxLng };    
    }  
    
    /**  
     * 计算中心经纬度与目标经纬度的距离（米）  
     *   
     * @param centerLon  
     *            中心精度  
     * @param centerLat  
     *            中心纬度  
     * @param targetLon  
     *            需要计算的精度  
     * @param targetLat  
     *            需要计算的纬度  
     * @return 米  
     */    
    public static Double distance(Double centerLon, Double centerLat, Double targetLon, Double targetLat) {    
	    if(centerLon==null||centerLat==null||targetLon==null||targetLat==null){
	    	return null;
	    }
        double jl_jd = 102834.74258026089786013677476285;// 每经度单位米;    
        double jl_wd = 111712.69150641055729984301412873;// 每纬度单位米;    
        double b = Math.abs((centerLat - targetLat) * jl_jd);    
        double a = Math.abs((centerLon - targetLon) * jl_wd);    
        return Math.sqrt((a * a + b * b));    
    } 
    public static double Distance(double long1, double lat1, double long2,  
            double lat2) {  
        double a, b, R;  
        R = 6378137; // 地球半径  
        lat1 = lat1 * Math.PI / 180.0;  
        lat2 = lat2 * Math.PI / 180.0;  
        a = lat1 - lat2;  
        b = (long1 - long2) * Math.PI / 180.0;  
        double d;  
        double sa2, sb2;  
        sa2 = Math.sin(a / 2.0);  
        sb2 = Math.sin(b / 2.0);  
        d = 2  
                * R  
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
                        * Math.cos(lat2) * sb2 * sb2));  
        return d;  
    }  
    
    /**
    java通过经纬度计算两个点的之间的距离的算法 - dengyukun - dengyukun的博客     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
    java通过经纬度计算两个点的之间的距离的算法 - dengyukun - dengyukun的博客     * @param lng1
    java通过经纬度计算两个点的之间的距离的算法 - dengyukun - dengyukun的博客     * @param lat1
    java通过经纬度计算两个点的之间的距离的算法 - dengyukun - dengyukun的博客     * @param lng2
    java通过经纬度计算两个点的之间的距离的算法 - dengyukun - dengyukun的博客     * @param lat2
    java通过经纬度计算两个点的之间的距离的算法 - dengyukun - dengyukun的博客     * @return
    java通过经纬度计算两个点的之间的距离的算法 - dengyukun - dengyukun的博客     */
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2)
        {
          double radLat1 = rad(lat1);
           double radLat2 = rad(lat2);
           double a = radLat1 - radLat2;
         double b = rad(lng1) - rad(lng2);
          double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
           Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
          s = s * 6378137;
          s = Math.round(s * 10000) / 10000;
          return s;
       }
    public static void main(String[] args) {
		System.out.println(GetDistance(Double.parseDouble("124.8197"), Double.parseDouble("43.5149"), Double.parseDouble("116.3229"),Double.parseDouble("39.9834")));
		System.out.println((double)(807207/1000));
		System.out.println(TypeUtil.typeToString("", getUserGps(39.983424,116.322987)));
    }
}
