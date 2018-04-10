
package one.show.common.cdn;

import java.io.IOException;

import one.show.common.Fetch;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Haliaeetus leucocephalus  2016年6月28日 下午2:16:42
 *
 * 
 */

public class WCNGBSDK {
	

	private RequestConfig requestConfig = RequestConfig.custom()  
            .setSocketTimeout(2000)  
            .setConnectTimeout(2000)  
            .setConnectionRequestTimeout(2000)  
            .build();  
	
	public static HttpClient httpClient = HttpClients.createDefault();
	
	private static Logger log = LoggerFactory.getLogger(Fetch.class);

	private Integer statusCode = 0;

	public Integer getStatusCode() {
		return statusCode;
	}
	
	/**
	 * 
	 * @param wcUrl 推流地址/观看地址
	 * @param ip	客户端IP
	 * @param type	2 http  3rtmp
	 * @return
	 * @throws Exception
	 */
	public String ngbRequest(String wcUrl, String ip, Integer type) throws Exception{

		String url = "http://sdkoptedge.chinanetcenter.com";
		long begin = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(url);
		String ngbUrl = null;
		HttpResponse res = null;
		int code = 0;
		try {
			httpGet.setHeader("WS_URL", wcUrl);
			httpGet.setHeader("WS_RETIP_NUM", "1");
			httpGet.setHeader("WS_URL_TYPE", String.valueOf(type));
			httpGet.setHeader("UIP", ip);

			httpGet.setConfig(requestConfig);  
			res = httpClient.execute(httpGet);
			code = res.getStatusLine().getStatusCode();
			ngbUrl = new String(EntityUtils.toByteArray(res.getEntity()), "utf-8");
			if (ngbUrl != null){
				ngbUrl = ngbUrl.replaceAll("\n", "");
			}
			
		} catch (ClientProtocolException e) {
			log.error("HttpGet abort : " + e.getMessage());
			httpGet.abort();
			throw e;
		} catch (IOException e) {
			log.error("HttpGet abort : " + e.getMessage());
			httpGet.abort();
			throw e;
		} finally {
			try {
				if (res != null) {
					log.debug("HttpResponse consumeContent");
					res.getEntity().consumeContent();
				}
			} catch (IOException e) {
				log.error("-------> Release HTTP connection exception:", e);
			}
		}

		long end = System.currentTimeMillis();

		Header[] headers = httpGet.getAllHeaders();
		String header = "";
		for(int i=0; i<headers.length; i++){
			header += headers[i].getName()+"="+headers[i].getValue()+",";
		}
		log.info("HttpGet==>" + url + "header:"+header+" [time=" + (end - begin) + "ms, code="
				+ code + "]");
		log.info("ngbUrl="+ngbUrl);
		this.statusCode = code;
		return ngbUrl;
	}
	
	public static String getNGBRtmp(String rtmp){
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String url = new WCNGBSDK().ngbRequest("http://pull.souyu.tv/show/762927950176845824_1470731172.flv", "223.104.38.174", 2);
		url = url.replaceAll("\n", "");
		System.out.println(url);
	}
}


