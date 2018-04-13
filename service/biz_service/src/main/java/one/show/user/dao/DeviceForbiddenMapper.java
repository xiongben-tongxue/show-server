package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.domain.DeviceForbidden;

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

@Component
@DAO
public interface DeviceForbiddenMapper {

	@DataSource("manageRead")
	@Select("select did,expire_time,forbiddenby,create_time from t_device_forbidden where did = #{did}")
	@Results({ @Result(column="expire_time",property="expireTime"), 
		@Result(column="create_time",property="createTime") })
	public List<DeviceForbidden> findDeviceForbiddenListByDid(@Param("did") String did);
	
	@DataSource("manageWrite")
	@Insert("insert into t_device_forbidden (did,expire_time,forbiddenby,create_time) values (#{did},#{expireTime},#{forbiddenby},#{createTime})")
	public void saveForbidden(DeviceForbidden deviceForbidden);
	
	@DataSource("manageWrite")
	@Delete("delete from t_device_forbidden where did=#{did}")
	public void deleteForbidden(String did);

	@DataSource("manageRead")
	@SelectProvider(method = "getForbidden", type = Provide.class)
	public List<DeviceForbidden> findForbiddenByDid(@Param("did") String did);

	public static class Provide{

		public String getForbidden(Map<String, Object> parms){
			String sql = "select * from t_device_forbidden where did = #{did} and expire_time > unix_timestamp(now())";
			return sql;
		}
	}

}
