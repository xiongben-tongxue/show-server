/**
 * 
 */
package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.AdminFunc;

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
 * @author Haliaeetus leucocephalus 2018年1月22日 下午4:46:48
 *
 */
@Component
public interface AdminFuncMapper {
	
	
	@DataSource("manageWrite")
	@Options(useGeneratedKeys = false)
    @Insert("insert into t_admin_func (funcid, funcname, url, functype, fatherfuncid, status) values (#{funcId}, #{funcName}, #{url}, #{funcType}, #{fatherFuncId}, #{status})")
	public void save(AdminFunc adminFunc);
	
	
	@DataSource("manageWrite")
    @Update("update  t_admin_func set funcname=#{funcName}, url=#{url}, functype=#{funcType}, fatherfuncid=#{fatherFuncId}, status=#{status} where funcid=#{funcId}")
	public void update(AdminFunc adminFunc);
	
	@DataSource("manageWrite")
	@Update("update  t_admin_func set status=#{status} where funcid!=#{funcId} and substr(funcid,1,length(#{funcId}))=#{funcId}")
	public void updateChildStatusByFuncId(@Param("funcId") String funcId, @Param("status") Integer status);
	
	@DataSource("manageWrite")
    @Delete("delete from t_admin_func where funcid=#{funcId}")
	public void delete(@Param("funcId") String funcId);
	
	@DataSource("manageRead")
    @Select("select funcid, funcname, url, functype, fatherfuncid, status from t_admin_func where funcid=#{funcId}")
	 @Results({ @Result(column = "funcid", property = "funcId"), 
		 		@Result(column = "funcname", property = "funcName"), 
		 		@Result(column = "functype", property = "funcType"),
		 		@Result(column = "fatherfuncid", property = "fatherFuncId")})
	public AdminFunc findFuncById(@Param("funcId") String funcId);
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findChildList")
	@Results({ @Result(column = "funcid", property = "funcId"), 
		 		@Result(column = "funcname", property = "funcName"), 
		 		@Result(column = "functype", property = "funcType"),
		 		@Result(column = "fatherfuncid", property = "fatherFuncId")})
	public List<AdminFunc> findChildList(@Param("funcId") String funcId, @Param("recursion") Boolean recursion);
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findFuncList")
	@Results({ @Result(column = "funcid", property = "funcId"), 
		 		@Result(column = "funcname", property = "funcName"), 
		 		@Result(column = "functype", property = "funcType"),
		 		@Result(column = "fatherfuncid", property = "fatherFuncId")})
	public List<AdminFunc> findFuncList(@Param("status") Integer status);


	public static class Provider {
        public String findChildList(Map<String, Object> params) {
            Boolean recursion = (Boolean) params.get("recursion");
            if (recursion){
            	return "select funcid, funcname, url, functype, fatherfuncid, status  from t_admin_func where funcid!=#{funcId} and substr(funcid,1,length(#{funcId}))=#{funcId} order by funcid asc";
            }else{
            	return "select funcid, funcname, url, functype, fatherfuncid, status  from t_admin_func where fatherfuncid=#{funcId} order by funcid asc";
            }
            
        }
        
        public String findFuncList(Map<String, Object> params) {
        	Integer status = (Integer)params.get("status");
            if (status == null){
            	return "select funcid, funcname, url, functype, fatherfuncid, status from t_admin_func order by funcid asc";
            }else{
            	return "select funcid, funcname, url, functype, fatherfuncid, status from t_admin_func where status=#{status} order by funcid asc";
            }
            
        }
        
        
    }

}


