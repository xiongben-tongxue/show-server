package one.show.pay.dao;

import java.util.List;
import java.util.Map;

import one.show.pay.domain.GiftRank;
import one.show.pay.domain.TransactionToUid;

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
public interface TransactionToUidMapper {

	@DataSource("manageWrite")
	@Insert("insert into t_transaction_to_uid (transaction_id,receive_item_id,receive_item_name,receive_item_type,receive_item_number,actually_item_id,actually_item_name,actually_item_type,actually_item_number,actually_item_price,action_type,reason,vid,uid,create_time,from_uid) values (#{transactionId},#{receiveItemId},#{receiveItemName},#{receiveItemType},#{receiveItemNumber},#{actuallyItemId},#{actuallyItemName},#{actuallyItemType},#{actuallyItemNumber},#{actuallyItemPrice},#{actionType},#{reason},#{vid},#{uid},#{createTime},#{fromUid})")
	public void saveTransactionToUid(TransactionToUid transactionToUid);
	
	@DataSource("manageRead")
	@SelectProvider(method="findTransactionToUidListByParam",type=Provider.class)
	@Results(value = { @Result(column = "transaction_id", property = "transactionId"),
			@Result(column = "receive_item_id", property = "receiveItemId"),
			@Result(column = "receive_item_name", property = "receiveItemName"),
			@Result(column = "receive_item_type", property = "receiveItemType"),
			@Result(column = "receive_item_number", property = "receiveItemNumber"),
			@Result(column = "actually_item_id", property = "actuallyItemId"),
			@Result(column = "actually_item_name", property = "actuallyItemName"),
			@Result(column = "actually_item_type", property = "actuallyItemType"),
			@Result(column = "actually_item_number", property = "actuallyItemNumber"),
			@Result(column = "actually_item_price", property = "actuallyItemPrice"),
			@Result(column = "action_type", property = "actionType"),
			@Result(column = "from_uid", property = "fromUid"),
			@Result(column = "create_time", property = "createTime")})
	public List<TransactionToUid> findTransactionToUidListByParam(@Param("uid")long uid,@Param("statement")Map<String,String> params,@Param("start")int start,@Param("end")int end);
	
	
	@DataSource("manageRead")
	@SelectProvider(method="findTransactionToUidCountByParam",type=Provider.class)
	public Integer findTransactionToUidCountByParam(@Param("uid")long uid,@Param("statement")Map<String,String> params);
	
	
	
	/**
	 * 按贡献的珍珠汇总排行榜, 不按照送出的礼物价值汇总
	 * @param uid
	 * @param start
	 * @param pageCount
	 * @return
	 */
	@DataSource("manageRead")
	@Results(value = {@Result(column = "from_uid", property = "fromUid")})
	@Select("select t.uid,t.from_uid,sum(t.num) total,t.create_time time from (SELECT uid,from_uid,receive_item_number num,create_time fROM t_transaction_to_uid where uid=#{uid} and receive_item_type=2 and receive_Item_id=2 order by create_time asc) t group by from_uid  order by total desc,time asc limit #{start},#{pageCount}")
	public List<GiftRank> findGiftRanks(@Param("uid")long uid,@Param("start")int start,@Param("pageCount")int pageCount);
	
	
	public static class Provider {

		public String findTransactionToUidListByParam(Map<String, Object> params) {
			String sql = "select transaction_id,receive_item_id,receive_item_name,receive_item_type,receive_item_number,actually_item_id,actually_item_name,actually_item_type,actually_item_number,actually_item_price,action_type,reason,vid,uid,create_time,from_uid from t_transaction_to_uid where uid=#{uid} ";
			if(params.get("statement")!=null){
				Map<String, String> paramMap = (Map<String, String>) params.get("statement");
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{statement." + entry.getKey()+ "}";
				}
			}
			sql +=" order by create_time desc limit #{start},#{end}"; 
			return sql;
		}
		
		public String findTransactionToUidCountByParam(Map<String, Object> params) {
			String sql = "select count(*) from t_transaction_to_uid where uid=#{uid} ";
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
