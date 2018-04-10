/**
 * 
 */
package one.show.stat.dao;

import java.util.List;
import java.util.Map;

import one.show.stat.domain.MonitorStat;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午5:37:14
 *
 */

@Component
public interface MonitorStatMapper {
	
	 @DataSource("manageWrite")
	 @Options(useGeneratedKeys = true, keyProperty = "id")
	 @Insert("insert into t_monitor_stat(name, total_time, succeed_num, fail_num, total_num, type, time) values(#{name}, #{totalTime}, #{succeedNum}, #{failNum}, #{totalNum}, #{type}, #{time})")
	 public void save(MonitorStat monitorStat);
	 
	 
	 @DataSource("manageRead")
	 @Select("select id, name, total_time, succeed_num, fail_num, total_num, type, time from  t_monitor_stat where name=#{name} and type=#{type} and time=#{time}")
	 @Results(value={@Result(column="total_time",property="totalTime"),
			 @Result(column="succeed_num",property="succeedNum"),
			 @Result(column="fail_num",property="failNum"),
			 @Result(column="total_num",property="totalNum")})
	 public MonitorStat find(@Param("name") String name, @Param("type") Integer type, @Param("time") Integer time);
	 
	 
	 @DataSource("manageRead")
	 @Select("select  name, sum(total_time) as totalTime, sum(succeed_num) as succeedNum, sum(fail_num) as failNum, sum(total_num) as totalNum, sum(total_time)/sum(total_num) as avgTime from  t_monitor_stat where time>=#{beginTime} and time<=#{endTime} and type=#{type} group by name order by avgtime desc limit #{start},#{count}")
	 public List<MonitorStat> findList(@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime, @Param("type") Integer type, @Param("start") Integer start, @Param("count") Integer count);
	 
	 @DataSource("manageRead")
	 @Select("select count(*) from (select name from  t_monitor_stat where time>=#{beginTime} and time<=#{endTime} and type=#{type} group by name) a")
	 public Integer findListCount(@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime, @Param("type") Integer type, @Param("start") Integer start, @Param("count") Integer count);

	 
	 @DataSource("manageWrite")
	 @UpdateProvider(type = Provider.class, method = "updateById")
	 public void updateById(@Param("id") Long id, @Param("statement") String statement);
	

	 public static class Provider {
	        public String updateById(Map<String, Object> params) {
	            String statement = (String) params.get("statement");
	            Integer id = Integer.parseInt(params.get("id").toString());
	            String sql = "update t_monitor_stat set "+statement+" where id="+ id;
	            return sql;
	        }
	       
	 }


}


