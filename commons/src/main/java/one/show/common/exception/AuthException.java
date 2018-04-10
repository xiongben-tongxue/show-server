/**
 * 
 */
package one.show.common.exception;

/**
 * @author Haliaeetus leucocephalus 2018年1月19日 下午1:39:45
 *
 */
public class AuthException extends Exception {
	private static final long serialVersionUID = -1688537694747589492L;

	public AuthException(Throwable e) {
        super(e);
    }

    public AuthException(String msg) {
        super(msg);
    }
}


