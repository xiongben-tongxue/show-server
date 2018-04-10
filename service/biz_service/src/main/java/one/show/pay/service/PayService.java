package one.show.pay.service;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.common.exception.StockNotEnoughException;
import one.show.pay.domain.ExtractRmb;
import one.show.pay.domain.GiftRank;
import one.show.pay.domain.Item;
import one.show.pay.domain.Orders;
import one.show.pay.domain.PayConfig;
import one.show.pay.domain.ReturnList;
import one.show.pay.domain.TransactionFrom;
import one.show.pay.domain.TransactionToUid;
import one.show.pay.domain.TransactionToVid;
import one.show.pay.thrift.view.OrdersView;


public interface PayService {
	
	public void saveOrders(Orders orders) throws ServiceException;

	public Orders getOrdersById(long orderId) throws ServiceException;
	
	public void updateOrdersById(long orderId,Map<String, String> paramMap) throws ServiceException;

	public int updateOrdersByConditions(Map<String, String> conditions, Map<String, String> paramMap) throws ServiceException;


	public ReturnList<Orders> getOrdersListByParams(Map<String, String> paramMap, int start, int count)throws ServiceException;
	
	public int getOrdersMoneyByParams(Map<String, String> paramMap)throws ServiceException;
	
	public void saveTransactionFrom(TransactionFrom transactionFrom) throws ServiceException;
	
	public List<TransactionFrom> findTransactionFromList(long uid,Map<String,String> params,int start,int end)throws ServiceException;
	
	public int findTransactionCountFromList(long uid,Map<String,String> params)throws ServiceException;
	
	public void saveTransactionTo(TransactionToUid transactionToUid) throws ServiceException;
	
	public List<TransactionToUid> findTransactionToUidList(long uid,Map<String,String>params,int start,int pageCount)throws ServiceException;
	
	public int findTransactionToUidCount(long uid,Map<String,String>params)throws ServiceException;
	
	public void saveTransactionToVid(TransactionToVid transactionToVid)throws ServiceException;
	
	public List<TransactionToVid>findTransactionToVid(String vid,Map<String,String> params)throws ServiceException;
	
	public List<Orders> findOrdersViewsByUid(long uid,Map<String,String> params,int start,int end) throws ServiceException;
	
	public void send(long uid, Item item,  long liveId, long tid) throws ServiceException, StockNotEnoughException;
	
	public void redeemRmb(ExtractRmb extractRmb)  throws ServiceException, StockNotEnoughException;
	
	
	public List<GiftRank> findGiftRanksByUid(long uid,int start,int count);
	
	public List<GiftRank> findGiftRanksByVid(long vid,int start,int count);
	
	public Integer findTotalReceiveByVid(long vid) throws ServiceException;
	
	public List<PayConfig> findAllPayConfigs() throws ServiceException;

	public OrdersView findOrderViewByReceipt(String md5_receipt) throws ServiceException;

}
