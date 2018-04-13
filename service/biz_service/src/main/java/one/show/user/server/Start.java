/**
 * 
 */
package one.show.user.server;

import one.show.common.server.ServerStart;

/**
 * @author zhangwei 2015年8月28日 下午5:07:46
 *
 */
public class Start extends ServerStart{

	public static void main(final String[] args) {
		
		new Start().startServer("user");
		
	}
}


