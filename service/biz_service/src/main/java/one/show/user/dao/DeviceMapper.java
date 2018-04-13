package one.show.user.dao;

import java.util.Map;

import one.show.user.domain.Device;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component
@DAO
public interface DeviceMapper {
	@DataSource("manageRead")
	@Results({ @Result(column = "device_uuid", property = "deviceUuid"),
		@Result(column = "app_version", property = "appVersion"),
		@Result(column = "device_name", property = "deviceName"),
		@Result(column = "kernel_version", property = "kernelVersion"),
		@Result(column = "phone_type", property = "phoneType"),
		@Result(column = "push_id", property = "pushId"),
		@Result(column = "phone_number", property = "phoneNumber"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "update_time", property = "updateTime")})
	@Select("select device_uuid,platform,uid,app_version,channel,device_name,kernel_version,phone_type,push_id,phone_number,os,create_time,update_time,sc from t_device where device_uuid = #{deviceUuid}")
	public Device findDeviceByDid( @Param("deviceUuid") String did);

	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateDevice")
	public void updateDevice( @Param("deviceUuid") String did,@Param("paramsMap") Map<String,String> paramsMap);

	@DataSource("manageWrite")
	@Insert("insert into t_device(device_uuid,platform,uid,app_version,channel,device_name,kernel_version,phone_type,push_id,phone_number,os,create_time,update_time,sc) values(#{deviceUuid},#{platform},#{uid},#{appVersion},#{channel},#{deviceName},#{kernelVersion},#{phoneType},#{pushId},#{phoneNumber},#{os},#{createTime},#{updateTime},#{sc})")
	public int insert( Device device);
	
	@DataSource("manageWrite")
	@Insert("delete from t_device where device_uuid=#{did}")
	public void delete( @Param("did") String did);

	public static class Provider {

        public String updateDevice(Map<String, Object> params) {
        	Map<String, String> updateContent =(Map<String, String>)params.get("paramsMap");
            int length = updateContent.size();
            String sql = "update t_device set ";
           
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += entry.getKey() + " = " + "#{paramsMap." + entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }
            return sql + " where device_uuid = #{deviceUuid}";
        }
	}
}
