package one.show.common.exception;


/**
 *  异常处理　
 * @author Haliaeetus leucocephalus 18-2-26
 *
 */
public class ReturnException extends Exception {

	public ReturnException(Throwable e) {
        super(e);
    }

    public ReturnException(String msg) {
        super(msg);
    }
}
