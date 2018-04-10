package one.show.pay.dao;

import java.util.List;
import java.util.Map;

import one.show.pay.domain.StockLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component
@DAO(catalog = "pay")
public interface StockLogMapper {

	@DataSource("manageWrite")
	@Insert("insert into t_stock_log "
			+ "(uid,item_id, item_name, item_type, item_number, operate, action_time, action_type, action_desc, before_change, after_change) "
			+ "values "
			+ "(#{uid}, #{itemId}, #{itemName}, #{itemType}, #{itemNumber}, #{operate}, #{actionTime},#{actionType},#{actionDesc},#{beforeChange},#{afterChange})")
	public void saveStockLog( StockLog stockLog);

	@DataSource("manageRead")
	@SelectProvider(method = "getStockLogListByUid", type = Provider.class)
	@Results(value = { @Result(column = "order_id", property = "orderId"),
			@Result(column = "action_time", property = "actionTime"),
			@Result(column = "action_type", property = "actionType"),
			@Result(column = "action_desc", property = "actionDesc"),
			@Result(column = "before_change", property = "beforeChange"),
			@Result(column = "after_change", property = "afterChange"),
			@Result(column = "item_id", property = "itemId"),
			@Result(column = "item_name", property = "itemName"),
			@Result(column = "item_type", property = "itemType"),
			@Result(column = "item_number", property = "itemNumber")})
	public List<StockLog> getStockLogListByUid( @Param("uid") long uid, @Param("paramMap") Map<String, String> paramMap,  @Param("start")Integer start,  @Param("count")Integer count);
	
	@DataSource("manageRead")
	@SelectProvider(method = "getStockLogCountByUid", type = Provider.class)
	public Integer getStockLogCountByUid( @Param("uid") long uid, @Param("paramMap") Map<String, String> paramMap);

	@DataSource("manageRead")
	@SelectProvider(method = "getStockLogItemTotalByUid", type = Provider.class)
	public Double getStockLogItemTotalByUid( @Param("uid") long uid, @Param("paramMap") Map<String, String> paramMap);

	@SuppressWarnings("unchecked")
	public static class Provider {

		public String getStockLogCountByUid(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select count(*) from t_stock_log where uid = #{uid}";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and `" + entry.getKey() + "`" + "="
							+ "#{paramMap." + entry.getKey() + "}";
				}
			}
			
			return sql;
		}
		
		
		public String getStockLogListByUid(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select id,uid,item_id, item_name, item_type, item_number, operate, action_time, action_type, action_desc, before_change, after_change from t_stock_log where uid = #{uid}";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and `" + entry.getKey() + "`" + "="
							+ "#{paramMap." + entry.getKey() + "}";
				}
			}
			
			sql += " order by action_time desc limit #{start}, #{count}";
			
			return sql;
		}
		
		public String getStockLogItemTotalByUid(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select count(item_number) from t_stock_log where uid = #{uid}";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and `" + entry.getKey() + "`" + "="
							+ "#{paramMap." + entry.getKey() + "}";
				}
			}
			
			return sql;
		}
	}
}