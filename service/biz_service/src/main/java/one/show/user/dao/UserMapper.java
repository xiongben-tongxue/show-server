package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * Created by Haliaeetus leucocephalus on 15/7/14.
 */

@Component("userMapper")
@DAO(catalog = "user")
public interface UserMapper {
	@DataSource("dbUserReadShard0")
	@SelectProvider(type = Provider.class, method = "findAllUsers")
	@Results({ @Result(column = "popular_no", property = "popularNo")})
	public List<User> findAllUsers0(@Param("tbid") Integer tbid);
	
	@DataSource("dbUserReadShard1")
	@SelectProvider(type = Provider.class, method = "findAllUsers")
	@Results({ @Result(column = "popular_no", property = "popularNo")})
	public List<User> findAllUsers1(@Param("tbid") Integer tbid);
	
	
	@DataSource("manageRead")
	@Results({ @Result(column = "fan_level", property = "fanLevel"),
		@Result(column = "master_level", property = "masterLevel"),
		@Result(column = "last_logintime", property = "lastLogintime"),
		@Result(column = "last_livetime", property = "lastLivetime"),
		@Result(column = "sign_status", property = "signStatus"),
		@Result(column = "popular_no", property = "popularNo"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "profile_img", property = "profileImg"),
		@Result(column = "ry_token", property = "ryToken"),
		@Result(column = "device_uuid", property = "deviceUuid"),
		@Result(column = "is_new", property = "isNew"),
		@Result(column = "is_admin", property = "isAdmin"),
		@Result(column = "last_login_type", property = "lastLoginType"),
		@Result(column = "notify_config", property = "notifyConfig"),
		@Result(column = "phone_number", property = "phoneNumber"),
		@Result(column = "family_id", property = "familyId")})
	@Select("select id,fan_level,islive,nickname,master_level,last_logintime,last_livetime,is_admin,last_login_type,role,sign_status,popular_no,create_time,isrobot,latitude,longitude,gender,profile_img,city,active,age,constellation,description,ry_token,device_uuid,is_new,notify_config,phone_number,family_id from t_user where id = #{id}")
	public User findById( @Param("id") long id);

    @DataSource("manageRead")
    @SelectProvider(type = Provider.class, method = "findByIds")
    @Options(useCache=false, flushCache=true)
    public List<User> findByIds(@ShardBy(value = "id", name = "ids") @Param("ids") List<Long> ids);
    
    @DataSource("manageWrite")
    @Insert("insert into t_user(id,fan_level,islive,nickname,master_level,last_logintime,last_livetime,role,sign_status,popular_no,create_time,isrobot,latitude,longitude,gender,profile_img,city,active,age,constellation,description,ry_token,device_uuid,is_new,notify_config,phone_number,is_admin,last_login_type) values(#{id},#{fanLevel},#{islive},#{nickname},#{masterLevel},#{lastLogintime},#{lastLivetime},#{role},#{signStatus},#{popularNo},#{createTime},#{isrobot},#{latitude},#{longitude},#{gender},#{profileImg},#{city},#{active},#{age},#{constellation},#{description},#{ryToken},#{deviceUuid},#{isNew},#{notifyConfig},#{phoneNumber},#{isAdmin},#{lastLoginType})")
	public void insertUser( User user);

    @Select("select count(1) from user where id = #{id}")
    long count(@Param("id") String id);
    
    @DataSource("manageWrite")
    @Insert("delete from t_user where id=#{id}")
	public void deleteUser( @Param("id") long id);

    
    @DataSource("manageWrite")
    @UpdateProvider(type = Provider.class, method = "updateById")
    void updateUserById( @Param("id") long id, @Param("updateContent") Map<String, String> updateContent);

    public static class Provider {
    	
    	public String findAllUsers(Map<String, Object> params) {
    			Integer tbid = (Integer)params.get("tbid");
    			return "select id, nickname, popular_no from t_user_"+tbid;
        }
    	 
        public String findByIds(Map<String, Object> params) {
            List<Long> ids = (List<Long>) params.get("ids");

            return "select id, name from user where id in (" + Joiner.on(",").join(ids) + ") ";
        }

        public String updateById(Map<String, Object> params) {
            Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
            int length = updateContent.size();
            String sql = "update t_user set ";
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
