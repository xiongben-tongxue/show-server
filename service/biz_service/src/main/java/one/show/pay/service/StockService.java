/**
 * 
 */
package one.show.pay.service;

import java.util.List;
import java.util.Map;

import one.show.common.Constant.ACTION;
import one.show.common.exception.ServiceException;
import one.show.common.exception.StockNotEnoughException;
import one.show.pay.domain.Item;
import one.show.pay.domain.Stock;

/**
 * @author Haliaeetus leucocephalus 2015年11月11日 上午11:53:48
 *
 */
public interface StockService {
	
	
	public void increase(long uid, Item item,  ACTION action, Integer expire,
			String actionDesc) throws ServiceException;
	
	
	public void reduce(long uid, Item item,  ACTION action, Integer expire,
			String actionDesc) throws ServiceException, StockNotEnoughException;
	
	/**
	 * 冻结
	 * @param uid
	 * @param item
	 * @param action
	 * @param actionDesc
	 * @throws ServiceException
	 * @throws StockNotEnoughException
	 */
	public void freeze(long uid, Item item,  ACTION action, 
			String actionDesc) throws ServiceException, StockNotEnoughException;
	
	
	/**
	 * 解冻
	 * @param uid
	 * @param item
	 * @param action
	 * @param actionDesc
	 * @throws ServiceException
	 * @throws StockNotEnoughException
	 */
	public void unfreeze(long uid, Item item,  ACTION action, 
			String actionDesc) throws ServiceException, StockNotEnoughException;
	    
	
	public List<Stock> findStockByUidAndParam(long uid,Map<String,String> params) throws ServiceException;
	
	public Stock findStockByUidAndItem(long uid, Integer itemType, Integer itemId) throws ServiceException;
	
	
}