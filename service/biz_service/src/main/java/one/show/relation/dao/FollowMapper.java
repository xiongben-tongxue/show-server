/**
 * 
 */
package one.show.relation.dao;

import java.util.List;
import java.util.Map;

import one.show.relation.domain.Follow;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
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
public interface FollowMapper {
	
	 @DataSource("dbRelationReadShard0")
	 @Select("select uid, fid, create_at from t_follow where uid = #{uid} order by create_at desc limit #{start},#{count}")
	 @Results({ @Result(column = "create_at", property = "time") })
	 public List<Follow> findListByUid(@ShardBy("uid") @Param("uid") Long uid, @Param("start") Integer start, @Param("count") Integer count);
	 
	 
	 @DataSource("dbRelationWriteShard0")
	 @Options(useGeneratedKeys = false)
	 @Insert("insert into t_follow(uid, fid, create_at) values(#{uid},#{fid},#{time})")
	 public void save(@ShardBy("uid") Follow follow);
	 
	 @DataSource("dbRelationWriteShard0")
	 @Insert("delete from t_follow where uid=#{uid} and fid=#{fid}")
	 public void delete(@ShardBy("uid") @Param("uid") Long uid, @Param("fid") Long fid);
	 
	 @DataSource("dbRelationReadShard0")
	 @Select("select count(0) from t_follow where uid=#{uid}")
	 public Integer findFollowCountByUid(@ShardBy("uid") @Param("uid") Long uid);

	@DataSource("dbRelationReadShard0")
	@SelectProvider(type = Provider.class, method = "findFollowList")
	List<Long> isFollowed(@ShardBy("uid")  @Param("uid") Long currentUser,@Param("fids") List<Long> uids);
	
	@DataSource("dbRelationReadShard0")
	@SelectProvider(type = Provider.class, method = "findFansList")
	List<Long> isFans(@ShardBy("uid")  @Param("uid") Long currentUser,@Param("fids") List<Long> uids);

	 public static class Provider {
	        public String findFollowList(Map<String, Object> params) {
	            List<Long> ids = (List<Long>) params.get("fids");
	            String sql = "";
	            for(Long id : ids){
	            	sql += "'"+id+"',";
	            }
	            if (sql.lastIndexOf(",") > 0){
	            	sql = sql.substring(0, sql.lastIndexOf(","));
	            }
	            
	            if (sql.length() == 0){
	            	return "select fid from t_follow where 1=2";
	            }else{
	            	return "select fid from t_follow where uid = #{uid} and fid in (" + sql + ") ";
	            }
	            
	        }
	        
	        
	        public String findFansList(Map<String, Object> params) {
	            List<Long> ids = (List<Long>) params.get("fids");
	            String sql = "";
	            for(Long id : ids){
	            	sql += "'"+id+"',";
	            }
	            if (sql.lastIndexOf(",") > 0){
	            	sql = sql.substring(0, sql.lastIndexOf(","));
	            }
	            
	            if (sql.length() == 0){
	            	return "select fid from t_fans where 1=2";
	            }else{
	            	return "select fid from t_fans where uid = #{uid} and fid in (" + sql + ") ";
	            }
	            
	        }
	 }
}


