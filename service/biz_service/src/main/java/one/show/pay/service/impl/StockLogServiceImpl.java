/**
 * 
 */
package one.show.pay.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.pay.dao.StockLogMapper;
import one.show.pay.domain.ReturnList;
import one.show.pay.domain.StockLog;
import one.show.pay.service.StockLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2015年11月17日 下午5:03:32
 *
 */

@Component
public class StockLogServiceImpl implements StockLogService {

	@Autowired
	private StockLogMapper stockLogMapper;
	
	/* (non-Javadoc)
	 * @see one.show.pay.service.StockLogService#findStockLogListByUidAndParam(java.lang.String, java.util.Map)
	 */
	@Override
	public ReturnList<StockLog> findStockLogListByUidAndParam(long uid,
			Map<String, String> params, Integer start, Integer count) throws ServiceException {

		ReturnList<StockLog> stockLogList = new ReturnList<StockLog>();
        try {
        	 List<StockLog> list = stockLogMapper.getStockLogListByUid(uid, params, start, count);
        	
            stockLogList.count = stockLogMapper.getStockLogCountByUid(uid, params);
            stockLogList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return stockLogList;
	}

	@Override
	public Double getStockLogItemTotalByUid(long uid,
			Map<String, String> paramMap) throws ServiceException {
		try {
			return stockLogMapper.getStockLogItemTotalByUid(uid, paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveStockLog(StockLog stockLog) throws ServiceException {
		
		try {
			stockLogMapper.saveStockLog(stockLog);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}