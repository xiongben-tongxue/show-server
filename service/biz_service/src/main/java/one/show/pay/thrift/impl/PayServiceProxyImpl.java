package one.show.pay.thrift.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.common.Constant;
import one.show.common.Constant.ACTION;
import one.show.common.Constant.ITEM_MONEY;
import one.show.common.Constant.ITEM_TYPE;
import one.show.common.exception.ServiceException;
import one.show.common.exception.StockNotEnoughException;
import one.show.id.thrift.iface.IDServiceProxy;
import one.show.pay.domain.ExtractBind;
import one.show.pay.domain.ExtractRmb;
import one.show.pay.domain.FamilyClearing;
import one.show.pay.domain.FamilyMemberClearing;
import one.show.pay.domain.GiftRank;
import one.show.pay.domain.Item;
import one.show.pay.domain.Orders;
import one.show.pay.domain.PayConfig;
import one.show.pay.domain.ReturnList;
import one.show.pay.domain.Stock;
import one.show.pay.domain.StockLog;
import one.show.pay.domain.TransactionFrom;
import one.show.pay.domain.TransactionToUid;
import one.show.pay.domain.TransactionToVid;
import one.show.pay.service.ExtractBindService;
import one.show.pay.service.ExtractRmbService;
import one.show.pay.service.FamilyClearingService;
import one.show.pay.service.PayService;
import one.show.pay.service.StockLogService;
import one.show.pay.service.StockService;
import one.show.pay.thrift.iface.PayServiceProxy.Iface;
import one.show.pay.thrift.view.ExtractBindView;
import one.show.pay.thrift.view.ExtractRmbListView;
import one.show.pay.thrift.view.ExtractRmbView;
import one.show.pay.thrift.view.FamilyClearingListView;
import one.show.pay.thrift.view.FamilyClearingView;
import one.show.pay.thrift.view.FamilyMemberClearingListView;
import one.show.pay.thrift.view.FamilyMemberClearingView;
import one.show.pay.thrift.view.GiftRankView;
import one.show.pay.thrift.view.ItemView;
import one.show.pay.thrift.view.OrdersListView;
import one.show.pay.thrift.view.OrdersView;
import one.show.pay.thrift.view.PayConfigView;
import one.show.pay.thrift.view.StockLogListView;
import one.show.pay.thrift.view.StockLogView;
import one.show.pay.thrift.view.StockView;
import one.show.pay.thrift.view.TransactionFromView;
import one.show.pay.thrift.view.TransactionToView;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("payServiceProxyImpl")
public class PayServiceProxyImpl implements Iface {

	private static final Logger log = LoggerFactory.getLogger(PayServiceProxyImpl.class);

	@Autowired
	private PayService payService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private StockLogService stockLogService;
	
	@Autowired
	private ExtractRmbService extractRmbService;
	
	@Autowired
	private ExtractBindService extractBindService;
	
	@Autowired
	private IDServiceProxy.Iface idServiceClientProxy;
	
	@Autowired
	private FamilyClearingService familyClearingService;
	
