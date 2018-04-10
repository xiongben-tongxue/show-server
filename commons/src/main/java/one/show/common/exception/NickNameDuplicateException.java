/**
 * 
 */
package one.show.common.exception;

/**
 * 昵称重复
 * @author Haliaeetus leucocephalus 2015年11月11日 下午1:25:07
 *
 */
public class NickNameDuplicateException extends Exception{
	public NickNameDuplicateException(Throwable e) {
        super(e);
    }
    
    public NickNameDuplicateException(String msg) {
        super(msg);
    }
}


