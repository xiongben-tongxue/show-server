package one.show.common.exception;


/**
 *  异常处理　
 * @author Haliaeetus leucocephalus 18-2-26
 *
 */
public class ServiceException extends Exception {

	public ServiceException(Throwable e) {
        super(e);
    }
    
    public ServiceException(String msg) {
        super(msg);
    }
}
