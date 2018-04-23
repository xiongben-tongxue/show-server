/**
 * 
 */
package one.show.user.dao;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

import one.show.user.domain.ThirdData;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangwei 2015年8月4日 下午8:29:04
 *
 */

@Component("thirdDataMapper")
@DAO(catalog = "user")
public interface ThirdDataMapper {
	
	@DataSource("manageRead")
    @Select("select uid,type,token,tid from t_third_data where tid = #{tid} and type=#{type}")
	public ThirdData findByTidAndType( @Param("tid") String tid, @Param("type") String type);
	
	@DataSource("manageRead")
    @Select("select uid,type,token,tid from t_third_data where uid = #{uid}")
	public List<ThirdData> findThirdDataListByUid(@Param("uid")long uid);

	@DataSource("manageRead")
    @Select("select uid,type,token,tid from t_third_data where uid = #{uid} and type=#{type}")
	public ThirdData findThirdDataByUidAndType(@Param("uid") long uid, @Param("type") String type);
	
	@DataSource("manageWrite")
	@Insert("insert into t_third_data (uid,type,token,tid,create_time) values(#{uid},#{type},#{token},#{tid},#{createTime})")
	public int save( ThirdData thirdData);
	 
	@DataSource("manageWrite")
	@Delete("delete from t_third_data where tid=#{tid} and type=#{type}")
	public void deleteThirdData(@Param("tid") String tid, @Param("type") String type);
	
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	public void updateThirdDataById( @Param("tid") String tid,@Param("type") String type,@Param("updateContent") Map<String,String> updateContent);
	
	 public static class Provider {
       

        public String updateById(Map<String, Object> params) {
            Map<String,String> updateContent = (Map<String, String>) params.get("updateContent");
            int length = updateContent.size();
            String sql = "update t_third_data set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += "`" + entry.getKey() + "`" + "=" + "#{updateContent." + entry.getKey()+"}";;
                if (length > 0)
                    sql += ",";
            }
            return sql + " where tid = #{tid} and type=#{type}";
        }
      
    }

}


