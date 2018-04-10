package one.show.common.ai;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.Fetch;
import one.show.common.HttpsUtil;
import one.show.common.JacksonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;

public class Face {
	
	private static Logger log = LoggerFactory.getLogger(Face.class);
	
	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}
	
	/**
	 * 
	 * @param imageStr 图片内容BASE64编码
	 * @return
	 */
	public static Feature ageDetection(String imageStr){
		
		Feature feature = new Feature();
	    try {
			 String appcode = "03df32b1239e489a8a43f6cbab50c866";
			 Map<String, String> headers = new HashMap<String, String>();
			 //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			 headers.put("Authorization", "APPCODE " + appcode);
			 //根据API的要求，定义相对应的Content-Type
			 headers.put("Content-Type", "application/json; charset=UTF-8");
			    
			 String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\""+imageStr+"\"}}]}";
			 String url = "https://dm-23.data.aliyun.com/rest/160601/face/age_detection.json";
			 String res = HttpsUtil.post(url, headers, bodys);
			 log.info("res : "+res);
			 
			 Map map = JacksonUtil.readJsonToObject(Map.class, res);
			 
			 if (map != null){
				List<Map> list = (ArrayList<Map>)map.get("outputs"); 
				Map featureMap = list.get(0);
				Map outputValueMap = (Map)featureMap.get("outputValue");
				Map dataValueMap = JacksonUtil.readJsonToObject(Map.class, (String)outputValueMap.get("dataValue"));
				
				if (dataValueMap != null){
					if (((Integer)dataValueMap.get("errno")) == 0){
						feature.setNumber(((Integer)dataValueMap.get("number")));
						feature.setAges(((List<Integer>)dataValueMap.get("age")));
						feature.setRect(((List<Integer>)dataValueMap.get("rect")));
					}
				}
			 }
			 
	    } catch (Exception e) {
	    	log.error(e.getMessage(), e);
	    }
	    
	    return feature;
		
	}
	
	
	public static void main(String[] args) {
		 String imageStr = getImageStr("/Users/zw/Downloads/59e59cad8c19b0e4638fc66b.jpg");
//		 ageDetection(imageStr);
		 
		 Fetch fetch = new Fetch();
		 Map properties = new HashMap();
		 properties.put("image", imageStr);
		 String res = fetch.post("http://video-test.inner.seeyou.hifun.mobi/detection/face", properties);
		 System.out.println("==========="+res);
	}

}
