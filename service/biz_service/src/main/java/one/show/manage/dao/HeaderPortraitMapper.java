package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.HeaderPortrait;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

@Component
public interface HeaderPortraitMapper {
	@DataSource("manageRead") 
	@Select("select id,img,nick_name,create_at,uid from t_head_portrait order by create_at desc limit #{pageSize},#{pageCount}")
	@Results({ @Result(column = "nick_name", property = "nickName"), @Result(column = "create_at", property = "createAt") })
	public List<HeaderPortrait> findHeaderPortraitList(@Param("pageSize") int pageSize,@Param("pageCount")int pageCount);
	
	@DataSource("manageWrite")
	@Delete("delete from t_head_portrait where id=#{id}")
	public void deleteHeaderPortrait(@Param("id")long id);
	
	@DataSource("manageWrite")
	@DeleteProvider(method="deleteHeaderPortraitList",type=Provider.class)
	public void deleteHeaderPortraitList(@Param("idList") List<Long> idList);
	
	@DataSource("manageWrite")
	@Insert("insert into t_head_portrait (img,nick_name,create_at,uid) values (#{img},#{nickName},#{createAt},#{uid})")
	public void saveHeaderPortraitList(HeaderPortrait headerPortrait );
	@DataSource("manageRead") 
	@SelectProvider(type=Provider.class,method="findHeaderPortraitCount")
	public Integer findHeaderPortraitCount();
	@DataSource("manageRead")
	@Select("select id,img,nick_name,create_at,uid from t_head_portrait where uid=#{uid}")
	@Results({ @Result(column = "nick_name", property = "nickName"), @Result(column = "create_at", property = "createAt")})
	public HeaderPortrait findHeaderPortrait(@Param("uid")String uid);
	@DataSource("manageWrite")
	@Delete("delete from t_head_portrait wheren uid= #{uid}")
	public void deleteHeaderPortraitByUid(@Param("uid") String uid);
	@DataSource("manageWrite")
	@UpdateProvider(type=Provider.class,method="updateById")
	public void updateHeaderPortraitByUid(@Param("uid")String uid,@Param("updateContent")Map<String,String> params);
	public static class Provider {
        public String deleteHeaderPortraitList(Map<String, Object> params){
        	List<Long> ids = (List<Long>) params.get("idList");
        	String sql = "delete from t_head_portrait";
        	if(ids!=null && ids.size()!=0){
        		return sql;
        	}else{
        		sql = sql+"where id in (";
        		int size = ids.size();
        		for(int i=0;i<size;i++){
        			if(i==size-1){
        				sql=sql+ids.get(i);
        			}else{
        				sql = sql+ids.get(i)+",";
        			}
        		}
        		sql = sql+" )";
        	}
        	return sql;
        }
        public String findHeaderPortraitCount(){
        	return "select count(0) from t_head_portrait";
        }
        public String updateById(Map<String, Object> params) {
            // fixme 对字段值中存在 单引号的处理，对 表情符号等特殊字符的处理
            Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
            int length = updateContent.size();
            String sql = "update t_head_portrait set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }
            sql = sql + " where uid = #{uid}";
            return sql;
        }
    }
}
