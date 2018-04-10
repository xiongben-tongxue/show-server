
package one.show.stat.dao;

import java.util.Map;

import one.show.stat.dao.MonitorStatMapper.Provider;
import one.show.stat.domain.RobotStatDaily;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * @author Haliaeetus leucocephalus  2016年7月16日 下午6:04:20
 *
 * 
 */
@Component
@DAO(catalog = "stat")
public interface RobotStatDailyMapper {

	@DataSource("manageRead")
	@Select("select spend,date from t_robot_stat_daily where date = #{date}")
	public RobotStatDaily findByDate(@Param("date") int date);

	/**
	 * 添加
	 * @param RobotStatDaily
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_robot_stat_daily(spend,date) values(#{spend},#{date})")
	public void save(RobotStatDaily robotStatDaily);
	/**
	 * 更新
	 * @param RobotStatDaily
	 */
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateByDate")
	public void updateByDate(@Param("date") int date, @Param("updateContent") Map<String, String> updateContent);
	 
	public static class Provider {
        public String updateByDate(Map<String, Object> params) {
        	 Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
             int length = updateContent.size();
             String sql = "update t_robot_stat_daily set ";
             for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                 length--;
                 sql += entry.getKey() + " = " + entry.getKey() + "+#{updateContent." + entry.getKey()+"}";
                 if (length > 0)
                     sql += ",";
             }
             sql = sql + " where date = #{date}";
             return sql;
        }
	}
}


