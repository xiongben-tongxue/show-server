/**
 * 
 */
package one.show.manage.dao;

import java.util.List;

import one.show.manage.domain.AdminRoleFunc;
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
 * @author Haliaeetus leucocephalus 2018年1月22日 下午4:48:34
 *
 */
@Component
public interface AdminRoleFuncMapper {
	
	@DataSource("manageRead")
    @Select("select roleid, funcid from t_admin_role_func where roleid=#{roleId}")
	 @Results({ @Result(column = "roleid", property = "roleId"), 
		 		@Result(column = "funcid", property = "funcId")})
	public List<AdminRoleFunc> findFuncListByRoleId(@Param("roleId") Integer roleId);
	
	
	@DataSource("manageWrite")
    @Delete("delete from t_admin_role_func where roleid=#{roleId}")
	public void deleteRoleFunc(@Param("roleId") Integer roleId);
	
	@DataSource("manageWrite")
	@Options(useGeneratedKeys = false)
    @Insert("insert into t_admin_role_func (roleid, funcid) values (#{roleId}, #{funcId})")
	public void save(AdminRoleFunc adminRoleFunc);

}


