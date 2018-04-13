package one.show.user.dao;

import java.util.List;

import one.show.user.domain.Robot;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

/**
 * Created by zhangwei on 15/7/14.
 */

@Component("robotMapper")
@DAO(catalog = "user")
public interface RobotMapper {

	
	@DataSource("manageWrite")
    @Insert("insert into t_robot (uid,popular_no,nick_name,portrait,description,gender,create_time) values (#{uid},#{popularNo},#{nickName},#{portrait},#{description},#{gender},#{createTime})")
	public int save(Robot robot);
	
	@DataSource("manageRead")
	@Results({ @Result(column = "nick_name", property = "nickName"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "popular_no", property = "popularNo")})
    @Select("select uid,popular_no,nick_name,portrait,description,gender,create_time from t_robot")
	public List<Robot> findList();



}
