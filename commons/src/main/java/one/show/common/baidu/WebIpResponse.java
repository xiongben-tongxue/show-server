package one.show.common.baidu;

import one.show.common.JacksonUtil;
import one.show.common.TypeUtil;

public class WebIpResponse {

	public String address;
	public IPLocation content;
	public int status;
	public String message;
	

	public static void main(String[] args) {
//		String jsonStr = "{\"address\":\"CN|\u798f\u5efa|\u53a6\u95e8|None|CHINANET|0|0\",\"content\":{\"address\":\"\u798f\u5efa\u7701\u53a6\u95e8\u5e02\",\"address_detail\":{\"city\":\"\u53a6\u95e8\u5e02\",\"city_code\":194,\"district\":\"\",\"province\":\"\u798f\u5efa\u7701\",\"street\":\"\",\"street_number\":\"\"},\"point\":{\"x\":\"118.10388605\",\"y\":\"24.48923061\"}},\"status\":0}";
		String jsonStr = "{\"status\":200,\"message\":\"APP涓嶅瓨鍦紝AK鏈夎璇锋鏌ュ啀閲嶈瘯\"}";
		System.out.println(TypeUtil.typeToString("", JacksonUtil.readJsonToObject(WebIpResponse.class, jsonStr)));
	}
	
}
