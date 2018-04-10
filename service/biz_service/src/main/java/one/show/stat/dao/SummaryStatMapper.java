/**
 * 
 */
package one.show.stat.dao;

import java.util.List;

import one.show.stat.domain.SummaryStat;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2018年3月16日 下午3:38:09
 *
 */
public interface SummaryStatMapper {
	
	 @DataSource("manageWrite")
	 @Options(useGeneratedKeys = true, keyProperty = "id")
	 @Insert("insert into t_summary_stat(pv, play, videos, share, login, register, gift, recharge, live_max, time) values(#{pv}, #{play}, #{videos}, #{share}, #{login}, #{register}, #{gift}, #{recharge},#{liveMax}, #{time})")
	 public void save(SummaryStat summaryStat);
	 
	 
	 @DataSource("manageWrite")
	 @Update("update t_summary_stat set pv=pv+#{pv}, play=play+#{play}, videos=videos+#{videos}, share=share+#{share}, login=login+#{login}, register=register+#{register}, gift=gift+#{gift}, recharge=recharge+#{recharge}  where time=#{time}")
	 public void update(SummaryStat summaryStat);
	 
	 @DataSource("manageWrite")
	 @Update("update t_summary_stat set live_max=#{num}  where time=#{time}")
	 public void updateLiveMax(@Param("num") Integer num, @Param("time") Integer time);
	 
	 @DataSource("manageRead")
	 @Select("select id, pv, play, videos, share, login, register, gift, recharge, live_max, time from  t_summary_stat where time=#{time}")
	 @Results(value={@Result(column="live_max",property="liveMax")})
	 public SummaryStat find(@Param("time") Integer time);
	 
	 @DataSource("manageRead")
	 @Select("select id, pv, play, videos, share, login, register, gift, recharge,live_max, time from  t_summary_stat where time>=#{time} order by time asc")
	 @Results(value={@Result(column="live_max",property="liveMax")})
	 public List<SummaryStat> findSummaryList(@Param("time") Integer time);

}


