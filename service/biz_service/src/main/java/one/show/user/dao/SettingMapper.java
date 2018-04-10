package one.show.user.dao;

import java.util.Map;

import one.show.user.domain.Setting;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component("settingMapper")
@DAO(catalog = "user")
public interface SettingMapper {

	@DataSource("manageRead")
	@Select("select uid,no_disturb,notify_follow,default_text,hi,notify_at,share_when_like,friends_entry,notify_comment,notify_like,pushed,vibration,publish_video,gift_video,share_when_comment from t_setting where uid = #{uid}")
	public Setting getSettingByUid( @Param("uid") String uid);

	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class,method = "updateSwith")
	void setSwitch( @Param("uid") String uid,@Param("paramMap") Map<String, String> paramMap);

	public static class Provider{
		
		 public String updateSwith(Map<String, Object> params) {
	            Map<String, String> paramMap = (Map<String, String>) params.get("paramMap");
	            int length = paramMap.size();
	            String sql = "update t_setting set ";
	            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
	                length--;
	                sql += entry.getKey() + " = " + "#{paramMap." + entry.getKey()+"}";
	                if (length > 0)
	                    sql += ",";
	            }
	            sql = sql + " where uid = #{uid}";
	            return sql;
	        }
	}
}
