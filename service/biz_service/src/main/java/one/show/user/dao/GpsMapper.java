package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.domain.Gps;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

@Component
public interface GpsMapper {
	@DataSource("manageRead")
	@Select("select uid,latitude,longitude,update_time,city,gender from t_gps where uid=#{uid}")
	public Gps findGpsByUid(@Param("uid") long uid);
	@DataSource("manageWrite")
	@Insert("insert into t_gps (uid,latitude,longitude,update_time,city,gender) values (#{uid},#{latitude},#{longitude},#{updateTime},#{city},#{gender})")
	public void saveGps(Gps gps);
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateGps")
	public void updateGps(@Param("uid")long uid,@Param("statement")Map<String,String>statement);
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findNearGpsList")
	public List<Gps> findNearGpsList(@Param("statement") Map<String,String> statement,@Param("start")int start,@Param("count")int count);
	public static class Provider {

        public String updateGps(Map<String, Object> params) {
        	Map<String, String> updateContent =(Map<String, String>)params.get("statement");
            int length = updateContent.size();
            String sql = "update t_gps set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += entry.getKey() + " = " + "#{statement." + entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }
            return sql + " where uid = #{uid}";
        }
        public String findNearGpsList(Map<String,Object> params){
        	Map<String, String> selectContent =(Map<String, String>)params.get("statement");
        	String sql = "select uid,latitude,longitude,update_time,city,gender from t_gps where longitude > #{statement.down_lon} and longitude < #{statement.top_lon} and latitude > #{statement.left_lat} and latitude < #{statement.right_lat} order by ACOS(SIN((#{statement.lat}*3.1415)/180)*SIN((latitude*3.1415)/180)+COS((#{statement.lat}*3.1415)/180)*COS((latitude*3.1415)/180)*COS((#{statement.lon}*3.1415)/180-(longitude*3.1415)/180))*6380 asc limit #{start},#{count}";
        	//String sql="select uid,latitude,longitude,update_time,city,gender from t_gps where longitude > 115.30022676607962 and longitude < 117.64595723392037 and latitude > 39.09713802779225 and latitude < 40.89418597220776 order by ACOS(SIN((39.995662*3.1415)/180)*SIN((latitude*3.1415)/180)+COS((39.995662*3.1415)/180)*COS((latitude*3.1415)/180)*COS((116.473092*3.1415)/180-(longitude*3.1415)/180))*6380 asc limit 0,20";
        	return sql;
        }
	}
}
