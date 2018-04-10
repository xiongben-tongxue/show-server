
package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.dao.GiftMapper.Provider;
import one.show.manage.domain.Gift;
import one.show.manage.domain.Notice;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus  2016年6月2日 下午8:56:08
 *
 * 
 */
@Component
public interface NoticeMapper {
	
	@DataSource("manageRead")
	@Results({ @Result(column = "update_time", property = "updateTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime")})
	@Select("select id,content,update_time,create_time,`interval`,start_time, end_time from t_notice where id = #{id}")
	public Notice findById(@Param("id") Integer id);


	/**
	 * 添加
	 * @param Notice
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_notice(content,update_time,create_time,start_time, end_time, `interval`) values(#{content},#{updateTime},#{createTime},#{startTime},#{endTime},#{interval})")
	public void insert(Notice notice);
	
	
	@DataSource("manageWrite")
	@Insert("delete from t_notice where id=#{id}")
	public void delete(@Param("id") Integer id);
	
	
	/**
	 * 修改
	 * @param updateContent
	 * @param id
	 */
	@DataSource("manageWrite")
    @UpdateProvider(type = Provider.class, method = "update")
	public void update(@Param("updateContent") Map<String, String> updateContent, @Param("id") Integer id);
	
	@DataSource("manageRead")
	@Results({ @Result(column = "update_time", property = "updateTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime")})
	@Select("select id,content,update_time,create_time,`interval`,start_time, end_time from t_notice where start_time <= UNIX_TIMESTAMP(now()) and end_time >= UNIX_TIMESTAMP(now()) order by create_time desc")
	public List<Notice> findEffecNoticeList();
	
	
	@DataSource("manageRead")
	@Results({ @Result(column = "update_time", property = "updateTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime")})
	@Select("select id,content,update_time,create_time,`interval`,start_time, end_time from t_notice order by create_time desc limit #{start}, #{count}")
	public List<Notice> findNoticeList(@Param("start") Integer start, @Param("count") Integer count);
	
	
	@DataSource("manageRead")
	@Select("select count(*) from t_notice")
	public Integer findNoticeListCount();
	
	public static class Provider {
		
        public String update(Map<String, Object> params) {
        	Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
        	
        	int length = updateContent.size();
            String sql = "update t_notice set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += "`" + entry.getKey() + "`" + "=" + "#{updateContent."+entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }            
            return sql + " where id = #{id}";
            
        }
        
        
    }


}


