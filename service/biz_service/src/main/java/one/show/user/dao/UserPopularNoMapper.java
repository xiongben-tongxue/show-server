package one.show.user.dao;

import java.util.List;

import one.show.user.domain.UserPopular;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

@Component
@DAO
public interface UserPopularNoMapper {

	@DataSource("manageRead")
	@Results({ @Result(column = "popular_no", property = "popularNo"), @Result(column = "create_time", property = "createTime") })
	@Select("select  popular_no,uid, status, create_time from t_user_popularno where uid=#{uid}")
	public List<UserPopular> findListByUid(@Param("uid")long uid);
	
	@DataSource("manageWrite")
	@Insert("insert into t_user_popularno (popular_no,uid,status,  create_time) values (#{popularNo},#{uid}, #{status}, #{createTime})")
	public void save(UserPopular userPopular);
	
	@DataSource("manageWrite")
	@Insert("delete from t_user_popularno where uid=#{uid} and popular_no=#{popularNo}")
	public void delete(@Param("uid")long uid, @Param("popularNo")long popularNo);
	
	
	@DataSource("manageWrite")
	@Update("update t_user_popularno set status=#{status} where uid=#{uid} and popular_no=#{popularNo}")
    void updateStatus(@Param("uid")long uid, @Param("popularNo")long popularNo, @Param("status") int status);

}
