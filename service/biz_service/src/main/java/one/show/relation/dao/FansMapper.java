/**
 * 
 */
package one.show.relation.dao;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

import one.show.relation.domain.Fans;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangwei 2015年8月5日 下午8:52:53
 *
 */

@Component
@DAO(catalog = "relation")
public interface FansMapper {
	
	 @DataSource("dbRelationReadShard0")
	 @Select("select uid, fid, create_at from t_fans where uid = #{uid} order by create_at desc limit #{start},#{count}")
	 @Results({ @Result(column = "create_at", property = "time") })
	 public List<Fans> findListByUid(@ShardBy("uid") @Param("uid") Long uid, @Param("start") Integer start, @Param("count")Integer count);
	 
	 
	 @DataSource("dbRelationWriteShard0")
	 @Insert("insert into t_fans(uid, fid, create_at) values(#{uid},#{fid},#{time})")
	 public void save(@ShardBy("uid") Fans fans);
	 
	 
	 @DataSource("dbRelationWriteShard0")
	 @Insert("delete from t_fans where uid=#{uid} and fid=#{fid}")
	 public void delete(@ShardBy("uid") @Param("uid") Long uid, @Param("fid") Long fid);
	 

	 @DataSource("dbRelationReadShard0")
	 @Select("select count(0) from t_fans where uid=#{uid}")
	 public Integer findFansCountByUid(@ShardBy("uid") @Param("uid") Long uid);
}


