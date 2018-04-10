/**
 * 
 */
package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.AdminUser;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2018年1月22日 下午4:48:50
 *
 */
@Component
public interface AdminUserMapper {
	
	@DataSource("manageWrite")
	@Options(useGeneratedKeys = false)
    @Insert("insert into t_admin_user (username, password, name, status, createtime, usertype ,fid) values (#{userName}, #{password}, #{name}, #{status}, #{createTime}, #{userType}, #{fid})")
	public void save(AdminUser adminUser);
	
		
	@DataSource("manageWrite")
    @Delete("delete from t_admin_user where username=#{userName}")
	public void delete(@Param("userName") String userName);
	
	
	@DataSource("manageWrite")
    @Update("update t_admin_user set name=#{name}, status=#{status} where username=#{userName}")
	public void update(AdminUser adminUser);
	
	@DataSource("manageWrite")
    @Update("update t_admin_user set password=#{password} where username=#{userName}")
	public void updatePwd(@Param("userName") String userName, @Param("password") String password);
	
	@DataSource("manageRead")
    @Select("select username, password, name, status, createtime,usertype ,fid from t_admin_user where username=#{userName}")
	 @Results({ @Result(column = "username", property = "userName"), 
		 		@Result(column = "createtime", property = "createTime"),
		 		@Result(column = "usertype", property = "userType")})
	public AdminUser findUserByUserName(@Param("userName") String userName);
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findUserList")
	@Results({ @Result(column = "username", property = "userName"), 
		 		@Result(column = "createtime", property = "createTime"),
		 		@Result(column = "usertype", property = "userType")})
	public List<AdminUser> findUserList(@Param("status") Integer status, @Param("start") Integer start, @Param("count") Integer count);
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findUserListCount")
	public Integer findUserListCount(@Param("status") Integer status);
	
	
	public static class Provider {
       
        public String findUserList(Map<String, Object> params) {
        	Integer status = (Integer)params.get("status");
            if (status == null){
            	return "select username, password, name, status, createtime,usertype ,fid from t_admin_user order by userType, createtime desc limit #{start},#{count}";
            }else{
            	return "select username, password, name, status, createtime,usertype ,fid from t_admin_user where status=#{status} order by userType, createtime desc limit #{start},#{count}";
            }
            
        }
        
        public String findUserListCount(Map<String, Object> params) {
        	Integer status = (Integer)params.get("status");
            if (status == null){
            	return "select count(*) from t_admin_user";
            }else{
            	return "select count(*) from t_admin_user where status=#{status}";
            }
            
        }
        
        
    }


}


