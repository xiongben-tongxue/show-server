package one.show.pay.dao;

import java.util.List;
import java.util.Map;

import one.show.pay.domain.Stock;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component
@DAO(catalog = "pay")
public interface StockMapper {
	
	
	@DataSource("manageWrite")
	@Insert("insert into t_stock (uid,item_id,item_name,item_type,item_number,create_time,update_time,dead_line) values (#{uid},#{itemId},#{itemName},#{itemType},#{itemNumber},#{createTime},#{updateTime},#{deadLine})")
	public void saveStock(Stock stock);
	
	@DataSource("manageWrite")
	@Update("update t_stock set item_number=item_number+#{number}, update_time=#{now} where uid=#{uid} and item_type=#{itemType} and item_id=#{itemId}")
	public void increaseItemNum(@Param("uid")long uid,@Param("itemType")Integer itemType, @Param("itemId")Integer itemId, @Param("number")Double number, @Param("now")Integer now);
	
	@DataSource("manageWrite")
	@Update("update t_stock set item_number=item_number-#{number}, update_time=#{now} where uid=#{uid} and item_type=#{itemType} and item_id=#{itemId} and item_number>=#{number}")
	public int reduceItemNum(@Param("uid")long uid,@Param("itemType")Integer itemType, @Param("itemId")Integer itemId, @Param("number")Double number, @Param("now")Integer now);
	
	@DataSource("manageWrite")
	@Update("update t_stock set dead_line=#{deadLine}, update_time=UNIX_TIMESTAMP(now()) where uid=#{uid} and item_type=#{itemType} and item_id=#{itemId}")
	public void updateDealLine(@Param("uid")long uid,@Param("itemType")Integer itemType, @Param("itemId")Integer itemId, @Param("deadLine")Integer deadLine);
	
	@DataSource("manageRead")
	@Select("select id,uid,item_id,item_name,item_type,item_number,create_time,update_time,dead_line from t_stock where uid=#{uid} and item_type=#{itemType} and item_id=#{itemId}")
	@Results(value = { @Result(column = "item_id", property = "itemId"),
			@Result(column = "item_name", property = "itemName"),
			@Result(column = "item_type", property = "itemType"),
			@Result(column = "item_number", property = "itemNumber"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime"),
			@Result(column = "dead_line", property = "deadLine")})
	public Stock findStock(@Param("uid")long uid,@Param("itemType")Integer itemType, @Param("itemId")Integer itemId);
	
	@DataSource("manageRead")
	@SelectProvider(method="findStockByParam",type=Provider.class)
	@Results(value = { @Result(column = "item_id", property = "itemId"),
			@Result(column = "item_name", property = "itemName"),
			@Result(column = "item_type", property = "itemType"),
			@Result(column = "item_number", property = "itemNumber"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime"),
			@Result(column = "dead_line", property = "deadLine")})
	public List<Stock> findStockByParam(@Param("uid")long uid,@Param("statement")Map<String,String> params);
	
	public static class Provider {
		
		public String findAllStock(Map<String, Object> params) {
			Integer tbid = (Integer)params.get("tbid");
			return "select id,uid,item_id,item_name,item_type,item_number,create_time,update_time,dead_line from t_stock_"+tbid;
		}

		public String findStockByParam(Map<String, Object> params) {
			String sql = "select id,uid,item_id,item_name,item_type,item_number,create_time,update_time,dead_line from t_stock where uid=#{uid} ";
			if(params.get("statement")!=null){
				Map<String, String> paramMap = (Map<String, String>) params.get("statement");
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{statement." + entry.getKey()+ "}";
				}
			}
			sql +=" order by create_time desc"; 
			return sql;
		}
	}
}
