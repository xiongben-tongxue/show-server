/**
 * 
 */
package one.show.common.exception;

/**
 * 库存不足
 * @author Haliaeetus leucocephalus 2015年11月11日 下午1:25:07
 *
 */
public class StockNotEnoughException extends Exception{
	public StockNotEnoughException(Throwable e) {
        super(e);
    }
    
    public StockNotEnoughException(String msg) {
        super(msg);
    }
}


