package one.show.common.cdn;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import one.show.common.JacksonUtil;
import one.show.common.MD5;
import one.show.common.TimeUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public class WCSSafeLinkUtil {

	private static final Logger log = LoggerFactory.getLogger(WCSSafeLinkUtil.class);
	
	public static final String key = "#";
	
	public final static String BUCKET_VIDEO = "";
	public final static String BUCKET_IMG =	"";
	public static final String[] WULI_VIDEO_DOMAINS = {""};
	public static final String[] showone_IMG_DOMAINS = {""};

	public static Map<String, String> domainToBucket = new ConcurrentHashMap<String, String>();
	public static Map<String, List<String>> safeDomainList = new ConcurrentHashMap<String, List<String>>();
	
	static{
		for(String domain:WULI_VIDEO_DOMAINS){
			domainToBucket.put(domain, BUCKET_VIDEO);
		}
		for(String domain:showone_IMG_DOMAINS){
			domainToBucket.put(domain, BUCKET_IMG);
		}
		safeDomainList.put(BUCKET_VIDEO, ImmutableList.of(""));
		safeDomainList.put(BUCKET_IMG, ImmutableList.of(""));
	}
	
	public static void initSafeDomainList(String domainString){
		if(StringUtils.isEmpty(domainString)){
			return;
		}
		Map map = JacksonUtil.readJsonToObject(Map.class, domainString);
		List img_domains = (List)map.get(BUCKET_IMG);
		if(img_domains!=null&&!img_domains.isEmpty()){
			safeDomainList.put(BUCKET_IMG, img_domains);
		}
		List video_domains = (List)map.get(BUCKET_VIDEO);
		if(video_domains!=null&&!video_domains.isEmpty()){
			safeDomainList.put(BUCKET_VIDEO, video_domains);
		}
	}
	
	
	public static String getSafeLink(String url,String ip){
		if(ip.startsWith("172.100")){
			return url;
		}
		String resultUrl = url;
		URL link = null;
		try {
			link = new URL(url);
		} catch (MalformedURLException e) {
			log.error("url parse error.url="+url);
		}
		if(link==null){
			return url;
		}
		String bucket = domainToBucket.get(link.getHost());
		if(bucket==null){
			return url;
		}
		List<String> safeDomains = safeDomainList.get(bucket);
		if(safeDomains!=null&&!safeDomains.isEmpty()){
			String safeDomian = safeDomains.get((int)(System.currentTimeMillis()%safeDomains.size()));
			
			String shortUrl = url.substring(url.lastIndexOf("/"));
			String time = Integer.toHexString(TimeUtil.getCurrentTimestamp()).toUpperCase();
			String str =shortUrl + key + time + ip;
			String secret = MD5.md5(str);
			resultUrl = "http://"+safeDomian + "/"+ secret + "/" + time + shortUrl+"?ip="+ip;
		}
		return resultUrl;
	}
	
	public static void main(String[] args) {
	}

}
