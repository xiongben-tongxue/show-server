/**
 * 
 */
package one.show.common.exception;

/**
 * @author Haliaeetus leucocephalus 2018年1月19日 下午1:39:45
 *
 */
public class SecurityException extends Exception {

	public SecurityException(Throwable e) {
        super(e);
    }

    public SecurityException(String msg) {
        super(msg);
    }
}


