package one.show.user.dao;

import one.show.user.domain.PopularUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component
@DAO
public interface PopularNoUserMapper {

	@DataSource("manageRead")
	@Results({ @Result(column = "popular_no", property = "popularNo") })
	@Select("select popular_no,uid from t_popular_no where popular_no=#{popularNo}")
	public PopularUser findPopularUserByPopularNo(@Param("popularNo")long popularNo);
	
	@DataSource("manageWrite")
	@Insert("insert into t_popular_no (popular_no,uid,create_time) values (#{popularNo},#{uid},#{createTime})")
	public void savePopularUser(PopularUser popularUser);
	
	@DataSource("manageWrite")
	@Insert("delete from  t_popular_no where popular_no = #{popularNo}")
	public void deletePopularUser(@Param("popularNo")long popularNo);
}
