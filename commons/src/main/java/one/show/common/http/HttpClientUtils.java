package one.show.common.http;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientUtils {

	public static CloseableHttpClient httpClient;
	public static CloseableHttpClient httpsClient;
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	static{
		//从链接池中获取一个连接
		httpClient = HttpClientPoolUtils.getHttpClient();
		httpsClient = HttpsClientPoolUtils.getHttpClient();
	}
	
	public static String execute(HttpUriRequest request,boolean isHttps) throws Exception {
		CloseableHttpResponse response = null;
		try {
			if(isHttps){
				response = httpsClient.execute(request);
			}else{
				response = httpClient.execute(request);
			}
			HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
		} catch (Exception e) {
			throw e;
		}finally{
			if(response!=null)
			response.close();
		}
	}
	
	public static InputStream executeCallBackInputStream(HttpUriRequest request,boolean isHttps) throws Exception {
		CloseableHttpResponse response = null;
		try {
			if(isHttps){
				response = httpsClient.execute(request);
			}else{
				response = httpClient.execute(request);
			}
			HttpEntity entity = response.getEntity();
			return entity.getContent();
		} catch (Exception e) {
			throw e;
		}finally{
			if(response!=null)
				response.close();
		}
	}
	
	/**
	 * post请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static InputStream sendPostCallBackInputStream(String url,String paramet){
		return sendPostCallBackInputStream(url,paramet,"application/x-www-form-urlencoded; charset=UTF-8");
	}
	
	/**
	 * post请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String url,String paramet){
		return sendPost(url,paramet,"application/x-www-form-urlencoded; charset=UTF-8");
	}
	/**
	 * post请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String sendPostJson(String url,String paramet){
		return sendPost(url,paramet,"application/json; charset=UTF-8");
	}
	
	/**
	 * post请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static InputStream sendPostCallBackInputStream(String url,String paramet,String contentType){
		HttpPost httpPost = new HttpPost(url);
		StringEntity httpEntity = new StringEntity(paramet,"UTF-8");
		httpEntity.setContentType(contentType);
		httpPost.setEntity(httpEntity);
		InputStream inputStream = null;
		try {
			long startTime = System.currentTimeMillis();
			inputStream = executeCallBackInputStream(httpPost,isHttps(url));
			long endTime = System.currentTimeMillis();
			logger.info(""+(endTime-startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * get请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static InputStream sendGetCallBackInputStream(String url){
		HttpGet httpGet = new HttpGet(url);
		InputStream inputStream = null;
		try {
			long startTime = System.currentTimeMillis();
			inputStream = executeCallBackInputStream(httpGet,isHttps(url));
			long endTime = System.currentTimeMillis();
			logger.info("sendGet:"+(endTime-startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * post请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String url,String paramet,String contentType){
		HttpPost httpPost = new HttpPost(url);
		StringEntity httpEntity = new StringEntity(paramet,"UTF-8");
		httpEntity.setContentType(contentType);
		httpPost.setEntity(httpEntity);
		String json = "";
		try {
			long startTime = System.currentTimeMillis();
			json = execute(httpPost,isHttps(url));
			long endTime = System.currentTimeMillis();
			logger.info("json:::"+json);
			logger.info("POST::"+(endTime-startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * get请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String sendGet(String url){
		HttpGet httpGet = new HttpGet(url);
		String json = "";
		try {
			long startTime = System.currentTimeMillis();
			json = execute(httpGet,isHttps(url));
			long endTime = System.currentTimeMillis();
			logger.info("json:::"+json);
			logger.info("sendGet:"+(endTime-startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	private static boolean isHttps(String url){
		boolean isHttps = false;
		if(url.startsWith("https")){
			isHttps = true;
		}
		return isHttps;
	}

	/**
	 * url加密
	 * @param param
	 * @return
	 */
	public static String urlEncoder(String param){
		try {
			String result= param;
			if(StringUtils.isNotBlank(param)){
				result = URLEncoder.encode(param, "utf-8");
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	/**
	 * url解密
	 * @param param
	 * @return
	 */
	public static String urlDecoder(String param){
		try {
			String result= param;
			if(StringUtils.isNotBlank(param)){
				result = URLDecoder.decode(param, "utf-8");
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	
}
