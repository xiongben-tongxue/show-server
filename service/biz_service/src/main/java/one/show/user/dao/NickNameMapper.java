package one.show.user.dao;

import one.show.user.domain.NickNameUser;

import org.apache.ibatis.annotations.Delete;
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
public interface NickNameMapper {
	@DataSource("manageRead")
	@Results({ @Result(column = "nick_name", property = "nickName"),@Result(column = "create_time", property = "createTime") })
	@Select("select nick_name,uid from t_nick_name where nick_name = #{nickName}")
	public NickNameUser findNickNameUserByNickName(@Param("nickName")String nickName);
	
	@DataSource("manageWrite")
	@Insert("insert into t_nick_name (nick_name,uid,create_time) values(#{nickName},#{uid},#{createTime})")
	public void saveNickNameUser(NickNameUser nickNameUser);
	
	@DataSource("manageWrite")
	@Delete("delete from t_nick_name where nick_name=#{nickName}")
	public void deleteNickName(@Param("nickName")String nickName);
}
