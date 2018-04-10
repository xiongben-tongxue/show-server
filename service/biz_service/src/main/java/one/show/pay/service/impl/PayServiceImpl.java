package one.show.pay.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import one.show.common.Adapter;
import one.show.common.Constant.ACTION;
import one.show.common.Constant.ITEM_MONEY;
import one.show.common.Constant.ITEM_TYPE;
import one.show.common.exception.ServiceException;
import one.show.common.exception.StockNotEnoughException;
import one.show.id.thrift.iface.IDServiceProxy;
import one.show.pay.dao.OrdersMapper;
import one.show.pay.dao.TransactionFromMapper;
import one.show.pay.dao.TransactionToUidMapper;
import one.show.pay.dao.TransactionToVidMapper;
import one.show.pay.domain.ExtractRmb;
import one.show.pay.domain.GiftRank;
import one.show.pay.domain.Item;
import one.show.pay.domain.Orders;
import one.show.pay.domain.PayConfig;
import one.show.pay.domain.ReturnList;
import one.show.pay.domain.Stock;
import one.show.pay.domain.TransactionFrom;
import one.show.pay.domain.TransactionToUid;
import one.show.pay.domain.TransactionToVid;
import one.show.pay.service.ExtractRmbService;
import one.show.pay.service.PayService;
import one.show.pay.service.StockService;
import one.show.pay.thrift.view.OrdersView;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PayServiceImpl implements PayService{

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private TransactionFromMapper transactionFromMapper;
	@Autowired
	private TransactionToUidMapper transactionToUidMapper;
	@Autowired
	private TransactionToVidMapper transactionToVidMapper;
	@Autowired
	private StockService stockService;
	@Autowired
	private ExtractRmbService extractRmbService;
	
	@Autowired
	private IDServiceProxy.Iface idServiceClientProxy;

	
	@Override
	public void saveOrders(Orders orders) throws ServiceException {
		try {
			ordersMapper.saveOrders(orders);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Orders getOrdersById(long orderId) throws ServiceException {
		try {
			return ordersMapper.getOrdersById(orderId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateOrdersById(long orderId, Map<String, String> paramMap)
			throws ServiceException {
		try {
			ordersMapper.updateOrdersById(orderId,paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public int updateOrdersByConditions(Map<String, String> conditions, Map<String, String> paramMap) throws ServiceException {
		try {
			return ordersMapper.updateOrdersByConditions(conditions,paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveTransactionFrom(TransactionFrom transactionFrom)
			throws ServiceException {
		try {
			transactionFromMapper.saveTransactionFrom(transactionFrom);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<TransactionFrom> findTransactionFromList(long uid,
			Map<String, String> params,int start,int end) throws ServiceException {
		List<TransactionFrom> list = null;
		try {
			list = transactionFromMapper.findTransactionFromByParams(uid, params,start,end);
		} catch (Exception e) {
			throw  new ServiceException(e);
		}
		return list;
	}

	@Override
	public void saveTransactionTo(TransactionToUid transactionToUid)
			throws ServiceException {
		try {
			transactionToUidMapper.saveTransactionToUid(transactionToUid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<TransactionToUid> findTransactionToUidList(long uid,
			Map<String, String> params,int start,int pageCount) throws ServiceException {
		List<TransactionToUid> list = null;
		try {
			list=transactionToUidMapper.findTransactionToUidListByParam(uid, params,start,pageCount);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public void saveTransactionToVid(TransactionToVid transactionToVid)
			throws ServiceException {
		try {
			transactionToVidMapper.saveTransactionToVid(transactionToVid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<TransactionToVid> findTransactionToVid(String vid,
			Map<String, String> params) throws ServiceException {
		List<TransactionToVid> list=null;
		try {
			list = transactionToVidMapper.findTransactionToVidByParams(vid, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see one.show.pay.service.PayService#getOrdersListByParams(java.util.Map, int, int)
	 */
	@Override
	public ReturnList<Orders> getOrdersListByParams(
			Map<String, String> paramMap, int start, int count)
			throws ServiceException {
		ReturnList<Orders> ordersList = new ReturnList<Orders>();
        try {
        	 List<Orders> list = ordersMapper.getOrdersListByParams(paramMap, start, count);
        	
            ordersList.count = ordersMapper.getOrdersCountByParams(paramMap);
            ordersList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return ordersList;
	
	}

	/* (non-Javadoc)
	 * @see one.show.pay.service.PayService#getOrdersMoneyByParams(java.util.Map)
	 */
	@Override
	public int getOrdersMoneyByParams(Map<String, String> paramMap)
			throws ServiceException {
		try {
			return ordersMapper.getOrdersMoneyByParams(paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Orders> findOrdersViewsByUid(long uid,Map<String,String> params,int start,int end)
			throws ServiceException {
		List<Orders> list = null;
		try {
			list = ordersMapper.getOrdersByUserId(uid,params,start,end);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}


	
	@Override
	public void send(long uid, Item item,  long liveId, long tid)
					throws ServiceException, StockNotEnoughException {

		Item fromItem = new Item();
		fromItem.setId(ITEM_MONEY.SHOWCOIN.getId());
		fromItem.setName(ITEM_MONEY.SHOWCOIN.getName());
		fromItem.setType(ITEM_TYPE.MONEY.getId());
		
		Item toItem = new Item();
		toItem.setId(ITEM_MONEY.SHOWCOIN.getId());
		toItem.setName(ITEM_MONEY.SHOWCOIN.getName());
		toItem.setType(ITEM_TYPE.MONEY.getId());
		
		
		long transactionId;
		try {
			transactionId = idServiceClientProxy.nextId();
		} catch (TException e) {
			throw new ServiceException(e);
		}
		
		Integer now = (int)(System.currentTimeMillis()/1000);
		double totalPrice = item.getPrice()*item.getNumber();
		fromItem.setNumber(totalPrice);
		
		toItem.setNumber(Adapter.getReceive(totalPrice));
		
		stockService.reduce(uid, fromItem, ACTION.SEND_GIFT, null, "赠送 "+item.getNumber()+" 个 "+item.getName()+"，单价"+item.getPrice());
		
		stockService.increase(tid, toItem , ACTION.SEND_GIFT, null, "接收 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
		
		TransactionFrom transactionFrom = new TransactionFrom();
		transactionFrom.setTransactionId(transactionId);
		transactionFrom.setUid(uid);
		transactionFrom.setToUid(tid);
		transactionFrom.setGiveItemId(fromItem.getId());
		transactionFrom.setGiveItemName(fromItem.getName());
		transactionFrom.setGiveItemType(fromItem.getType());
		transactionFrom.setGiveItemNumber(fromItem.getNumber());
		transactionFrom.setActuallyItemId(item.getId());
		transactionFrom.setActuallyItemName(item.getName());
		transactionFrom.setActuallyItemType(item.getType());
		transactionFrom.setActuallyItemNumber(item.getNumber());
		transactionFrom.setActuallyItemPrice(item.getPrice());
		transactionFrom.setActionType(ACTION.SEND_GIFT.getId());
		transactionFrom.setReason("赠送 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
		transactionFrom.setCreateTime(now);
		
		TransactionToUid transactionToUid = new TransactionToUid();
		transactionToUid.setTransactionId(transactionId);
		transactionToUid.setUid(tid);
		transactionToUid.setVid(liveId);
		transactionToUid.setFromUid(uid);
		transactionToUid.setReceiveItemId(toItem.getId());
		transactionToUid.setReceiveItemName(toItem.getName());
		transactionToUid.setReceiveItemType(toItem.getType());
		transactionToUid.setReceiveItemNumber(toItem.getNumber());
		transactionToUid.setActuallyItemId(item.getId());
		transactionToUid.setActuallyItemName(item.getName());
		transactionToUid.setActuallyItemType(item.getType());
		transactionToUid.setActuallyItemNumber(item.getNumber());
		transactionToUid.setActuallyItemPrice(item.getPrice());
		transactionToUid.setActionType(ACTION.SEND_GIFT.getId());
		transactionToUid.setReason("接收 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
		transactionToUid.setCreateTime(now);
		
		
		transactionFromMapper.saveTransactionFrom(transactionFrom);
		transactionToUidMapper.saveTransactionToUid(transactionToUid);
		
		
		if (liveId > 0){
			TransactionToVid transactionToVid = new TransactionToVid();
			BeanUtils.copyProperties(transactionToUid, transactionToVid);
			transactionToVidMapper.saveTransactionToVid(transactionToVid);
		}
		
		
	}

	@Override
	public void redeemRmb(ExtractRmb extractRmb) throws ServiceException,
			StockNotEnoughException {

		Item showCoin = new Item();
		showCoin.setId(ITEM_MONEY.SHOWCOIN.getId());
		showCoin.setName(ITEM_MONEY.SHOWCOIN.getName());
		showCoin.setType(ITEM_TYPE.MONEY.getId());
		showCoin.setNumber(extractRmb.getItemNumber());
		
		Stock stock = stockService.findStockByUidAndItem(extractRmb.getUid(), showCoin.getType(), showCoin.getId());
		double number = stock.getItemNumber();
		
		//冻结
		stockService.freeze(extractRmb.getUid(), showCoin, ACTION.SHOWCOIN_EXCHANGE_RMB, "人民币提现，冻结"+showCoin.getName()+extractRmb.getItemNumber()+"个");
				
		try {
			long id = idServiceClientProxy.nextId();
			extractRmb.setId(id);
		} catch (TException e) {
			throw new ServiceException(e);
		}
				
		extractRmb.setBeforeChange(number);
		extractRmb.setAfterChange(number-extractRmb.getItemNumber());
		extractRmbService.saveExtractRmb(extractRmb);
	
	}

	@Override
	public List<GiftRank> findGiftRanksByUid(long uid,int start,int count) {
		return transactionToUidMapper.findGiftRanks(uid,start,count);
	}
	
	@Override
	public List<GiftRank> findGiftRanksByVid(long vid,int start,int count) {
		return transactionToVidMapper.findGiftRanks(vid,start,count);
	}
	
	public static void main(String[] args) {
		BigDecimal result = new BigDecimal(7).multiply(new BigDecimal(1.5)).setScale(1, BigDecimal.ROUND_HALF_UP);
		System.out.println(result);
	}

	@Override
	public List<PayConfig> findAllPayConfigs() throws ServiceException {
		return ordersMapper.getPayConfigs();
	}


	@Override
	public Integer findTotalReceiveByVid(long vid) throws ServiceException {
		
		try {
			Integer total = transactionToVidMapper.findTotalReceive(vid);
			if (total != null){
				return total;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return 0;
	}

	@Override
	public OrdersView findOrderViewByReceipt(String md5_receipt) throws ServiceException {
		try {
			return ordersMapper.getOrdersByReceipt(md5_receipt);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int findTransactionCountFromList(long uid, Map<String, String> params)
			throws ServiceException {
		Integer num = null;
		try {
			num = transactionFromMapper.findTransactionCountFromByParams(uid, params);
		} catch (Exception e) {
			throw  new ServiceException(e);
		}
		return num == null ? 0 : num;
	
	}

	@Override
	public int findTransactionToUidCount(long uid, Map<String, String> params)
			throws ServiceException {
		
		Integer num = null;
		try {
			num = transactionToUidMapper.findTransactionToUidCountByParam(uid, params);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return num == null ? 0 : num;
	
	}

}