package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.ActivityConfig;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;


/**
 * 活动基础配置表Mapper
 * @author hank
 * @date Mon Aug 08 20:01:28 CST 2016
 *
 */
@Component("activityConfigMapper")
@DAO
public interface ActivityConfigMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "open_activity", property = "openActivity"),
		@Result(column = "is_activating", property = "isActivating"),
		@Result(column = "activity_config", property = "activityConfig"),
		@Result(column = "icon_index", property = "iconIndex"),
		@Result(column = "order_index", property = "orderIndex"),
		@Result(column = "open_start_time", property = "openStartTime"),
		@Result(column = "open_end_time", property = "openEndTime"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "limit_gread", property = "limitGread"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "update_time", property = "updateTime")})
	@Select("select id,open_activity,name,description,is_activating,activity_config,icon_index,order_index,open_start_time,open_end_time,start_time,end_time,limit_gread,create_time,update_time from t_activity_config where id = #{id}")
	public ActivityConfig findById(@Param("id") int id);

	@DataSource("manageRead")
	@Results({ @Result(column = "open_activity", property = "openActivity"),
		@Result(column = "is_activating", property = "isActivating"),
		@Result(column = "activity_config", property = "activityConfig"),
		@Result(column = "icon_index", property = "iconIndex"),
		@Result(column = "order_index", property = "orderIndex"),
		@Result(column = "open_start_time", property = "openStartTime"),
		@Result(column = "open_end_time", property = "openEndTime"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "limit_gread", property = "limitGread"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "update_time", property = "updateTime")})
	@Select("select id,open_activity,name,description,is_activating,activity_config,icon_index,order_index,open_start_time,open_end_time,start_time,end_time,limit_gread,create_time,update_time from t_activity_config")
	public List<ActivityConfig> findAll();
	/**
	 * 添加
	 * @param ActivityConfig
	 */
	@DataSource("manageWrite")
	@Insert("insert into manage(id,open_activity,name,description,is_activating,activity_config,icon_index,order_index,open_start_time,open_end_time,start_time,end_time,limit_gread,create_time,update_time) values(#{id},#{openActivity},#{name},#{description},#{isActivating},#{activityConfig},#{iconIndex},#{orderIndex},#{openStartTime},#{openEndTime},#{startTime},#{endTime},#{limitGread},#{createTime},#{updateTime})")
	public int insertActivityConfig(ActivityConfig config);
	/**
	 * 更新
	 * @param ActivityConfig
	 */
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateActivityConfigById")
	void updateActivityConfigById(@Param("id") int id, @Param("updateContent") Map<String, String> updateContent);

	public static class Provider {
		public String updateActivityConfigById(Map<String, Object> params) {
			Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
			int length = updateContent.size();
			String sql = "update t_activity_config set ";
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