package one.show.common.http;

import java.nio.charset.CodingErrorAction;
import java.util.concurrent.TimeUnit;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;


public class HttpClientPoolUtils {

	//httpClient 链接池
	private static PoolingHttpClientConnectionManager connManager;
	
	private static ConnectionConfig connectionConfig;//链接配置

	private static int maxConLifeTimeMs = 300000;//链接最大存活时间
	private static int defaultMaxConPerHost = 100;
	private static int maxPerRoute = 200;
	private static int maxTotalConn = 10000;
//	private static SocketConfig socketConfig;
	
	//初始化链接池，并且设置链接池参数
	static{
		
		//初始化链接池管理
		connManager = new PoolingHttpClientConnectionManager(maxConLifeTimeMs,TimeUnit.MILLISECONDS);
		// Create socket configuration
//        socketConfig = SocketConfig.custom()
//            .setTcpNoDelay(true)
//            .build();
//        // Configure the connection manager to use socket configuration either
//        // by default or for a specific host.
//        connManager.setDefaultSocketConfig(socketConfig);
//     // Create message constraints
//        MessageConstraints messageConstraints = MessageConstraints.custom()
//            .setMaxHeaderCount(200)
//            .setMaxLineLength(2000)
//            .build();
		// Create connection configuration
		connectionConfig = ConnectionConfig.custom()
	            .setMalformedInputAction(CodingErrorAction.IGNORE)
	            .setUnmappableInputAction(CodingErrorAction.IGNORE)
	            .setCharset(Consts.UTF_8)
	            .build();
	        // Configure the connection manager to use connection configuration either
	        // by default or for a specific host.
	        connManager.setDefaultConnectionConfig(connectionConfig);
//	        connManager.setConnectionConfig(new HttpHost("somehost", 80), ConnectionConfig.DEFAULT);

	        // Configure total max or per route limits for persistent connections
	        // that can be kept in the pool or leased by the connection manager.
	        connManager.setMaxTotal(maxTotalConn);
	        connManager.setDefaultMaxPerRoute(defaultMaxConPerHost);
	        connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), maxPerRoute);

	        // Create an HttpClient with the given custom dependencies and configuration.
//	        CloseableHttpClient httpclient = HttpClients.custom()
//	            .setConnectionManager(connManager)
//	            .build();
		
		
	}
	
	/**
	 * 获取httpClient
	 * @return
	 */
	public static CloseableHttpClient getHttpClient(){
		CloseableHttpClient client = HttpClients.custom()
	            .setConnectionManager(connManager)
	            .build();
		return client;
	}
	
}
