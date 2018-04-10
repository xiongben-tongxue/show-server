package one.show.pay.dao;

import java.util.List;
import java.util.Map;

import one.show.pay.domain.TransactionFrom;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component
@DAO(catalog = "pay")
public interface TransactionFromMapper {
	@DataSource("manageWrite")
	@Insert("insert into t_transaction_from (transaction_id,give_item_id,give_item_name,give_item_type,give_item_number,actually_item_id,actually_item_name,actually_item_type,actually_item_number,actually_item_price,action_type,reason,uid,create_time,to_uid) values (#{transactionId},#{giveItemId},#{giveItemName},#{giveItemType},#{giveItemNumber},#{actuallyItemId},#{actuallyItemName},#{actuallyItemType},#{actuallyItemNumber},#{actuallyItemPrice},#{actionType},#{reason},#{uid},#{createTime},#{toUid})")
	public void saveTransactionFrom(TransactionFrom transactionFrom);
	
	@DataSource("manageRead")
	@SelectProvider(method="findTransactionFromByParams",type=Provider.class)
	@Results(value = { @Result(column = "transaction_id", property = "transactionId"),
			@Result(column = "give_item_id", property = "giveItemId"),
			@Result(column = "give_item_name", property = "giveItemName"),
			@Result(column = "give_item_type", property = "giveItemType"),
			@Result(column = "give_item_number", property = "giveItemNumber"),
			@Result(column = "actually_item_id", property = "actuallyItemId"),
			@Result(column = "actually_item_name", property = "actuallyItemName"),
			@Result(column = "actually_item_type", property = "actuallyItemType"),
			@Result(column = "actually_item_number", property = "actuallyItemNumber"),
			@Result(column = "actually_item_price", property = "actuallyItemPrice"),
			@Result(column = "action_type", property = "actionType"),
			@Result(column = "to_uid", property = "toUid"),
			@Result(column = "create_time", property = "createTime")})
	public List<TransactionFrom> findTransactionFromByParams(@Param("uid")long uid,@Param("statement")Map<String,String> params,@Param("start")int start,@Param("end")int end);

	@DataSource("manageRead")
	@SelectProvider(method="findTransactionCountFromByParams",type=Provider.class)
	public Integer findTransactionCountFromByParams(@Param("uid")long uid,@Param("statement")Map<String,String> params);

	
	public static class Provider {

		public String findTransactionFromByParams(Map<String, Object> params) {
			String sql = "select transaction_id,give_item_id,give_item_name,give_item_type,give_item_number,actually_item_id,actually_item_name,actually_item_type,actually_item_number,actually_item_price,action_type,reason,uid,create_time,to_uid from t_transaction_from where uid=#{uid} ";
			if(params.get("statement")!=null){
				Map<String, String> paramMap = (Map<String, String>) params.get("statement");
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{statement." + entry.getKey()+ "}";
				}
			}
			sql +=" order by create_time desc limit #{start},#{end}"; 
			return sql;
		}
		
		public String findTransactionCountFromByParams(Map<String, Object> params) {
			String sql = "select count(*) from t_transaction_from where uid=#{uid} ";
			if(params.get("statement")!=null){
				Map<String, String> paramMap = (Map<String, String>) params.get("statement");
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{statement." + entry.getKey()+ "}";
				}
			}
			return sql;
		}
	}
}
