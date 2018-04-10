package one.show.common;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;

public class Geo {
	
	private static Logger logger = LoggerFactory.getLogger(Geo.class);
	
	private DatabaseReader reader;
	
	private static Geo geo = new Geo();
	
	private Geo(){
		try{
			reader = new DatabaseReader.Builder(Loader.class.getResourceAsStream("/GeoLite2-City.mmdb")).build();       
			
		}catch(Exception e){
			logger.error("Exception:"+e.getMessage(), e);
		}
	}
	
	public final static Geo getInstance() {
		return geo;
	} 
	
	public String getCountryIsoCode(String ip){
		  InetAddress ipAddress;
		try {
			ipAddress = InetAddress.getByName(ip);
		     // 获取查询结果      
		      CityResponse response = reader.city(ipAddress); 
		      // 获取国家信息
		      Country country = response.getCountry();
		      return country.getIsoCode();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}     

		return null;

	}
	
	public String getCountryName(String ip){
		  InetAddress ipAddress;
		try {
			ipAddress = InetAddress.getByName(ip);
		     // 获取查询结果      
		      CityResponse response = reader.city(ipAddress); 
		      // 获取国家信息
		      Country country = response.getCountry();
		      return country.getName();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}     

		return null;
	}
	
	/**
	 * 省份
	 * @param ip
	 * @return
	 */
	public String getSubdivisionName(String ip){
		  InetAddress ipAddress;
		try {
			ipAddress = InetAddress.getByName(ip);
		     // 获取查询结果      
		      CityResponse response = reader.city(ipAddress); 
		      // 获取国家信息
		      Subdivision subdivision = response.getMostSpecificSubdivision();
		      return subdivision.getName();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}     

		return null;
	}

	
	public static void main(String[] args) throws Exception{      
//	      // 创建 GeoLite2 数据库     
//	      // 读取数据库内容   
//	      DatabaseReader reader = new DatabaseReader.Builder(Loader.class.getResourceAsStream("/GeoLite2-City.mmdb")).build();       
//	      InetAddress ipAddress = InetAddress.getByName("117.19.3.40");     
//
//	      // 获取查询结果      
//	      CityResponse response = reader.city(ipAddress);     
//
//	      // 获取国家信息
//	      Country country = response.getCountry();
//	      System.out.println(country.getIsoCode());               // 'CN'
//	      System.out.println(country.getName());                  // 'China'
//	      System.out.println(country.getNames().get("zh-CN"));    // '中国'
//
//	      // 获取省份
//	      Subdivision subdivision = response.getMostSpecificSubdivision();
//	      System.out.println(subdivision.getName());   // 'Guangxi Zhuangzu Zizhiqu'
//	      System.out.println(subdivision.getIsoCode()); // '45'
//	      System.out.println(subdivision.getNames().get("zh-CN")); // '广西壮族自治区'
//
//	      // 获取城市
//	      City city = response.getCity();
//	      System.out.println(city.getName()); // 'Nanning'
//	      Postal postal = response.getPostal();
//	      System.out.println(postal.getCode()); // 'null'
//	      System.out.println(city.getNames().get("zh-CN")); // '南宁'
//	      Location location = response.getLocation();
//	      System.out.println(location.getLatitude());  // 22.8167
//	      System.out.println(location.getLongitude()); // 108.3167
		
		Geo.getInstance().getCountryIsoCode("");

	}  

}
