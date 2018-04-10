package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.domain.ThirdBind;

import org.apache.ibatis.annotations.Delete;
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

@Component("thirdBindMapper")
@DAO(catalog = "user")
public interface ThirdBindMapper {

	@DataSource("manageRead")
    @Select("select bid,uid,tid,public_status,type from t_third_bind where uid = #{uid}")
	public List<ThirdBind> findThirdBindByUid( @Param("uid") long uid);
	
	@DataSource("manageWrite")
	@Insert("insert into t_third_bind (uid,tid,public_status,type, create_time) values (#{uid},#{tid},#{publicStatus},#{type},#{createTime})")
	public long save(ThirdBind thirdBind);
	 
	@DataSource("manageWrite")
	@Delete("delete from  t_third_bind where uid=#{uid} and type =#{type}")
	public void deleteThirdBind( @Param("uid") long uid,@Param("type") String type);
	
	@DataSource("manageRead")
	@Results(value={@Result(column="public_status",property="publicStatus")})
	@Select("select bid,uid,tid,public_status,type from t_third_bind where uid = #{uid} and type =#{type}")
	public List<ThirdBind> findThirdBindByUidAndType( @Param("uid") long uid,@Param("type") String type);
	
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	public void updateThirdBindByUidAndType( @Param("uid")long uid,@Param("type")String type,@Param("paramMap") Map<String,String> paramMap);
	public static class Provider {
	       

        public String updateById(Map<String, Object> params) {
            Map<String,String> updateContent = (Map<String, String>) params.get("paramMap");
            int length = updateContent.size();
            String sql = "update t_third_bind set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += entry.getKey() + " = " + "#{paramMap." + entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }
            return sql + " where uid =#{uid} and type=#{type}";
        }
      
    }
}
