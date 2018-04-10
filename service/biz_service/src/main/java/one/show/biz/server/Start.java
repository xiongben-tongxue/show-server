/**
 * 
 */
package one.show.biz.server;

import java.io.UnsupportedEncodingException;

import one.show.common.server.ServerStart;

import org.springframework.aop.framework.AopProxyUtils;

/**
 * @author Haliaeetus leucocephalus 2018年1月28日 下午5:07:46
 *
 */
public class Start extends ServerStart{

	public static void main(final String[] args) {
		String jarFilePath = AopProxyUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();  
	   // URL Decoding  
	   try {
	    jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");
	   } catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }  
	   System.out.println("jarFilePath:"+jarFilePath);
		new Start().startServer("biz");
		
	}
}


