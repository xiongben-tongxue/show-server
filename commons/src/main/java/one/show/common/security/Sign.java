/**
 * 
 */
package one.show.common.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import one.show.common.MD5;
import one.show.common.Constant.USER_AGENT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午6:45:48
 *
 */
public class Sign {
	
	 private static final Logger log = LoggerFactory.getLogger(Sign.class);
	 
	/**
	 * 签名生成算法
	 * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(Map<String,String> params, String secret, USER_AGENT userAgent)
	{
	    // 先将参数以其参数名的字典序升序进行排序
	    Map<String, String> sortedParams = new TreeMap<String, String>(params);
	    Set<Entry<String, String>> entrys = sortedParams.entrySet();
	 
	    // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
	    StringBuilder basestring = new StringBuilder();
	    for (Entry<String, String> param : entrys) {
	    	
	    	String value = param.getValue()==null?"":param.getValue();
	    	/*
	    	if (!value.equals("")){
	    		try {
					value = URLEncoder.encode(value, "utf-8");
				} catch (UnsupportedEncodingException e) {
				}
	    	}
	    	*/
	        basestring.append(param.getKey()).append("=").append(value);
	    }
	    basestring.append(secret);
	    String str = basestring.toString();
	    try {
	    	
	    	if (userAgent != null && userAgent == USER_AGENT.IOS){
	    		str = IOSURLEncoder.encode(str, "utf-8");
	    	}else{
	    		str = URLEncoder.encode(str, "utf-8");
	    	}
	    	
	    	
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
	    log.info("sign basestring : "+ str);
	    return MD5.md5(str);
	 
	    
	}
	
	
	public static void main(String[] args) throws IOException {
		
		String SECRET = "Knk9ss{3jMM;KD%;k8km,s;sks/.,.]ski2G9,^43*5KM./a.aNNlf/.sdfgnp==>(mskI^8*NKD::I&^(^(KDH,WND..LK*%KJD8'%73djkssj...;'][sks";
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Show-Userid", "916541832572321792");
		map.put("Show-UserToken", "368a02f17905d5ac7242851b5e7f9df76a20618b3fb2ec56f51dc44cfa7016c0");
		map.put("OS", "iOS");
		map.put("App-Version", "3.1.3");
		map.put("Kernel-Version", "11.0.1");
		map.put("Longitude", "108.735674");
		map.put("Latitude", "34.346783");
		map.put("City", "%E5%92%B8%E9%98%B3%E5%B8%82");
		map.put("Device-Uuid", "C108C419-FEF8-49C5-B1B3-A62CD1EA3536");
		map.put("Device-Name", "%E6%9B%B9%E8%94%A1%E6%B4%81");
		map.put("Phone-Type", "iPhone");
		map.put("Phone-Number", null);
		map.put("Channel", "iOS");
		map.put("Push-Id", "916541832572321792");
		map.put("Net-Type", "1");
		map.put("MAC", "02:00:00:00:00:00");
		map.put("IM", null);
		map.put("IDFA", "7232333C-3117-440F-B80F-B61D0F75E43D");
		map.put("IDFY", "C108C419-FEF8-49Cµ-B1Bs-A42CD1EA3536");
		map.put("OP", "3");
		map.put("CO", "china");
		map.put("SC", "1125.000000*2001.000000");
		map.put("VN", "3.1.3");
		map.put("TAG", null);
		map.put("Time", "1507398215");
		
		String mySign = Sign.getSignature(map, SECRET , USER_AGENT.IOS);
		System.out.println(mySign);
//		System.out.println(IOSURLEncoder.encode("~+* !'();:@&=+$,/?%#[]", "utf-8"));
//		System.out.println(URLEncoder.encode("~+* !'();:@&=+$,/?%#[]", "utf-8"));
		System.out.println(URLDecoder.decode("App-Version%3D3.1.3Show-UserToken%3D368a02f17905d5ac7242851b5e7f9df76a20618b3fb2ec56f51dc44cfa7016c0Show-Userid%3D916541832572321792CO%3DchinaChannel%3DiOSCity%3D%25E5%2592%25B8%25E9%2598%25B3%25E5%25B8%2582Device-Name%3D%25E6%259B%25B9%25E8%2594%25A1%25E6%25B4%2581Device-Uuid%3DC108C419-FEF8-49C5-B1B3-A62CD1EA3536IDFA%3D7232333C-3117-440F-B80F-B61D0F75E43DIDFY%3DC108C419-FEF8-49C%C2%B5-B1Bs-A42CD1EA3536IM%3DKernel-Version%3D11.0.1Latitude%3D34.346783Longitude%3D108.735674MAC%3D02%3A00%3A00%3A00%3A00%3A00Net-Type%3D1OP%3D3OS%3DiOSPhone-Number%3DPhone-Type%3DiPhonePush-Id%3D916541832572321792SC%3D1125.000000*2001.000000Time%3D1507398215VN%3D3.1.3Knk9ss%7B3jMM%3BKD%25%3Bk8km%2Cs%3Bsks%2F.%2C.%5Dski2G9%2C%5E43*5KM.%2Fa.aNNlf%2F.sdfgnp%3D%3D%3E%28mskI%5E8*NKD%3A%3AI%26%5E%28%5E%28KDH%2CWND..LK*%25KJD8%27%2573djkssj...%3B%27%5D%5Bsks", "utf-8"));
		
//		System.out.println(getSignature(map,"Knk9ss{3jMM;KD%;kl;jafo'jaUG9,^43*5KM./a.aNNlf/.sdfgnp==>(mskI^8*NKD::I&^(^(KDH,WND..LK*%KJD8'%73djkssj...;'][sks"));
	}


}


