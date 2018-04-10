package one.show.pay.dao;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import one.show.pay.thrift.view.OrdersView;
import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

import one.show.pay.domain.Orders;
import one.show.pay.domain.PayConfig;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@DAO(catalog = "pay")
public interface OrdersMapper {

	@DataSource("manageWrite")
	@Insert("insert into t_orders "
			+ "(order_id,uid, pay_status, pay_type, pay_money, pay_time, create_time,item_num,item_id,item_type, did, receipt, platform,transaction_id) "
			+ "values "
			+ "(#{orderId},#{uid}, #{payStatus}, #{payType}, #{payMoney}, #{payTime}, #{createTime},#{itemNum},#{itemId},#{itemType}, #{did},  #{receipt}, #{platform}, #{transactionId})")
	public void saveOrders(Orders orders);


	@DataSource("manageRead")
	@Select("select order_id,uid, pay_status, pay_type, pay_money, pay_time, create_time, item_num,item_id,item_type, did,transaction_id from t_orders where order_id = #{orderId}")
	@Results(value = { @Result(column = "order_id", property = "orderId"),
			@Result(column = "pay_status", property = "payStatus"),
			@Result(column = "pay_type", property = "payType"),
			@Result(column = "pay_money", property = "payMoney"),
			@Result(column = "pay_time", property = "payTime"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "transaction_id", property = "transactionId"),
			@Result(column = "item_num", property = "itemNum"),
			@Result(column = "item_id", property = "itemId"),
			@Result(column = "item_type", property = "itemType")
			})
	public Orders getOrdersById(long orderId);
	

	@DataSource("manageRead")
	@Select("select order_id,uid, pay_status, pay_type, pay_money, pay_time, create_time, item_num,item_id,item_type, did,receipt,transaction_id from t_orders where receipt = #{receipt}")
	@Results(value = { @Result(column = "order_id", property = "orderId"),
			@Result(column = "pay_status", property = "payStatus"),
			@Result(column = "pay_type", property = "payType"),
			@Result(column = "pay_money", property = "payMoney"),
			@Result(column = "pay_time", property = "payTime"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "transaction_id", property = "transactionId"),
			@Result(column = "item_num", property = "itemNum"),
			@Result(column = "item_id", property = "itemId"),
			@Result(column = "item_type", property = "itemType")
			})
	public OrdersView getOrdersByReceipt(String md5_receipt);

	@DataSource("manageRead")
	@SelectProvider(method="getOrdersByUserId",type=Provider.class)
	@Results(value = { @Result(column = "order_id", property = "orderId"),
			@Result(column = "pay_status", property = "payStatus"),
			@Result(column = "pay_type", property = "payType"),
			@Result(column = "pay_money", property = "payMoney"),
			@Result(column = "pay_time", property = "payTime"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "transaction_id", property = "transactionId"),
			@Result(column = "item_num", property = "itemNum"),
			@Result(column = "item_id", property = "itemId"),
			@Result(column = "item_type", property = "itemType")
			})
	public List<Orders> getOrdersByUserId(@Param("userId")long userId,@Param("paramMap")Map<String,String> params,@Param("start")int start,@Param("end")int end);
	
	@DataSource("manageRead")
	@Results(value = { @Result(column = "order_id", property = "orderId"),
			@Result(column = "pay_status", property = "payStatus"),
			@Result(column = "pay_type", property = "payType"),
			@Result(column = "pay_money", property = "payMoney"),
			@Result(column = "pay_time", property = "payTime"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "transaction_id", property = "transactionId"),
			@Result(column = "item_num", property = "itemNum"),
			@Result(column = "item_id", property = "itemId"),
			@Result(column = "item_type", property = "itemType")
			})
	@SelectProvider(method = "getOrdersListByParams", type = Provider.class)
	public List<Orders> getOrdersListByParams(@Param("paramMap") Map<String, String> paramMap, @Param("start")int start, @Param("count")int count);
	
	@DataSource("manageRead")
	@SelectProvider(method = "getOrdersCountByParams", type = Provider.class)
	public int getOrdersCountByParams(@Param("paramMap") Map<String, String> paramMap);
	
	@DataSource("manageRead")
	@SelectProvider(method = "getOrdersMoneyByParams", type = Provider.class)
	public int getOrdersMoneyByParams(@Param("paramMap") Map<String, String> paramMap);
	
	
	@DataSource("manageWrite")
	@UpdateProvider(method = "updateOrdersById", type = Provider.class)
	public int updateOrdersById(long orderId,
			@Param("paramMap") Map<String, String> paramMap);


	@DataSource("manageWrite")
	@UpdateProvider(method = "updateOrdersByConditions", type = Provider.class)
	public int updateOrdersByConditions(@Param("conditions") Map<String, String> conditions,
			@Param("paramMap") Map<String, String> paramMap);

