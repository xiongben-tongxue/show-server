/**
 * 
 */
package one.show.common.exception;

/**
 * @author Haliaeetus leucocephalus 2018年1月15日 下午6:00:32
 *
 */
public class LockException extends Exception {
    private static final long serialVersionUID = 1L;
    public LockException(String e){
        super(e);
    }
    public LockException(Exception e){
        super(e);
    }
}



