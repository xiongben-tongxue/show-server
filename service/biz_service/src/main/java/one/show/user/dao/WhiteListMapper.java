package one.show.user.dao;

import java.util.List;

import one.show.user.domain.WhiteList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

/**
 * Created by Haliaeetus leucocephalus on 15/7/14.
 */

@Component("whiteListMapper")
@DAO(catalog = "user")
public interface WhiteListMapper {

	
	@DataSource("manageWrite")
    @Insert("insert into t_whitelist (uid,time) values (#{uid},#{time})")
	public void save(@Param("uid") long uid,  @Param("time") int time);

	@DataSource("manageWrite")
	@Select("delete from t_whitelist where uid=#{uid}")
	void remove(@Param("uid") long uid);

	@DataSource("manageRead")
	@Select("select uid,time from t_whitelist  limit #{cursor},#{count}")
	List<WhiteList> findWhiteList(@Param("cursor") int cursor,@Param("count") int count);

	@DataSource("manageRead")
	@Select("select uid,time from t_whitelist where uid=#{uid} ")
	WhiteList findWhiteListByUid(@Param("uid") long uid);
	

}
