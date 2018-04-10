/**
 * 
 */
package one.show.manage.dao;

import java.util.List;

import one.show.manage.domain.AdminUserRole;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2018年1月22日 下午4:49:06
 *
 */
@Component
public interface AdminUserRoleMapper {

	@DataSource("manageRead")
    @Select("select username, roleid from t_admin_user_role where username=#{userName}")
	@Results({ @Result(column = "username", property = "userName"), 
		 		@Result(column = "roleid", property = "roleId")})
	public List<AdminUserRole> findUserRoleList(@Param("userName") String userName);
	
	@DataSource("manageWrite")
    @Delete("delete from t_admin_user_role where username=#{userName}")
	public void deleteUserRole(@Param("userName") String userName);
	
	@DataSource("manageWrite")
	@Options(useGeneratedKeys = false)
    @Insert("insert into t_admin_user_role (username, roleid) values (#{userName}, #{roleId})")
	public void save(AdminUserRole adminUserRole);
}