	@SuppressWarnings("unchecked")
	public static class Provider {

		private static final Logger log = LoggerFactory.getLogger(Provider.class);

		public String updateOrdersById(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			int length = paramMap.size();
			String sql = "update t_orders set ";
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{paramMap." + entry.getKey()
						+ "}";
				if (length > 0)
					sql += ",";
			}
			sql = sql + " where order_id = #{orderId}";
			return sql;
		}

		public String updateOrdersByConditions(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params.get("paramMap");
			Map<String, String> conditions = (Map<String, String>) params.get("conditions");

			StringBuilder sb = new StringBuilder();
			sb.append("update t_orders set ");
			sb.append(Joiner.on(",").join(
					Iterables.transform(paramMap.keySet(), new Function<String, String>() {
						public String apply(String key) { return key + " = " + "#{paramMap." + key+ "}"; }
					})
			));

			sb.append(" where ");

			sb.append(Joiner.on(" and ").join(
					Iterables.transform(conditions.keySet(), new Function<String, String>() {
						public String apply(String key) { return key + " = " + "#{conditions." + key+ "}"; }
					})
			));

			String s = sb.toString();
			log.info("sql : {}", s);
			return s;
		}
		
		
		public String getOrdersMoneyByParams(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select ifnull(sum(pay_money),0) from t_orders where 1=1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					
					if (entry.getKey().equals("begin_time")){
						sql += " and `create_time`" + ">="
								+ "#{paramMap." + entry.getKey() + "}";
					}else if (entry.getKey().equals("end_time")){
						sql += " and `create_time`" + "<="
								+ "#{paramMap." + entry.getKey() + "}";
					}else{
						sql += " and `" + entry.getKey() + "`" + "="
								+ "#{paramMap." + entry.getKey() + "}";
					}
					
				}
			}
			
			return sql;
		}
		
		
		public String getOrdersCountByParams(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select count(*) from t_orders where 1=1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					
					if (entry.getKey().equals("begin_time")){
						sql += " and `create_time`" + ">="
								+ "#{paramMap." + entry.getKey() + "}";
					}else if (entry.getKey().equals("end_time")){
						sql += " and `create_time`" + "<="
								+ "#{paramMap." + entry.getKey() + "}";
					}else{
						sql += " and `" + entry.getKey() + "`" + "="
								+ "#{paramMap." + entry.getKey() + "}";
					}
					
				}
			}
			
			return sql;
		}
		
		
		public String getOrdersListByParams(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select order_id,uid, pay_status, pay_type, pay_money, pay_time, create_time,item_num,item_id,item_type, did, platform,transaction_id from t_orders where 1=1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					if (entry.getKey().equals("begin_time")){
						sql += " and `create_time`" + ">="
								+ "#{paramMap." + entry.getKey() + "}";
					}else if (entry.getKey().equals("end_time")){
						sql += " and `create_time`" + "<="
								+ "#{paramMap." + entry.getKey() + "}";
					}else{
						sql += " and `" + entry.getKey() + "`" + "="
								+ "#{paramMap." + entry.getKey() + "}";
					}
				}
			}
			
			sql += " order by create_time desc limit #{start}, #{count}";
			System.out.println(sql);
			return sql;
		}
		public String getOrdersByUserId(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select order_id,uid, pay_status, pay_type, pay_money, pay_time, create_time,item_num,item_id,item_type, did,platform,transaction_id from t_orders where uid=#{userId}";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					if (entry.getKey().equals("begin_time")){
						sql += " and `create_time`" + ">="
								+ "#{paramMap." + entry.getKey() + "}";
					}else if (entry.getKey().equals("end_time")){
						sql += " and `create_time`" + "<="
								+ "#{paramMap." + entry.getKey() + "}";
					}else{
						sql += " and `" + entry.getKey() + "`" + "="
								+ "#{paramMap." + entry.getKey() + "}";
					}
				}
			}
			
			sql += " order by create_time desc limit #{start}, #{end}";
			
			return sql;
		}
	}
	
	
	@DataSource("manageRead")
	@Select("select id,charge_count,showcoin,display_showcoin,product_id,var_item,platform,item_type,item_id from t_pay_config")
	@Results({ @Result(column = "charge_count", property = "chargeCount"),
		@Result(column = "showcoin", property = "showCoin"),
		@Result(column = "display_showcoin", property = "displayShowCoin"),
		@Result(column = "product_id", property = "productId"),
		@Result(column = "var_item", property = "varItem"),
		@Result(column = "item_type", property = "itemType"),
		@Result(column = "item_id", property = "itemId")})
	public List<PayConfig> getPayConfigs();
}