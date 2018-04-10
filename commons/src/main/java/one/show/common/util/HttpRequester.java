package one.show.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequester {
	private String defaultContentEncoding;

	public HttpRequester() {
		this.defaultContentEncoding = "UTF-8";
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws Exception
	 */
	public String sendPost(String urlString, String params,
			Map<String, String> propertys) throws Exception {

		HttpURLConnection conn = null;
		URL url = new URL(urlString);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		if (propertys != null)
			for (String key : propertys.keySet()) {
				conn.addRequestProperty(key, propertys.get(key));
			}
		OutputStreamWriter objOutputStrm = new OutputStreamWriter(
				conn.getOutputStream());
		objOutputStrm.write(params);
		objOutputStrm.flush();
		conn.connect();
		InputStream inputStream = conn.getInputStream();
		byte buff[] = readStream(inputStream);
		String recvstring = new String(buff, this.defaultContentEncoding);
		conn.disconnect();
		return recvstring;

	}
	/**
	 * anySDK用post方法
	 * @param loginCheckUrl
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public String anySendPost(String loginCheckUrl,String queryString)throws Exception{
		URL url = new URL( loginCheckUrl );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty( "User-Agent", "px v1.0" );
        conn.setReadTimeout(30 * 1000);
        conn.setConnectTimeout(30 * 1000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8") );
        writer.write( queryString );
        writer.flush();
        writer.close();
        os.close();
        conn.connect();
        InputStream is = conn.getInputStream();
        String result = stream2String( is ); 
        return result;
	}
	/**
	 * 获取流中的字符串
	 * @param is
	 * @return
	 */
	private String stream2String( InputStream is )throws Exception {
		BufferedReader br = null;
		br = new BufferedReader( new java.io.InputStreamReader( is ));	
		String line = "";
		StringBuilder sb = new StringBuilder();
		while( ( line = br.readLine() ) != null ) {
			sb.append( line );
		}
		br.close();
		return sb.toString();
	}
	public String sendGet(String urlString) throws Exception {
		HttpURLConnection conn = null;
		URL url = new URL(urlString);
		conn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = conn.getInputStream();
//		byte buff[] = readStream(inputStream);
//		String recvstring = new String(buff, this.defaultContentEncoding);
//		return recvstring;
		return readStremReader(inputStream);
	}

	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		int len = -1;
		if ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		return outSteam.toByteArray();
	}

	public static String readStremReader(InputStream inStream) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inStream));
		// response
		StringBuilder responseContect = new StringBuilder();
		for (String line = ""; (line = reader.readLine()) != null;)
			responseContect.append(line);
		reader.close();

		return responseContect.toString();
	}

}
