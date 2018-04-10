package one.show.common.baidu;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import one.show.common.Fetch;
import one.show.common.JacksonUtil;
import one.show.common.TypeUtil;
import one.show.common.util.URLRequestUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinanetcenter.api.util.StringUtil;

public class BaiduApi {
	public static final Logger log = LoggerFactory.getLogger(BaiduApi.class);
	
	public static final String AK="vGfEAU4dKDFakbvR4ShAEAl66ZatbS71";
	public static final String SK="GyHccO34W4zNYfyMfk4pNcEy97zs4dMc";
	
	public static IPLocation getIpLocation(String ip){
		IPLocation location = null;
		// 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；post请使用TreeMap保存<key,value>，该方法会自动将key按照字母a-z顺序排序。所以get请求可自定义参数顺序（sn参数必须在最后）发送请求，但是post请求必须按照字母a-z顺序填充body（sn参数必须在最后）。以get请求为例：http://api.map.baidu.com/geocoder/v2/?address=百度大厦&output=json&ak=yourak，paramsMap中先放入address，再放output，然后放ak，放入顺序必须跟get请求中对应参数的出现顺序保持一致。
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("coor", "bd09ll");
		paramsMap.put("ip", ip);
		paramsMap.put("ak", AK);
		
		try {
			paramsMap.put("sn", SnCal.getSn("/location/ip?", paramsMap));
			String rspStr = URLRequestUtil.getRequestStr("https://api.map.baidu.com/location/ip?"+SnCal.toQueryString(paramsMap));
			WebIpResponse response = JacksonUtil.readJsonToObject(WebIpResponse.class, rspStr);
			if(response.status==0){
				location = response.content;
			}else{
				log.error("getLocationByIp error.code="+response.status+",message="+response.message);
			}
		} catch (UnsupportedEncodingException e) {
			log.error("getSn error.",e);
		}
		return location;
	}
	
	/**
	 * 获取当前用户的gps信息
	 * @param lat
	 * @param lon
	 * @return
	 */
	public static Map<String,Object> getUserGps(double lat,double lon){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String,String> map = new LinkedHashMap<String,String>();
			map.put("ak",AK);
			map.put("callback", "renderReverse");
			map.put("location", lat+","+lon);
			map.put("output","json");
			map.put("pois","1");
			map.put("sn", SnCal.getSn("/geocoder/v2/?", map));
			//http://api.map.baidu.com/geocoder/v2/?ak=AtyV5LwPHcz8UGRZbeIMvMPrY1bUHr4u&callback=renderReverse&location=39.983424,116.322987&output=json&pois=1
			String jsonStr=new Fetch().post("http://api.map.baidu.com/geocoder/v2/", map);
//			String jsonStr = URLRequestUtil.getRequestStr("http://api.map.baidu.com/geocoder/v2?"+SnCal.toQueryString(map));
			if(!StringUtil.isEmpty(jsonStr)){
				resultMap=JacksonUtil.readJsonToObject(Map.class,jsonStr);
			}
		} catch (UnsupportedEncodingException e) {
			log.error("getUserGps error.",e);
		}
		return resultMap;
	}
	
	public static void main(String[] args) {
		System.out.println(TypeUtil.typeToString("", getIpLocation("111.26.33.157")));
//		System.out.println(TypeUtil.typeToString("", getUserGps(39.983424,116.322987)));
	}

}
