package one.show.manage.dao;

import java.util.Map;

import one.show.manage.domain.ActivityShare;

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

@Component("activityShareMapper")
@DAO
public interface ActivityShareMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "share_times", property = "shareTimes"),
		@Result(column = "share_reward", property = "shareReward"),
		@Result(column = "last_share_time", property = "lastShareTime")})
	@Select("select did,share_times,share_reward,last_share_time from t_activity_share where did = #{did}")
	public ActivityShare findById( @Param("did") String did);

	/**
	 * 添加
	 * @param ActivityShare
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_activity_share(did,share_times,share_reward,last_share_time) values(#{did},#{shareTimes},#{shareReward},#{lastShareTime})")
	public int insertActivityShare(ActivityShare share);
	/**
	 * 更新
	 * @param ActivityShare
	 */
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	void updateById(@Param("did") String did, @Param("updateContent") Map<String, String> updateContent);

	public static class Provider {
		public String updateById(Map<String, Object> params) {
			Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
			int length = updateContent.size();
			String sql = "update t_activity_share set ";
			for (Map.Entry<String, String> entry : updateContent.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
				if (length > 0)
				sql += ",";
			}
			sql = sql + " where did = #{did}";
			return sql;
		}
	}
	@DataSource("manageWrite")
	@Update("update t_activity_share set share_times=share_times+1,share_reward=#{shareReward},last_share_time=#{updateTime} where did=#{did}")
	public void addToUserShare(@Param("did")String did, @Param("shareReward")String shareReward, @Param("updateTime")int updateTime);
}
