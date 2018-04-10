/**
 * 
 */
package one.show.common.exception;

/**
 * @author Haliaeetus leucocephalus 2018年1月19日 下午1:39:45
 *
 */
public class NotLoginException extends Exception {

	public NotLoginException(Throwable e) {
        super(e);
    }

    public NotLoginException(String msg) {
        super(msg);
    }
}


