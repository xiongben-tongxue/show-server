package one.show.common;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JacksonUtil {

	private static ObjectMapper instance;

	public static ObjectMapper getObjectMapper() {
		if (instance == null) {
			instance = new ObjectMapper();
		}
		return instance;
	}

	/**
	 * 讲对象写成json字符串
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String writeToJsonString(Object obj){
		Writer w = new StringWriter();
		ObjectMapper mapper = getObjectMapper();
		try {
			mapper.writeValue(w, obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return w.toString();
	}
	
	/**
	 * 将json字符串读成map格式
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static <T> T readJsonToObject(Class<T> valueType, String jsonStr) {
		ObjectMapper mapper = getObjectMapper();
		mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			return mapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将json字符串读成List格式
	 * @param valueType
	 * @param jsonStr
	 * @return
	 */
	public static <T> List<T> readJsonToList(TypeReference<List<T>> valueType,String jsonStr){
		ObjectMapper mapper = getObjectMapper();
		try {
			return mapper.readValue(jsonStr,valueType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		String jsonStr = "[{\"effectName\":\"粉嫩\",\"endTimeInterval\":1442760349.018913,\"startTimeInterval\":1442760338.548249,\"effectID\":31}]";
		List<Map<String, Object>> list = JacksonUtil.readJsonToObject(List.class, jsonStr);
		for (Map<String, Object> map : list) {
			System.out.println(map.get("effectName"));
		}
		
		String str = "{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"105754052340^1107805227002\",\"success\":true},\"request_id\":\"3acbu212ph5k\"}}";
		Map<String, Object> json = JacksonUtil.readJsonToObject(Map.class, str);
		System.out.println(json);
		
		String test = "{\"0\":[{\"id\":1,\"personNum\":50000,\"coin\": 50000},{\"id\":2,\"personNum\":100000,\"coin\":888}]}";
		Map map =JacksonUtil.readJsonToObject(Map.class,test);
		System.out.println((List<Map<String, Object>>)map.get("0"));
		Map map2 = new HashMap();
		map2.put("1", "121");
		map2.put("2", "133");
		List list2 = new ArrayList();
		list2.add(map2);
		System.out.println(list2);
    }
}
