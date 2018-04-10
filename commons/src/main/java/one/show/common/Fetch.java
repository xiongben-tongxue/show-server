package one.show.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fetch {

	

	private RequestConfig requestConfig = RequestConfig.custom()  
            .setSocketTimeout(10000)  
            .setConnectTimeout(10000)  
            .setConnectionRequestTimeout(10000)  
            .build();  
	
	public static HttpClient httpClient = HttpClients.createDefault();
	
	private static Logger log = LoggerFactory.getLogger(Fetch.class);

	private Integer statusCode = 0;

	public Integer getStatusCode() {
		return statusCode;
	}

	public String read(InputStream is, String encode) {
		try {
			if (is != null) {
				int read = 0;
				byte[] bytes = new byte[10 * 1024];
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

				while ((read = is.read(bytes)) >= 0) {
					byteArrayOutputStream.write(bytes, 0, read);

				}
				is.close();

				return new String(byteArrayOutputStream.toByteArray(), encode);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	public String get(String url, String encode) {

		long begin = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(url);
		String jsonStr = "";
		HttpResponse res = null;
		int code = 0;
		try {
			httpGet.setHeader("Content-Type", "text/html; charset=" + encode);
			httpGet.setHeader("Connection", "close");

			httpGet.setConfig(requestConfig);  
			res = httpClient.execute(httpGet);
			code = res.getStatusLine().getStatusCode();
			jsonStr = new String(EntityUtils.toByteArray(res.getEntity()),
					encode);

		} catch (ClientProtocolException e) {
			log.error("HttpGet abort : " + e.getMessage());
			httpGet.abort();
		} catch (IOException e) {
			log.error("HttpGet abort : " + e.getMessage());
			httpGet.abort();
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

		log.info("HttpGet==>" + url + " [time=" + (end - begin) + "ms, code="
				+ code + "]");

		this.statusCode = code;
		return jsonStr;
	}

	public String get(String url) {
		return get(url, "UTF-8");
	}

	/**
	 * post方法 author danielwang
	 * 
	 * @param url
	 * @param properties
	 * @return
	 */
	public String post(String url, Map<String, String> properties, String encode) {
		return post(url,null, properties,encode);
	}
	public String post(String url,Map<String, String> header, Map<String, String> properties, String encode) {
		long begin = System.currentTimeMillis();
		String rs = "";
		HttpPost post = new HttpPost(url);
		HttpEntity entity = null;
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		try {
			if(header!=null){
				for(String key:header.keySet()){
					post.addHeader(key, header.get(key));
				}
			}

			for (Map.Entry<String, String> entry : properties.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}

			post.setEntity(new UrlEncodedFormEntity(params, encode));
			
			post.setConfig(requestConfig); 
			HttpResponse rsp = httpClient.execute(post);

			entity = rsp.getEntity();
			rs = new String(EntityUtils.toByteArray(entity), encode);
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(e.getMessage(), e);
			post.abort();
		} finally {
			if (entity != null)
				try {
					EntityUtils.consume(entity);
				} catch (IOException e) {

				}
		}

		return rs;
	}

	/**
	 * post方法 author: danielwang
	 * 
	 * @param url
	 * @param properties
	 * @return
	 */
	public String post(String url, Map<String, String> properties) {
		return post(url, properties, HTTP.UTF_8);
	}
	
	public String post(String url,Map<String, String> header, Map<String, String> properties) {
		return post(url,header, properties, HTTP.UTF_8);
	}

	/**
	 * XML BODY方式HTTP请求
	 * 
	 * @param url
	 * @param xmlStr
	 * @return
	 */
	public String postXml(String url, String xmlStr, String encode) {
		if (encode == null || encode.equals("")) {
			encode = HTTP.UTF_8;
		}
		String rs = "";
		HttpPost httppost = new HttpPost(url);
		HttpEntity resEntity = null;
		try {
			StringEntity myEntity = new StringEntity(xmlStr, encode);
			httppost.addHeader("Content-Type", "text/xml");
			httppost.setEntity(myEntity);
			httppost.setConfig(requestConfig); 
			HttpResponse response = httpClient.execute(httppost);
			resEntity = response.getEntity();
			// rs=new
			// String(StreamUtils.getBytes(resEntity.getContent()),encode);
			rs = new String(EntityUtils.toByteArray(resEntity), encode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			httppost.abort();
		} finally {
			if (resEntity != null)
				try {
					EntityUtils.consume(resEntity);
				} catch (IOException e) {

				}
		}
		return rs;
	}

	public HttpResponse getRes(String url) {
		
		HttpGet httpGet = new HttpGet(url);
		HttpResponse res = null;
		try {
			httpGet.setHeader("Content-Type", "text/html; charset=UTF-8");
			httpGet.setHeader("Connection", "close");
			httpGet.setConfig(requestConfig); 
			res = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			log.error("HttpGet abort : " + e.getMessage());
			httpGet.abort();
		} catch (IOException e) {
			log.error("HttpGet abort : " + e.getMessage());
			httpGet.abort();
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
		return res;
	}

	/**
	 * 重载无编码请求
	 * 
	 * @param url
	 * @param xmlStr
	 * @return
	 */
	public String postXml(String url, String xmlStr) {
		return postXml(url, xmlStr, null);
	}
	
	public static void main(String[] args) {
		Fetch fetch = new Fetch();
		String json = fetch.get("http://service.ingkee.com/api/live/gettop?imsi=&uid=11590577&proto=5&idfa=41FBD56F-A4CA-49AB-AAA7-1B9791097D89&lc=0000000000000025&cc=TG0001&imei=&sid=20uRlmauouzU68Fm5zW5BPQ4JKceY5ubHCh9i1gu3pMEi2A6NJki3&cv=IK3.0.20_Iphone&devi=48e84496440b05e363aa374421ed326b9d77a823&conn=Wifi&ua=iPhone&idfv=99739E68-5241-4C8C-83D8-407C71259899&osversion=ios_9.200000&count=5&multiaddr=1");
		System.out.println(json);
	}
}
