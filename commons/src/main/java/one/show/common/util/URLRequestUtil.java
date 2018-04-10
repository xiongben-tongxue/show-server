package one.show.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

public class URLRequestUtil {
	//
	private static Logger logger = Logger.getLogger(URLRequestUtil.class);
	//
	public static String getRequestStr(String urlStr) {
		String inputLine = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		if(logger.isDebugEnabled()){
			logger.debug("getRequestStr urlStr=" +urlStr);
		}
		try {
			url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String str = null;
			while ((str = in.readLine()) != null) {
				inputLine += str;
			}
			in.close();
		} catch (Exception e) {
			logger.error("getRequestStr error." + e.getMessage(), e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("getRequestStr return=" +inputLine);
		}
		return inputLine;
	}
	
	public static String getPostRequestStr(String urlStr,Map<String, String> header, String param) {
		String inputLine = "";
		URL url = null;
		PrintWriter out = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("POST");
			if(header!=null){
				for(String key:header.keySet()){
					urlConnection.setRequestProperty(key, header.get(key));
				}
			}
			
			out = new PrintWriter(urlConnection.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String str = null;
			while ((str = in.readLine()) != null) {
				inputLine += str;
			}
			in.close();			
		} catch (Exception e) {
			logger.error(urlStr+"=====================\n"+param);
			logger.error("getRequestStr error." + e.getMessage(), e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return inputLine;
	}

	public static String URLEncoder(String param) {
		try {
			param = java.net.URLEncoder.encode(param, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("URLEncoder error." + e.getMessage(), e);
			return "";
		}
		return param;
	}

	public static String uniDecode(String name) {
		char[] c = name.toCharArray();
		char[] out = new char[c.length];
		name = loadConvert(c, 0, c.length, out);
		return name;
	}

	private static String loadConvert(char[] in, int off, int len,
			char[] convtBuf) {
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char) aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	public static String getRequestStrWithCookie(String urlStr,String cookie) {
		String inputLine = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		if(logger.isDebugEnabled()){
			logger.debug("getRequestStr urlStr=" +urlStr);
		}
		try {
			url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Cookie", cookie);
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-Type", "text/html");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String str = null;
			while ((str = in.readLine()) != null) {
				inputLine += str;
			}
			in.close();
		} catch (Exception e) {
			logger.error("getRequestStr error." + e.getMessage(), e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("getRequestStr return=" +inputLine);
		}
		return inputLine;
	}
	
}