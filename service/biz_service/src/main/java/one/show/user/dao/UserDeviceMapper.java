package one.show.user.dao;

import java.util.List;

import one.show.user.domain.UserDevice;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component
@DAO
public interface UserDeviceMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "device_uuid", property = "deviceUuid"),
		@Result(column = "create_time", property = "createTime")})
	@Select("select uid,device_uuid,create_time from t_user_device where uid = #{uid} and device_uuid = #{deviceUuid}")
	public UserDevice findById( @Param("uid") long uid,@Param("deviceUuid") String deviceUuid);

	@DataSource("manageRead")
	@Select("select uid,device_uuid,create_time from t_user_device where uid = #{uid}")
	public List<UserDevice> findListByUid( @Param("uid") long uid);
	/**
	 * 添加
	 * @param UserDevice
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_user_device(uid,device_uuid,create_time) values(#{uid},#{deviceUuid},#{createTime})")
	public int insertUserDevice(UserDevice device);
	
	@DataSource("manageWrite")
	@Insert("delete from t_user_device where uid=#{uid}")
	public int deleteUserDeviceByUid( @Param("uid") long uid);

}
