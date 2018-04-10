package one.show.common.baidu.aliyun;

import java.util.HashMap;
import java.util.Map;

import one.show.common.HttpsUtil;

public class IpInfo {
	
	/**
	 * 根据IP获取城市、运营商
	 * @param ip
	 * @return
	 */
	public static String getIpInfo(String ip){
		String url = "https://dm-81.data.aliyun.com//rest/160601/ip/getIpInfo.json";
	    String appcode = "03df32b1239e489a8a43f6cbab50c866";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);

	    try {
	    	
	    	url += "?ip="+ip;
	    	String response = HttpsUtil.get(url, headers);
	    	return response;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null; 
	}
	
	public static void main(String[] args) {
		System.out.println(getIpInfo("36.110.113.142"));
	}

}
