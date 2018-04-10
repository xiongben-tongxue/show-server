/**
 * 
 */
package one.show.stat.dao;

import one.show.stat.domain.UserStat;

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

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午5:37:14
 *
 */

@Component
@DAO(catalog = "stat")
public interface UserStatMapper {
	
	 @DataSource("manageRead")
	 @Select("select uid, fans, follow, login, videos, viewed,receive,spend,receive_exp, spend_exp from  t_user_stat where uid=#{uid}")
	 @Results(value={@Result(column="receive_exp",property="receiveExp"),
			 @Result(column="spend_exp",property="spendExp")})
	 public UserStat findByUid( @Param("uid") Long uid);
	
	
	 @DataSource("manageWrite")
	 @Insert("insert into t_user_stat(uid, fans, follow, login, videos, viewed,receive,spend, receive_exp, spend_exp ) values(#{uid}, #{fans}, #{follow},  #{login}, #{videos}, #{viewed}, #{receive}, #{spend}, #{receiveExp}， #{spendExp})")
	 public void save( UserStat userStat);
	 
	 
	 @DataSource("manageWrite")
	 @Update("update t_user_stat set fans=fans+#{fans},follow=follow+#{follow},login=login+#{login},videos=videos+#{videos},viewed=viewed+#{viewed},receive=receive+#{receive},spend=spend+#{spend},receive_exp=receive_exp+#{receiveExp}, spend_exp=spend_exp+#{spendExp} where uid=#{uid}")
	 public void update( UserStat userStat);
	 
	 @DataSource("manageWrite")
	 @Insert("delete from t_user_stat where uid=#{uid}")
	 public void delete( @Param("uid")  Long uid);
}


