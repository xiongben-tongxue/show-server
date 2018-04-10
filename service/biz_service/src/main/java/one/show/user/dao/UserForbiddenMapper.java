package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.domain.Forbidden;

import org.apache.ibatis.annotations.Delete;
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

@Component("forbiddenMapper")
@DAO(catalog = "user")
public interface UserForbiddenMapper {

	@DataSource("manageRead")
	@Select("select uid,expire_time,action,forbiddenby,create_time from t_user_forbidden where uid = #{uid}")
	@Results({ @Result(column="expire_time",property="expireTime"), 
		@Result(column="create_time",property="createTime") })
	public List<Forbidden> findForbiddenListByUid(@Param("uid") long uid);

	@DataSource("manageRead")
	@SelectProvider(method = "getActionList", type = Provide.class)
	public List<Integer> getForbiddenActionListByUid(@Param("uid") long uid);
	
	@DataSource("manageRead")
	@SelectProvider(method = "findForbidden", type = Provide.class)
	@Results({ @Result(column="expire_time",property="expireTime"), 
		@Result(column="create_time",property="createTime") })
	public Forbidden findForbiddenByActionAndUid(@Param("uid")long uid,@Param("action") int action);
	
	@DataSource("manageWrite")
	@Insert("insert into t_user_forbidden (uid,expire_time,action,forbiddenby,create_time) values (#{uid},#{expireTime},#{action},#{forbiddenby},#{createTime})")
	public void saveForbidden(Forbidden forbidden);
	
	@DataSource("manageWrite")
	@Delete("delete from t_user_forbidden where uid=#{uid}")
	public void deleteForbiddenByUid(@Param("uid") long uid);
	@DataSource("manageWrite")
	@Delete("delete from t_user_forbidden where uid=#{uid} and action=#{action}")
	public void deleteForbiddenByUidAndAction(@Param("uid") long uid,@Param("action") int action);
	public static class Provide{
		
		public String getActionList(Map<String, Object> parms){
			int now = (int)(System.currentTimeMillis()/1000l);
			long uid = Long.parseLong(parms.get("uid").toString());
			String sql = "select action from t_user_forbidden where uid = '"+ uid + "' and expire_time > "+ now;
			return sql;
		}
		
		public String findForbidden(Map<String, Object> parms){
			int now = (int)(System.currentTimeMillis()/1000l);
			String sql = "select uid,expire_time,action,forbiddenby,create_time from t_user_forbidden where uid = #{uid} and action= #{action} and expire_time > "+ now;
			return sql;
		}
	}
}
