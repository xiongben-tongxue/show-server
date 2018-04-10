
package one.show.stat.dao;

import java.util.Map;

import one.show.stat.domain.ActiveXbStat;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * @author Haliaeetus leucocephalus  2016年10月20日 下午5:23:51
 *
 * 
 */
@Component
public interface ActiveXbStatMapper {
	
	@DataSource("manageRead")
	@Results({ @Result(column = "gift_price", property = "giftPrice"),
		@Result(column = "gift_count", property = "giftCount"),
		@Result(column = "share_count", property = "shareCount"),
		@Result(column = "online_count", property = "onlineCount")})
	@Select("select id,uid,gift_price,gift_count,share_count,online_count,time from t_active_xb_stat where id = #{id}")
	public ActiveXbStat findById(@Param("id") String id);
	
	@DataSource("manageRead")
	@Results({ @Result(column = "gift_price", property = "giftPrice"),
		@Result(column = "gift_count", property = "giftCount"),
		@Result(column = "share_count", property = "shareCount"),
		@Result(column = "online_count", property = "onlineCount")})
	@Select("select id,uid,gift_price,gift_count,share_count,online_count,time from t_active_xb_stat where id = #{id}")
	public ActiveXbStat findByUidAndTime(@Param("uid") Long uid, @Param("time") Integer time);

	/**
	 * 添加
	 * @param ActiveXbStat
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_active_xb_stat(uid,gift_price,gift_count,share_count,online_count,time) values(#{uid},#{giftPrice},#{giftCount},#{shareCount},#{onlineCount},#{time})")
	public void insert(ActiveXbStat stat);
	/**
	 * 更新
	 * @param ActiveXbStat
	 */
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	public void updateById(@Param("id") Long id, @Param("updateContent") Map<String, String> updateContent);
	
	/**
	 * 删除
	 */
	public static class Provider {
		
		public String updateById(Map<String, Object> params) {
			Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
			int length = updateContent.size();
			String sql = "update t_active_xb_stat set ";
			for (Map.Entry<String, String> entry : updateContent.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
				if (length > 0)
				sql += ",";
			}
			sql = sql + " where id = #{id}";
			return sql;
		}
	}

}


