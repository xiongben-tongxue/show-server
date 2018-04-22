/**
 * 
 */
package one.show.pay.service.impl;

import one.show.common.Constant.ACTION;
import one.show.common.Constant.STOCK_OPERATE;
import one.show.common.exception.LockException;
import one.show.common.exception.ServiceException;
import one.show.common.exception.StockNotEnoughException;
import one.show.common.lock.DistributedLock;
import one.show.pay.dao.StockLogMapper;
import one.show.pay.dao.StockMapper;
import one.show.pay.domain.Item;
import one.show.pay.domain.Stock;
import one.show.pay.domain.StockLog;
import one.show.pay.service.StockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Haliaeetus leucocephalus 2015年11月11日 上午11:54:42
 *
 */
@Component
public class StockServiceImpl implements StockService {
	
	private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
	
	@Autowired
	private StockMapper stockMapper;
	
	@Autowired
	private StockLogMapper stockLogMapper;
	

	@Override
	public List<Stock> findStockByUidAndParam(long uid,
			Map<String, String> params) throws ServiceException {
		List<Stock> list = null;
		try {
			list = stockMapper.findStockByParam(uid, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}


	
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	public void increase(long uid, Item item,  ACTION action, Integer expire,
			String actionDesc) throws ServiceException {

		double number = item.getNumber();
		if (number <= 0){
			throw new ServiceException("增加物品["+item.getName()+"]数量不正确:"+number);
		}
		
		int now = (int)(System.currentTimeMillis()/1000);
		
		double beforeChange = 0;
		double afterChange = number;
		
		
		try {
			Stock stock = stockMapper.findStock(uid, item.getType(), item.getId());
			
			if (stock == null) {
	            stock = new Stock();
	            stock.setUid(uid);
	            stock.setItemId(item.getId());
	            stock.setItemType(item.getType());
	            stock.setItemName(item.getName());
	            stock.setItemNumber(number);
	            stock.setCreateTime(now);

	            if (expire != null) {
	            	stock.setItemNumber(1);
	            	stock.setDeadLine(now + expire * 24 * 60 * 60);
	            }
	            
	            stockMapper.saveStock(stock);
	            
	        } else {
	        	beforeChange = stock.getItemNumber();
	        	afterChange = stock.getItemNumber() + number;
	        	
	            if (expire == null) {
	            	stockMapper.increaseItemNum(uid, item.getType(), item.getId(),  number, now);
	            } else {
	                if (stock.getDeadLine() <= now) {
	                	stockMapper.updateDealLine(uid, item.getType(), item.getId(), now + expire * 24 * 60 * 60);
	                } else {
	                	stockMapper.updateDealLine(uid, item.getType(), item.getId(), stock.getDeadLine() + expire * 24 * 60 * 60);
	                }
	            }
	        }
		            

			 StockLog stockLog = new StockLog();
	         stockLog.setUid(uid);
	         stockLog.setItemId(item.getId());
	         stockLog.setItemType(item.getType());
	         stockLog.setItemName(item.getName());
	         stockLog.setItemNumber(number);
	         
	         stockLog.setOperate(STOCK_OPERATE.ADD.getId());
	         stockLog.setActionDesc(actionDesc);
	         stockLog.setActionTime(now);
	         stockLog.setActionType(action.getId());
	         stockLog.setBeforeChange(beforeChange);
	         stockLog.setAfterChange(afterChange);
	         stockLogMapper.saveStockLog(stockLog);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		

	        
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	public void reduce(long uid, Item item,  ACTION action,
			Integer expire, String actionDesc) throws ServiceException, StockNotEnoughException {

		double number = Math.abs(item.getNumber());
		
		if (number <= 0){
			throw new ServiceException("扣减物品["+item.getName()+"]数量不正确:"+number);
		}
		
		int now = (int)(System.currentTimeMillis()/1000);
		
		double beforeChange = 0;
		double afterChange = number;
		
		Stock stock = null;
		try {
			stock = stockMapper.findStock(uid, item.getType(), item.getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
			
			
		if (stock == null) {
           throw new StockNotEnoughException(item.getName()+"不足");
            
        } else {
        	beforeChange = stock.getItemNumber();
        	afterChange = stock.getItemNumber() - number;
        	
        	if (afterChange < 0){
        		throw new StockNotEnoughException(item.getName()+"不足");
        	}
        	
    		if (expire == null) {
    			int row = 0;
            	try {
            		row = stockMapper.reduceItemNum(uid, item.getType(), item.getId(),  number, now);
				} catch (Exception e) {
					throw new ServiceException(e);
				}
    			
            	if (row == 0){
            		throw new StockNotEnoughException(item.getName()+"不足");
            	}
            } else {
            	try {
            		if (stock.getDeadLine() > now) {
                    	stockMapper.updateDealLine(uid, item.getType(), item.getId(), stock.getDeadLine() - expire * 24 * 60 * 60);
                    } else {
                    	stockMapper.updateDealLine(uid, item.getType(), item.getId(), now - expire * 24 * 60 * 60);
                    }
				} catch (Exception e) {
					throw new ServiceException(e);
				}
                
            }
        	
        	try {    
                StockLog stockLog = new StockLog();
    			 stockLog.setUid(uid);
    			 stockLog.setItemId(item.getId());
    			 stockLog.setItemType(item.getType());
    			 stockLog.setItemName(item.getName());
    			 stockLog.setItemNumber(number);
    			 
    			 stockLog.setOperate(STOCK_OPERATE.MINUS.getId());
    			 stockLog.setActionDesc(actionDesc);
    			 stockLog.setActionTime(now);
    			 stockLog.setActionType(action.getId());
    			 stockLog.setBeforeChange(beforeChange);
    			 stockLog.setAfterChange(afterChange);
    			 stockLogMapper.saveStockLog(stockLog);
			} catch (Exception e) {
				throw new ServiceException(e);
			}
            
        }
	}


	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	public void freeze(long uid, Item item, ACTION action, String actionDesc)
			throws ServiceException, StockNotEnoughException {
		//减库存
		reduce(uid, item, action, null,  actionDesc);
		//增加冻结记录
		Item freeeItem = new Item();
		freeeItem.setType(item.getType());
		freeeItem.setId(-item.getId());
		freeeItem.setName("冻结"+item.getName());
		freeeItem.setNumber(item.getNumber());
		increase(uid, freeeItem, action, null,  actionDesc);
		
	}



	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class)
	public void unfreeze(long uid, Item item, ACTION action, String actionDesc)
			throws ServiceException, StockNotEnoughException {

		//减冻结记录
		Item freeeItem = new Item();
		freeeItem.setType(item.getType());
		freeeItem.setId(-item.getId());
		freeeItem.setName("冻结"+item.getName());
		freeeItem.setNumber(item.getNumber());
		reduce(uid, freeeItem, action, null,  actionDesc);

		//加库存
		increase(uid, item, action, null,  actionDesc);
		
	}



	@Override
	public Stock findStockByUidAndItem(long uid, Integer itemType, Integer itemId)
			throws ServiceException {
		try {
			return stockMapper.findStock(uid, itemType, itemId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}



}