	@Override
	public void saveOrders(OrdersView ordersView) throws TException {
		Orders orders = new Orders();
		try {
			BeanUtils.copyProperties(ordersView, orders);
			payService.saveOrders(orders);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public OrdersView getOrdersById(long orderId) throws TException {
		OrdersView ordersView = null;
		try {
			Orders orders = payService.getOrdersById(orderId);
			if (orders!=null) {
				ordersView = new OrdersView();
				BeanUtils.copyProperties(orders, ordersView);
			}
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		return ordersView;
	}

	@Override
	public void updateOrdersById(long orderId, Map<String, String> paramMap)
			throws TException {
		try {
			payService.updateOrdersById(orderId, paramMap);
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public int updateOrdersByConditions(Map<String, String> conditions, Map<String, String> paramMap) throws TException {
		try {
			return payService.updateOrdersByConditions(conditions, paramMap);
		} catch (ServiceException e) {
			log.error(e.getMessage(),e);
			throw new TException(e);
		}
	}


	@Override
	public List<StockView> findStockByUidAndParam(long uid,
			Map<String, String> params) throws TException {
		List<StockView> stockViewList = null;
		try {
			List<Stock> stockList = stockService.findStockByUidAndParam(uid, params);
			if (stockList!=null) {
				stockViewList = new ArrayList<StockView>();
				for (Stock stock : stockList) {
					StockView stockView = new StockView();
					BeanUtils.copyProperties(stock, stockView);
					stockViewList.add(stockView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		
		return stockViewList;
	}

	/* (non-Javadoc)
	 * @see one.show.pay.thrift.iface.PayServiceProxy.Iface#findStockLogByUidAndParam(java.lang.String, java.util.Map)
	 */
	@Override
	public StockLogListView findStockLogByUidAndParam(long uid,
			Map<String, String> params,int start, int count) throws TException {
		
		try {
			ReturnList<StockLog> returnList = stockLogService.findStockLogListByUidAndParam(uid, params, start, count);
			StockLogListView stockLogListView = new StockLogListView();
			stockLogListView.stockLogList = new ArrayList<StockLogView>();
			
			if (returnList != null && returnList.objects != null){
				for(StockLog stockLog : returnList.objects){
					StockLogView stockLogView = new StockLogView();
					BeanUtils.copyProperties(stockLog, stockLogView);
					stockLogListView.stockLogList.add(stockLogView);
				}
			}
			
			stockLogListView.count = returnList.count;
			return stockLogListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}
	@Override
	public List<TransactionToView> findTransactionToUidList(long uid,
			Map<String, String> params, int start, int pageCount)
			throws TException {
		List<TransactionToView> list = null;
		try {
			List<TransactionToUid> transactionToUidList = payService.findTransactionToUidList(uid, params, start, pageCount);
			if(transactionToUidList!=null){
				list = new ArrayList<TransactionToView>();
				for(TransactionToUid TransactionToUid:transactionToUidList){
					TransactionToView transactionToUidView = new TransactionToView();
					BeanUtils.copyProperties(TransactionToUid, transactionToUidView);
					list.add(transactionToUidView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}

	@Override
	public void saveTransactionFromView(TransactionFromView transactionFromView) throws TException {
		try {
			if(transactionFromView!=null){
				TransactionFrom transactionFrom = new TransactionFrom();
				BeanUtils.copyProperties(transactionFromView, transactionFrom);
				payService.saveTransactionFrom(transactionFrom);
			}

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void saveTransactionToView(
			TransactionToView transactionToUidView) throws TException {
		try {
			if(transactionToUidView!=null){
				TransactionToUid transactionToUid = new TransactionToUid();
				TransactionToVid transactionToVid = new TransactionToVid();
				BeanUtils.copyProperties(transactionToUidView, transactionToUid);
				BeanUtils.copyProperties(transactionToUidView, transactionToVid);
				payService.saveTransactionTo(transactionToUid);
				payService.saveTransactionToVid(transactionToVid);
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.pay.thrift.iface.PayServiceProxy.Iface#findOrdersListByParams(java.util.Map, int, int)
	 */
	@Override
	public OrdersListView findOrdersListByParams(Map<String, String> params,
			int start, int count) throws TException {
		try {
			ReturnList<Orders> returnList = payService.getOrdersListByParams(params, start, count);
			OrdersListView ordersListView = new OrdersListView();
			ordersListView.ordersList = new ArrayList<OrdersView>();
			
			if (returnList != null && returnList.objects != null){
				for(Orders orders : returnList.objects){
					OrdersView ordersView = new OrdersView();
					BeanUtils.copyProperties(orders, ordersView);
					ordersListView.ordersList.add(ordersView);
				}
			}
			
			ordersListView.count = returnList.count;
			return ordersListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	/* (non-Javadoc)
	 * @see one.show.pay.thrift.iface.PayServiceProxy.Iface#findOrdersMoneyByParams(java.util.Map)
	 */
	@Override
	public int findOrdersMoneyByParams(Map<String, String> params)
			throws TException {

		try {
			return payService.getOrdersMoneyByParams(params);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}
	@Override
	public List<OrdersView> findOrdersViewsByUid(long uid,Map<String,String> params,int start,int pageCount) throws TException {
		List<OrdersView> list = null;
		try {
			List<Orders> orderList = payService.findOrdersViewsByUid(uid,params,start,pageCount);
			if(orderList!=null){
				list = new ArrayList<OrdersView>();
				for(Orders orders:orderList){
					OrdersView ordersView = new OrdersView();
					BeanUtils.copyProperties(orders, ordersView);
					list.add(ordersView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}

	@Override
	public double getStockLogItemTotalByUid(long uid,
			Map<String, String> paramMap) throws TException {
		double total = 0;
		try {
			total = stockLogService.getStockLogItemTotalByUid(uid, paramMap);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		return total;
	}
	@Override
	public List<TransactionFromView> findTransactionFromViewListByParams(
			long uid, Map<String, String> params, int start, int pageCount)
			throws TException {
		List<TransactionFromView> list = null;
		try {
			List<TransactionFrom> transactionFromList = payService.findTransactionFromList(uid, params, start, pageCount);
			if(transactionFromList!=null){
				list = new ArrayList<TransactionFromView>();
				for(TransactionFrom transactionFrom:transactionFromList){
					TransactionFromView transactionFromView = new TransactionFromView();
					BeanUtils.copyProperties(transactionFrom, transactionFromView);
					list.add(transactionFromView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}


	/* (non-Javadoc)
	 * @see one.show.pay.thrift.iface.PayServiceProxy.Iface#sendGift(java.lang.String, int, java.lang.String, int, double, java.lang.String, java.lang.String)
	 */
	@Override
	public int send(long uid, ItemView itemView, long liveId, long tid) throws TException {
		Item item = new Item();
		BeanUtils.copyProperties(itemView, item);
		try {
			payService.send(uid, item, liveId, tid);
			return 0;
		} catch (ServiceException e) {
			throw new TException(e);
		} catch (StockNotEnoughException e) {
			return -1;
		}
	}
	
	@Override
	public ExtractRmbListView getExtractRmbListByUid(Map<String, String> paramMap, int start, int count)
			throws TException {
		try {
			ReturnList<ExtractRmb> returnList = extractRmbService
					.getExtractRmbListByUid(paramMap, start, count);
			ExtractRmbListView extractRmbListView = new ExtractRmbListView();
			extractRmbListView.extractRmbList = new ArrayList<ExtractRmbView>();

			if (returnList != null && returnList.objects != null) {
				for (ExtractRmb extractRmb : returnList.objects) {
					ExtractRmbView extractRmbView = new ExtractRmbView();
					BeanUtils.copyProperties(extractRmb, extractRmbView);
					extractRmbListView.extractRmbList.add(extractRmbView);
				}
			}
			extractRmbListView.count = returnList.count;
			return extractRmbListView;
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void charge(long uid,ItemView itemView) throws TException {
		
		
		try {
			Item item = new Item();
			BeanUtils.copyProperties(itemView, item);

			if (item.getType() == ITEM_TYPE.MONEY.getId() && item.getId() == ITEM_MONEY.SHOWCOIN.getId()){
				stockService.increase(uid, item, ACTION.RMB_BUY_SHOWCOIN, null, ACTION.RMB_BUY_SHOWCOIN.getName());
			} else{
				throw new TException("不支持的物品");
			}
			

		} catch (Exception e) {
			throw new TException(e);
		}
	}



	public double getExtractRmbTotalByUid(long uid, String key,
			Map<String, String> paramMap) throws TException {
		try {
			return extractRmbService
					.getExtractRmbTotalByUid(uid, key, paramMap);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateExtractRmbById(long id, Map<String, String> paramMap)
			throws TException {
		try {
			extractRmbService.updateExtractRmbById(id, paramMap);
		} catch (ServiceException e) {
			throw new TException(e);
		}

	}

	@Override
	public void saveExtractRmb(ExtractRmbView extractRmbView) throws TException {
		ExtractRmb extractRmb = new ExtractRmb();
		try {
			BeanUtils.copyProperties(extractRmbView, extractRmb);
			extractRmbService.saveExtractRmb(extractRmb);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public int redeemRmb(ExtractRmbView extractRmbView) throws TException {
		ExtractRmb extractRmb = new ExtractRmb();
		try {
			BeanUtils.copyProperties(extractRmbView, extractRmb);
			payService.redeemRmb(extractRmb);
			return 0;
		} catch (ServiceException e) {
			throw new TException(e);
		} catch (StockNotEnoughException e) {
			return -1;
		}
	}

	@Override
	public int systemSend(long uid, ItemView itemView) throws TException {
		Item item = new Item();
		BeanUtils.copyProperties(itemView, item);
		
		TransactionToUid transactionToUid = new TransactionToUid();
		TransactionFrom transactionFrom = new TransactionFrom();

		long transactionId =  idServiceClientProxy.nextId();
		if(item.getNumber() > 0){
				
				transactionToUid.setReceiveItemId(item.getId());
				transactionToUid.setReceiveItemName(item.getName());
				transactionToUid.setReceiveItemNumber(item.getNumber());
				transactionToUid.setReceiveItemType(item.getType());
				
				transactionToUid.setActuallyItemId(item.getId());
				transactionToUid.setActuallyItemName(item.getName());
				transactionToUid.setActuallyItemNumber(item.getNumber());
				transactionToUid.setActuallyItemPrice(item.getPrice());
				transactionToUid.setActuallyItemType(item.getType());
				transactionToUid.setUid(uid);
				transactionToUid.setActionType(Constant.ACTION.SYS_GIVE.getId());
				transactionToUid.setCreateTime((int)(System.currentTimeMillis()/1000));
				transactionToUid.setReason(Constant.ACTION.SYS_GIVE.getName());
				transactionToUid.setTransactionId(transactionId);
				try {
					if (itemView.getType() == ITEM_TYPE.PROP.getId() && itemView.getId() == Constant.ITEM_PROP.VIP.getId()){
						stockService.increase(uid, item, Constant.ACTION.SYS_GIVE, Math.abs((int)itemView.getNumber()*30), Constant.ACTION.SYS_GIVE.getName());
					}else{
						stockService.increase(uid, item, Constant.ACTION.SYS_GIVE, null, Constant.ACTION.SYS_GIVE.getName());
					}
					
					payService.saveTransactionTo(transactionToUid);
					return 0;
				} catch (ServiceException e) {
					throw new TException(e);
				}
			
			}else{
				
				transactionFrom.setGiveItemId(item.getId());
				transactionFrom.setGiveItemName(item.getName());
				transactionFrom.setGiveItemNumber(Math.abs(item.getNumber()));
				transactionFrom.setGiveItemType(item.getType());
				transactionFrom.setActuallyItemId(item.getId());
				transactionFrom.setActuallyItemName(item.getName());
				transactionFrom.setActuallyItemNumber(Math.abs(item.getNumber()));
				transactionFrom.setActuallyItemPrice(item.getPrice());
				transactionFrom.setActuallyItemType(item.getType());
				transactionFrom.setUid(uid);
				transactionFrom.setActionType(Constant.ACTION.SYS_RECOVERY.getId());
				transactionFrom.setCreateTime((int)(System.currentTimeMillis()/1000));
				transactionFrom.setReason(Constant.ACTION.SYS_RECOVERY.getName());
				transactionFrom.setTransactionId(transactionId);
				
				try {
					if (itemView.getType() == ITEM_TYPE.PROP.getId() && itemView.getId() == Constant.ITEM_PROP.VIP.getId()){
						stockService.reduce(uid, item,  Constant.ACTION.SYS_RECOVERY, Math.abs((int)itemView.getNumber()*30), Constant.ACTION.SYS_RECOVERY.getName());
					}else{
						stockService.reduce(uid, item,  Constant.ACTION.SYS_RECOVERY, null, Constant.ACTION.SYS_RECOVERY.getName());
					}
					
					
				} catch (ServiceException e) {
					throw new TException(e);
				} catch (StockNotEnoughException e) {
					return -1;
				}
				
				try {
					
					payService.saveTransactionFrom(transactionFrom);
				} catch (Exception e) {
					throw new TException(e);
				}
				
				return 0;
				
			}
		
	}

	@Override
	public List<GiftRankView> findGiftRanksByUid(long uid, int start, int count)
			throws TException {
		List<GiftRank> ranks = payService.findGiftRanksByUid(uid, start, count);
		List<GiftRankView> rankViews = new ArrayList<GiftRankView>();
		for(GiftRank rank:ranks){
//			log.info(TypeUtil.typeToString("from db=======", rank));
			
			GiftRankView rankView = new GiftRankView();
			BeanUtils.copyProperties(rank, rankView);
			rankViews.add(rankView);
		}
		return rankViews;
	}

	@Override
	public List<GiftRankView> findGiftRanksByVid(long vid, int start, int count)
			throws TException {
		List<GiftRank> ranks = payService.findGiftRanksByVid(vid, start, count);
		List<GiftRankView> rankViews = new ArrayList<GiftRankView>();
		for(GiftRank rank:ranks){
			GiftRankView rankView = new GiftRankView();
			BeanUtils.copyProperties(rank, rankView);
			rankViews.add(rankView);
		}
		return rankViews;
	}

	@Override
	public int consume(long uid, ItemView itemView) throws TException {
		Item item = new Item();
		BeanUtils.copyProperties(itemView, item);
		
		Item jinbi = new Item();
		jinbi.setId(ITEM_MONEY.SHOWCOIN.getId());
		jinbi.setName(ITEM_MONEY.SHOWCOIN.getName());
		jinbi.setType(ITEM_TYPE.MONEY.getId());
		double totalPrice = item.getPrice()*item.getNumber();
		jinbi.setNumber(totalPrice);
		
		long transactionId =  idServiceClientProxy.nextId();
		
		TransactionFrom transactionFrom = new TransactionFrom();
		transactionFrom.setTransactionId(transactionId);
		transactionFrom.setUid(uid);
		transactionFrom.setGiveItemId(jinbi.getId());
		transactionFrom.setGiveItemName(jinbi.getName());
		transactionFrom.setGiveItemType(jinbi.getType());
		transactionFrom.setGiveItemNumber(jinbi.getNumber());
		transactionFrom.setActuallyItemId(item.getId());
		transactionFrom.setActuallyItemName(item.getName());
		transactionFrom.setActuallyItemType(item.getType());
		transactionFrom.setActuallyItemNumber(item.getNumber());
		transactionFrom.setActuallyItemPrice(item.getPrice());
		transactionFrom.setActionType(ACTION.CONSUMER.getId());
		transactionFrom.setReason("购买 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
		
		Integer now = (int)(System.currentTimeMillis()/1000);
		transactionFrom.setCreateTime(now);
		
		
		try {
			stockService.reduce(uid, jinbi,  Constant.ACTION.CONSUMER, null, "购买 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
		} catch (ServiceException e) {
			throw new TException(e);
		} catch (StockNotEnoughException e) {
			return -1;
		}
		
		try {
			payService.saveTransactionFrom(transactionFrom);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		return 0;
		
		
	}

	@Override
	public int buy(long uid, ItemView itemView) throws TException {

		
		Item item = new Item();
		BeanUtils.copyProperties(itemView, item);
		
		Item jinbi = new Item();
		jinbi.setId(ITEM_MONEY.SHOWCOIN.getId());
		jinbi.setName(ITEM_MONEY.SHOWCOIN.getName());
		jinbi.setType(ITEM_TYPE.MONEY.getId());
		double totalPrice = item.getPrice()*item.getNumber();
		jinbi.setNumber(totalPrice);
		
		long transactionId =  idServiceClientProxy.nextId();
		
		TransactionFrom transactionFrom = new TransactionFrom();
		transactionFrom.setTransactionId(transactionId);
		transactionFrom.setUid(uid);
		transactionFrom.setGiveItemId(jinbi.getId());
		transactionFrom.setGiveItemName(jinbi.getName());
		transactionFrom.setGiveItemType(jinbi.getType());
		transactionFrom.setGiveItemNumber(jinbi.getNumber());
		transactionFrom.setActuallyItemId(item.getId());
		transactionFrom.setActuallyItemName(item.getName());
		transactionFrom.setActuallyItemType(item.getType());
		transactionFrom.setActuallyItemNumber(item.getNumber());
		transactionFrom.setActuallyItemPrice(item.getPrice());
		transactionFrom.setActionType(ACTION.BUY.getId());
		transactionFrom.setReason("购买 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
		
		Integer now = (int)(System.currentTimeMillis()/1000);
		transactionFrom.setCreateTime(now);
		
		
		try {
			stockService.reduce(uid, jinbi,  Constant.ACTION.BUY, null, "购买 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
		} catch (ServiceException e) {
			throw new TException(e);
		} catch (StockNotEnoughException e) {
			return -1;
		}
		
		try {
			stockService.increase(uid, item , Constant.ACTION.BUY, null, "购买 "+item.getNumber()+" 个 "+item.getName()+", 单价"+item.getPrice());
			payService.saveTransactionFrom(transactionFrom);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		return 0;
	}

	@Override
	public List<PayConfigView> findAllPayConfigs() throws TException {
		List<PayConfigView> list = null;
		try {
			List<PayConfig> configs =  payService.findAllPayConfigs();
			if(configs!=null){
				list = new ArrayList<PayConfigView>();
				for(PayConfig pc:configs){
					PayConfigView v = new PayConfigView();
					BeanUtils.copyProperties(pc, v);
					list.add(v);
				}
			}
		} catch (ServiceException e) {
			throw new TException(e);
		}
		return list;
	}



	@Override
	public int findTotalReceiveByVid(long vid) throws TException {
		try {
			return payService.findTotalReceiveByVid(vid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public OrdersView findOrderViewByReceipt(String md5_receipt)
			throws TException {
		try {
			return payService.findOrderViewByReceipt(md5_receipt);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void saveExtractBind(ExtractBindView extractBindView)
			throws TException {

		ExtractBind extractBind = new ExtractBind();
		try {
			BeanUtils.copyProperties(extractBindView, extractBind);
			extractBindService.saveExtractBind(extractBind);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		
	}

	@Override
	public ExtractBindView getExtractBindByUid(long uid) throws TException {

		ExtractBindView extractBindView = null;
		try {
			ExtractBind extractBind = extractBindService.getExtractBindByUid(uid);
			if (extractBind!=null) {
				extractBindView = new ExtractBindView();
				BeanUtils.copyProperties(extractBind, extractBindView);
			}
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		return extractBindView;
		
	}

	@Override
	public void updateExtractBindByUid(long uid, String alipayAccount, String alipayName)
			throws TException {
		try {
			extractBindService.updateExtractBindByUid(uid, alipayAccount, alipayName);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}
		
	@Override
	public long getMaxBatchNo() throws TException {
		return extractRmbService.getMaxBatchNo();
	}

	@Override
	public List<ExtractRmbView> getExtractRmbListByBatchNo(long batchNo)
			throws TException {
		try {
			List<ExtractRmb> returnList = extractRmbService.getExtractRmbListByBatchNo(batchNo);
			List<ExtractRmbView>  extractRmbList = new ArrayList<ExtractRmbView>();

			if (returnList != null && returnList.size()>0) {
				for (ExtractRmb extractRmb : returnList) {
					ExtractRmbView extractRmbView = new ExtractRmbView();
					BeanUtils.copyProperties(extractRmb, extractRmbView);
					extractRmbList.add(extractRmbView);
				}
			}
			return extractRmbList;
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public StockView findStockByUidAndItem(long uid, int itemType, int itemId)
			throws TException {
		
		StockView stockView = null;
		try {
			Stock stock = stockService.findStockByUidAndItem(uid, itemType, itemId);
			if (stock!=null) {
				stockView = new StockView();
				BeanUtils.copyProperties(stock, stockView);
			}
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		return stockView;
	}

	@Override
	public List<ExtractRmbView> getExtractRmbListByStatus(int status, int count)
			throws TException {
		try {
			List<ExtractRmb> returnList = extractRmbService.getExtractRmbListByStatus(status, count);
			List<ExtractRmbView>  extractRmbList = new ArrayList<ExtractRmbView>();

			if (returnList != null && returnList.size()>0) {
				for (ExtractRmb extractRmb : returnList) {
					ExtractRmbView extractRmbView = new ExtractRmbView();
					BeanUtils.copyProperties(extractRmb, extractRmbView);
					extractRmbList.add(extractRmbView);
				}
			}
			return extractRmbList;
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void unfreeze(long uid, ItemView itemView, int actionValue,
			String actionDesc) throws TException {
		ACTION action = ACTION.getActionByValue(actionValue);
		
		Item item = new Item();
		BeanUtils.copyProperties(itemView, item);
		try {
			stockService.unfreeze(uid, item, action, actionDesc);
		} catch (Exception e) {
			log.error("unfreeze error.",e);
			throw new TException(e);
		}
	}

	@Override
	public ExtractRmbView getExtractRmbViewById(long id) throws TException {
		return extractRmbService.getExtractRmbViewById(id);
	}

	@Override
	public void reduce(long uid, ItemView itemView, int actionId, String actionDesc)
			throws TException {
		try {
			ACTION action = ACTION.getActionByValue(actionId);
			
			Item item = new Item();
			BeanUtils.copyProperties(itemView, item);
			stockService.reduce(uid, item, action, null, actionDesc);;
		} catch (Exception e) {
			log.error("reduce error.",e);
			throw new TException(e);
		}
	}

	@Override
	public double getExtractRmbTotalCount() throws TException {
		return extractRmbService.getExtractRmbTotalCount();
	}

	@Override
	public void freeze(long uid, ItemView itemView, int actionValue,
			String actionDesc) throws TException {

		ACTION action = ACTION.getActionByValue(actionValue);
		
		Item item = new Item();
		BeanUtils.copyProperties(itemView, item);
		try {
			stockService.freeze(uid, item, action, actionDesc);
		} catch (Exception e) {
			log.error("freeze error.",e);
			throw new TException(e);
		}
		
	}

	@Override
	public int saveFamilyClearing(FamilyClearingView familyClearingView)
			throws TException {
		FamilyClearing familyClearing = new FamilyClearing();
		try {
			BeanUtils.copyProperties(familyClearingView, familyClearing);
			
			familyClearingService.addFamilyClearing(familyClearing);

			return familyClearing.getId();
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void saveFamilyMemberClearing(
			FamilyMemberClearingView familyMemberClearingView)
			throws TException {
		FamilyMemberClearing familyMemberClearing = new FamilyMemberClearing();
		try {
			BeanUtils.copyProperties(familyMemberClearingView, familyMemberClearing);
			familyClearingService.addFamilyMemberClearing(familyMemberClearing);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public FamilyClearingListView findFamilyClearingList(
			Map<String, String> params, int start, int count) throws TException {
		
		try {
			ReturnList<FamilyClearing> returnList = familyClearingService.getFamilyClearingList(params, start, count);
			FamilyClearingListView familyClearingListView = new FamilyClearingListView();
			familyClearingListView.familyClearingList = new ArrayList<FamilyClearingView>();
			
			if (returnList != null && returnList.objects != null){
				for(FamilyClearing familyClearing : returnList.objects){
					FamilyClearingView familyClearingView = new FamilyClearingView();
					BeanUtils.copyProperties(familyClearing, familyClearingView);
					familyClearingListView.familyClearingList.add(familyClearingView);
				}
			}
			
			familyClearingListView.count = returnList.count;
			return familyClearingListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
	
	}

	@Override
	public FamilyMemberClearingListView findFamilyMemberClearingListByCidAndFamilyId(
			int cid, long familyId, int start, int count) throws TException {

		try {
			ReturnList<FamilyMemberClearing> returnList = familyClearingService.getFamilyMemberClearingListByCidAndFamilyId(cid, familyId, start, count);
			FamilyMemberClearingListView familyMemberClearingListView = new FamilyMemberClearingListView();
			familyMemberClearingListView.familyMemberClearingList = new ArrayList<FamilyMemberClearingView>();
			
			if (returnList != null && returnList.objects != null){
				for(FamilyMemberClearing familyMemberClearing : returnList.objects){
					FamilyMemberClearingView familyMemberClearingView = new FamilyMemberClearingView();
					BeanUtils.copyProperties(familyMemberClearing, familyMemberClearingView);
					familyMemberClearingListView.familyMemberClearingList.add(familyMemberClearingView);
				}
			}
			
			familyMemberClearingListView.count = returnList.count;
			return familyMemberClearingListView;
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public FamilyClearingView getFamilyClearingViewById(long id)
			throws TException {
		FamilyClearingView fcv = null;
		FamilyClearing fc = familyClearingService.getFamilyClearingById(id);
		if(fc!=null){
			fcv = new FamilyClearingView();
			BeanUtils.copyProperties(fc, fcv);
		}
		return fcv;
	}

	@Override
	public void updateFamilyClearingView(long id,
			Map<String, String> updateContent) throws TException {
		familyClearingService.updateFamilyClearing(id,updateContent);
	}


	@Override
	public int findTransactionFromViewCountByParams(long uid,
			Map<String, String> params) throws TException {
		
		try {
			return payService.findTransactionCountFromList(uid, params);
		} catch (Exception e) {
			throw new TException(e);
		}
	
	}

	@Override
	public int findTransactionToUidCount(long uid, Map<String, String> params)
			throws TException {
		
		try {
			return payService.findTransactionToUidCount(uid, params);
			
		} catch (Exception e) {
			throw new TException(e);
		}
	}

}