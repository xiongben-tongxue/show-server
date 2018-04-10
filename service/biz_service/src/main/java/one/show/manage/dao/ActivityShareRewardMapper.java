package one.show.manage.dao;

import java.util.Map;

import one.show.manage.domain.ActivityShareReward;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;


/**
 * t_activity_share_rewardMapper
 * @author hank
 * @date Tue Aug 09 19:00:34 CST 2016
 *
 */
@Component("activityShareRewardMapper")
@DAO
public interface ActivityShareRewardMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "share_date", property = "shareDate"),
		@Result(column = "reward_1", property = "reward1"),
		@Result(column = "reward_2", property = "reward2"),
		@Result(column = "reward_3", property = "reward3"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "update_time", property = "updateTime")})
	@Select("select share_date,reward_1,reward_2,reward_3,create_time,update_time from t_activity_share_reward where share_date = #{shareDate}")
	public ActivityShareReward findByDate(@Param("shareDate") int shareDate);

	/**
	 * 添加
	 * @param ActivityShareReward
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_activity_share_reward(share_date,reward_1,reward_2,reward_3,create_time,update_time) values(#{shareDate},#{reward1},#{reward2},#{reward3},#{createTime},#{updateTime})")
	public int insertActivityShareReward(ActivityShareReward reward);
	/**
	 * 更新
	 * @param ActivityShareReward
	 */
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	void updateActivityShareRewardById(@Param("shareDate") int shareDate, @Param("updateContent") Map<String, String> updateContent);
	/**
	 * 删除
	 */
	public static class Provider {
	
		public String updateById(Map<String, Object> params) {
			Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
			int length = updateContent.size();
			String sql = "update t_activity_share_reward set ";
			for (Map.Entry<String, String> entry : updateContent.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
				if (length > 0)
				sql += ",";
			}
			sql = sql + " where shareDate = #{shareDate}";
			return sql;
		}
		
		public String addToShareReward(Map<String, Object> params) {
			Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
			int length = updateContent.size();
			String sql = "update t_activity_share_reward set ";
			for (Map.Entry<String, String> entry : updateContent.entrySet()) {
				length--;
				if(entry.getKey().equals("update_time")){
					sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
				}else{
					sql += entry.getKey() + " = "+ entry.getKey() + "+#{updateContent." + entry.getKey()+"}";
				}
				if (length > 0)
				sql += ",";
			}
			sql = sql + " where share_date = #{shareDate}";
			return sql;
		}
	}
	
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "addToShareReward")
	public void addToShareReward(@Param("shareDate")int shareDate,@Param("updateContent") Map<String, String> updateContent);
}