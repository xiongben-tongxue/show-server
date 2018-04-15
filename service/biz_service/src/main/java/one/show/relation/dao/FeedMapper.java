/**
 * 
 */
package one.show.relation.dao;

import java.util.List;
import java.util.Map;

import one.show.relation.domain.Feed;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * @author zhangwei 2015年8月5日 下午8:52:53
 *
 */

@Component
@DAO(catalog = "relation")
public interface FeedMapper {
	
	 @DataSource("dbRelationReadShard0")
	 @Results({ @Result(column = "create_at", property = "time"),@Result(column = "vod_status", property = "vodStatus") })
	 @SelectProvider(method="findListByUid",type=Provider.class)
	 public List<Feed> findListByUid(@ShardBy("uid") @Param("uid") Long uid, @Param("condition")Map<String, String> condition, @Param("start") Integer start, @Param("count") Integer count);
	
	 
	 @DataSource("dbRelationWriteShard0")
	 @Insert("insert into t_feed(uid, fid, type, point, create_at,vod_status) values(#{uid},#{fid},#{type},#{point},#{time},#{vodStatus})")
	 public void save(@ShardBy("uid") Feed feed);
	 
	 @DataSource("dbRelationWriteShard0")
	 @Insert("delete from  t_feed where uid = #{uid} and fid = #{fid} and point = #{point}")
	 public void delete(@ShardBy("uid") @Param("uid") long uid,@Param("fid") long fid,@Param("point") String point);
	 
	 @DataSource("dbRelationWriteShard0")
	 @UpdateProvider(type = Provider.class, method = "updateById")
	 void update(@ShardBy("uid") @Param("uid") long uid,@Param("fid") long fid,@Param("point") String point, @Param("updateContent") Map<String, String> updateContent);

	 public static class Provider {
		 
		public String findListByUid(Map<String, Object> params) {
			String sql = "select id, uid, fid, type, point, create_at,vod_status from t_feed where uid = #{uid} ";
			Map<String, String> paramMap = (Map<String, String>) params.get("condition");

			if(paramMap!=null){
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+ "}";
				}
			}
			sql += " order by create_at desc limit #{start},#{count} "; 
			return sql;
		}
			
			
		 public String updateById(Map<String, Object> params) {
	            Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
	            int length = updateContent.size();
	            String sql = "update t_feed set ";
	            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
	                length--;
	                sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
	                if (length > 0)
	                    sql += ",";
	            }
	            sql = sql + " where uid = #{uid} and fid = #{fid} and point = #{point}";
	            return sql;
	        }
	 }
}