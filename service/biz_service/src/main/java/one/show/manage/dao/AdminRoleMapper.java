/**
 * 
 */
package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.dao.AdminUserMapper.Provider;
import one.show.manage.domain.AdminRole;

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
 * @author Haliaeetus leucocephalus 2018年1月22日 下午4:47:53
 *
 */
@Component
public interface AdminRoleMapper {
	
	@DataSource("manageWrite")
    @Delete("delete from t_admin_role where roleid=#{roleId}")
	public void delete(@Param("roleId") Integer roleId);
	
	@DataSource("manageWrite")
    @Update("update t_admin_role set rolename=#{roleName}, roledesc=#{roleDesc}, status=#{status} where roleid=#{roleId}")
	public void update(AdminRole adminRole);
	
	@DataSource("manageWrite")
	@Options(useGeneratedKeys = true, keyProperty = "roleId")
    @Insert("insert into t_admin_role(rolename, roledesc, status, createtime) values (#{roleName}, #{roleDesc}, #{status}, #{createTime})")
	public void save(AdminRole adminRole);

	@DataSource("manageRead")
    @Select("select roleid, rolename, roledesc, status, createtime from t_admin_role where roleid=#{roleId}")
	@Results({ @Result(column = "roleid", property = "roleId"), 
				@Result(column = "rolename", property = "roleName"), 
		 		@Result(column = "roledesc", property = "roleDesc"), 
		 		@Result(column = "createtime", property = "createTime")})
	public AdminRole findRoleById(@Param("roleId") Integer roleId);
	
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findRoleList")
	@Results({ @Result(column = "roleid", property = "roleId"), 
			@Result(column = "rolename", property = "roleName"), 
			@Result(column = "roledesc", property = "roleDesc"), 
			@Result(column = "createtime", property = "createTime")})
	public List<AdminRole> findRoleList(@Param("status") Integer status,  @Param("start") Integer start, @Param("count") Integer count);
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findRoleListCount")
	public Integer findRoleListCount(@Param("status") Integer status);
	
	public static class Provider {
       
        public String findRoleList(Map<String, Object> params) {
        	Integer status =(Integer)params.get("status");
            if (status == null){
            	return "select roleid, rolename, roledesc, status, createtime from t_admin_role order by createtime desc limit #{start},#{count}";
            }else{
            	return "select roleid, rolename, roledesc, status, createtime from t_admin_role where status=#{status} order by createtime desc limit #{start},#{count}";
            }
            
        }
        
        public String findRoleListCount(Map<String, Object> params) {
        	Integer status = (Integer)params.get("status");
            if (status == null){
            	return "select count(*) from t_admin_role";
            }else{
            	return "select count(*) from t_admin_role where status=#{status}";
            }
            
        }
        
        
    }
}


