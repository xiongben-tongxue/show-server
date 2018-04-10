package one.show.video.dao;

import java.util.List;
import java.util.Map;

import one.show.video.domain.LiveUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * 直播Mapper
 * @author Haliaeetus leucocephalus
 * @date Thu May 19 12:18:00 CST 2016
 *
 */
@Component
@DAO
public interface LiveUserMapper{

	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findIdListByUid")
	public List<Long> findIdListByUid( @Param("uid")Long uid,  @Param("sort")String sort, @Param("condition") Map<String, String> condition, @Param("start")Integer start, @Param("count")Integer count);
	
	
	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "vod_status", property = "vodStatus")})
	@SelectProvider(type = Provider.class, method = "findLiveUserListByUid")
	public List<LiveUser> findLiveUserListByUid( @Param("uid")Long uid,  @Param("sort")String sort, @Param("condition") Map<String, String> condition, @Param("start")Integer start, @Param("count")Integer count);


	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findCountByUid")
	public int findCountByUid( @Param("uid")Long uid, @Param("condition") Map<String, String> condition);
	
	
	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "vod_status", property = "vodStatus")})
	@Select("select liveid,uid,snapshot,create_time,viewed,stream_name,cdn_type,status,vod_status from t_live_user where liveid = #{liveId}")
	public LiveUser findLiveUser( @Param("uid")Long uid, @Param("liveId")long liveId);

	@DataSource("manageWrite")
	@Insert("insert into t_live_user(liveid,uid,snapshot,stream_name, cdn_type, viewed, create_time, status, vod_status) values(#{liveId},#{uid},#{snapshot},#{streamName},#{cdnType},#{viewed},#{createTime},#{status},#{vodStatus})")
	public void save( LiveUser liveUser);
	
	/**
	 * 更新
	 * @param LiveUser0
	 */
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	public void updateLiveUser( @Param("uid")Long uid, @Param("liveId")long liveId, @Param("updateContent") Map<String, String> updateContent);
	
	 public static class Provider {
	    	
			 public String findLiveUserListByUid(Map<String, Object> params) {
	 		 	Map<String, String> condition = (Map<String, String>) params.get("condition");
	 			String sort = (String)params.get("sort");
	 			String sql = "select liveid,uid,snapshot,stream_name, cdn_type, create_time, status, vod_status from t_live_user where uid = #{uid}";
	 			if (condition != null){
		            for (Map.Entry<String, String> entry : condition.entrySet()) {
		                sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+"}";
		            }
	 			}
	 			return sql+" order by "+sort+" desc limit #{start}, #{count}";
			 }
		 
	    	public String findIdListByUid(Map<String, Object> params) {
	    		 	Map<String, String> condition = (Map<String, String>) params.get("condition");
	    			String sort = (String)params.get("sort");
	    			String sql = "select liveid from t_live_user where uid = #{uid}";
	    			if (condition != null){
	    	            for (Map.Entry<String, String> entry : condition.entrySet()) {
	    	                sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+"}";
	    	            }
	    			}   
	    			return sql+" order by "+sort+" desc limit #{start}, #{count}";
	        }
	    	
	    	public String findCountByUid(Map<String, Object> params) {
	    		Map<String, String> condition = (Map<String, String>) params.get("condition");
    			String sql = "select count(*) from t_live_user where uid = #{uid}";
    			if (condition != null){
	    			for (Map.Entry<String, String> entry : condition.entrySet()) {
		                sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+"}";
		            }
    			}
    			return sql;
	    	}
	    	
	    	public String updateById(Map<String, Object> params) {
				Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
				int length = updateContent.size();
				String sql = "update t_live_user set ";
				for (Map.Entry<String, String> entry : updateContent.entrySet()) {
					length--;
					sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
					if (length > 0)
					sql += ",";
				}
				sql = sql + " where liveid = #{liveId}";
				return sql;
			}
	 }
	

  
}