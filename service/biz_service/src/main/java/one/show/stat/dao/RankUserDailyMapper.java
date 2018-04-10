/**
 * 
 */
package one.show.stat.dao;

import java.util.List;

import one.show.stat.domain.MonitorStat;
import one.show.stat.domain.RankUserDaily;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午5:37:14
 *
 */

@Component
@DAO(catalog = "stat")
public interface RankUserDailyMapper {
	
	 @DataSource("manageWrite")
	 @Options(useGeneratedKeys = true, keyProperty = "id")
	 @Insert("insert into t_rank_user_daily(uid, number, type, date) values(#{uid}, #{number}, #{type}, #{date})")
	 public void save(RankUserDaily rankUserDaily);
	 
	 
	 @DataSource("manageWrite")
	 @Update("update t_rank_user_daily set number=number+#{number} where id=#{id}")
	 public void update(@Param("id") Long id, @Param("number") Double number);
	 
	 @DataSource("manageRead")
	 @Select("select id, uid, number, type, date from  t_rank_user_daily where date=#{date} and uid=#{uid} and type=#{type}")
	 public RankUserDaily find(@Param("uid") Long uid, @Param("type") Integer type, @Param("date") Integer date);
	 
	 
	 @DataSource("manageRead")
	 @Select("select  uid, sum(number) as number from  t_rank_user_daily where date>=#{beginTime} and date<=#{endTime} and type=#{type} group by uid order by number desc limit #{start},#{count}")
	 public List<RankUserDaily> findList(@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime, @Param("type") Integer type, @Param("start") Integer start, @Param("count") Integer count);
	 
	 @DataSource("manageRead")
	 @Select("select count(*) from (select id from  t_rank_user_daily where date>=#{beginTime} and date<=#{endTime} and type=#{type} group by uid) a")
	 public Integer findListCount(@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime, @Param("type") Integer type, @Param("start") Integer start, @Param("count") Integer count);

	 

	 

}


