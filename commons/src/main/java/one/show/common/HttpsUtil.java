package one.show.common;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsUtil {
	private static Logger log = LoggerFactory.getLogger(HttpsUtil.class);
	
	
	private static RequestConfig requestConfig = RequestConfig.custom()  
            .setSocketTimeout(10000)  
            .setConnectTimeout(5000)  
            .setConnectionRequestTimeout(5000)  
            .build();  
	
	public static String get(String url, Map<String,String> header){  
        HttpClient client = new DefaultHttpClient();
        client = WebClientDevWrapper.wrapClient(client);  
        HttpGet httpGet = new HttpGet(url);
        String rs = "";  
        log.info("http byte array post : "+url);
        
        try {  
        	if(header!=null){
				for(String key:header.keySet()){
					httpGet.addHeader(key, header.get(key));
				}
			}
        	httpGet.setConfig(requestConfig); 
            HttpResponse res = client.execute(httpGet);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
                rs = new String(EntityUtils.toByteArray(entity), charset);
              
            }  
        } catch (Exception e) {  
        	httpGet.abort();
        	log.error(e.getMessage(), e);
            throw new RuntimeException(e);  
        }  
        return rs;  
    }
	public static String post(String url, Map<String,String> header, String bodys) {  
        HttpClient client = new DefaultHttpClient();
        client = WebClientDevWrapper.wrapClient(client);  
        HttpPost post = new HttpPost(url);  
        String rs = "";  
        log.info("http byte array post : "+url);
        
        try {  
        	if(header!=null){
				for(String key:header.keySet()){
					post.addHeader(key, header.get(key));
				}
			}
        	StringEntity myEntity = new StringEntity(bodys, HTTP.UTF_8);
        	post.setEntity(myEntity);
        	post.setConfig(requestConfig); 
            HttpResponse res = client.execute(post);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
                rs = new String(EntityUtils.toByteArray(entity), charset);
              
            }  
        } catch (Exception e) {  
        	post.abort();
        	log.error(e.getMessage(), e);
            throw new RuntimeException(e);  
        }  
        return rs;  
    }  
	
	public static String post(String url, byte[] data) {  
        HttpClient client = new DefaultHttpClient();
        client = WebClientDevWrapper.wrapClient(client);  
        HttpPost post = new HttpPost(url);  
        String rs = "";  
        log.info("http byte array post : "+url);
        
        try {  
        	
			post.setEntity(new ByteArrayEntity(data)); 
			post.setConfig(requestConfig); 
            HttpResponse res = client.execute(post);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
                rs = new String(EntityUtils.toByteArray(entity), charset);
              
            }  
        } catch (Exception e) {  
        	post.abort();
        	log.error(e.getMessage(), e);
            throw new RuntimeException(e);  
        }  
        return rs;  
    }  
	
	public static String post(String url, Map<String,String> properties) {  
        HttpClient client = new DefaultHttpClient();
        client = WebClientDevWrapper.wrapClient(client);  
        HttpPost post = new HttpPost(url);  
        String rs = "";  
        
        log.info("http name value pair post : "+url);
        
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

			
        try {  
        	for(Map.Entry<String, String> entry : properties.entrySet()){
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
			post.setConfig(requestConfig); 
            HttpResponse res = client.execute(post);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
                rs = new String(EntityUtils.toByteArray(entity), charset);
                log.info("https result : "+rs);
            }else{
            	 log.error("invoke failed, response status:" + res.getStatusLine().getStatusCode()+", url"+url+", params="+TypeUtil.typeToString("params", properties));
            }
        } catch (Exception e) {
        	post.abort();
            throw new RuntimeException(e);  
        }  
        return rs;  
    }  
  
    public static class WebClientDevWrapper {  
        public static HttpClient wrapClient(HttpClient base) {  
            try {  
                SSLContext ctx = SSLContext.getInstance("TLS");  
                X509TrustManager tm = new X509TrustManager() {  
                    @Override  
                    public X509Certificate[] getAcceptedIssuers() {  
                        return null;  
                    }  
  
                    @Override  
                    public void checkClientTrusted(  
                            java.security.cert.X509Certificate[] chain,  
                            String authType)  
                            throws java.security.cert.CertificateException {  
                          
                    }  
  
                    @Override  
                    public void checkServerTrusted(  
                            java.security.cert.X509Certificate[] chain,  
                            String authType)  
                            throws java.security.cert.CertificateException {  
                          
                    }  
                };  
                ctx.init(null, new TrustManager[] { tm }, null);  
                SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
                ClientConnectionManager ccm = base.getConnectionManager();  
                SchemeRegistry sr = ccm.getSchemeRegistry();  
                sr.register(new Scheme("https", 443, ssf));  
                return new DefaultHttpClient(ccm, base.getParams());  
            } catch (Exception ex) {  
                ex.printStackTrace();  
                return null;  
            }  
        }  
    }  

}
