/**
 * 
 */
package one.show.pay.service;

import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.pay.domain.ReturnList;
import one.show.pay.domain.StockLog;

/**
 * @author Haliaeetus leucocephalus 2015年11月17日 下午5:00:35
 *
 */
public interface StockLogService {

	public ReturnList<StockLog> findStockLogListByUidAndParam(long uid,Map<String,String> params, Integer start, Integer count) throws ServiceException;

	public Double getStockLogItemTotalByUid(long uid, Map<String, String> paramMap) throws ServiceException;

	public void saveStockLog(StockLog stockLog) throws ServiceException;
}


