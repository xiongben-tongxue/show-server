package one.show.manage.dao;

import java.util.List;

import one.show.manage.domain.UserCDN;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

@Component
public interface UserCDNMapper {
	@DataSource("manageRead")
	@Results({ @Result(column = "cdn_type", property = "cdnType"), @Result(column = "create_time", property = "createTime") })
	@Select("select * from t_user_cdn order by create_time desc limit #{start}, #{count}")
	List<UserCDN> findUserCDNList(@Param("start") Integer start, @Param("count") Integer count);
	
	@DataSource("manageRead")
	@Select("select count(*) from t_user_cdn")
	Integer findUserCDNListCount();
	
	@DataSource("manageRead")
	@Results({ @Result(column = "cdn_type", property = "cdnType") })
	@Select("select * from t_user_cdn where uid=#{uid}")
	UserCDN findUserCDNByUid(@Param("uid") Long uid);
	
	
	@DataSource("manageWrite")
	@Update("update t_user_cdn set cdn_type=#{cdnType}, ngb=#{ngb}, rtmp=#{rtmp} where uid=#{uid}")
	void updateUserCDN(UserCDN userCDN);
	
	@DataSource("manageWrite")
	@Insert("insert into t_user_cdn (uid, cdn_type,ngb, rtmp, create_time) values (#{uid}, #{cdnType},#{ngb}, #{rtmp}, #{createTime})")
	void saveUserCDN(UserCDN userCDN);
	
	@DataSource("manageWrite")
	@Insert("delete from t_user_cdn where uid=#{uid}")
	void deleteUserCDN(@Param("uid") Long uid);
}
