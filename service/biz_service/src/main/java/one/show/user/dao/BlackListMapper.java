package one.show.user.dao;

import java.util.List;

import one.show.user.domain.BlackList;
import one.show.user.domain.RoomAdmin;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * Created by Haliaeetus leucocephalus on 15/7/14.
 */

@Component("blackListMapper")
@DAO(catalog = "user")
public interface BlackListMapper {

	
	@DataSource("manageWrite")
    @Insert("insert into t_blacklist (uid,tid,time) values (#{uid},#{tid},#{time})")
	public int save(@Param("uid") long uid, @Param("tid") long tid, @Param("time") int time);

	@DataSource("manageWrite")
	@Select("delete from t_blacklist where uid=#{uid} and tid=#{tid}")
	void remove(@Param("uid") long uid, @Param("tid") long tid);

	@DataSource("manageRead")
	@Select("select tid from t_blacklist where uid=#{uid} limit #{cursor},#{count}")
	List<Long> findBlackList(@Param("uid") long uid, @Param("cursor") int cursor,@Param("count") int count);

	@DataSource("manageRead")
	@Select("select uid,tid,time from t_blacklist where uid=#{uid} limit #{cursor},#{count}")
	List<BlackList> findBlackListByUid(@Param("uid") long uid, @Param("cursor") int cursor,@Param("count") int count);
	
	@DataSource("manageRead")
	@Select("select count(0) from t_blacklist where uid=#{uid}")
	public Integer findCountBlackListByUid(@Param("uid")long uid);
	
	@DataSource("manageRead")
	@Select("select uid,tid,time from t_blacklist where tid=#{tid} limit #{cursor},#{count}")
	List<BlackList> findBlackListByTid(@Param("tid") long tid, @Param("cursor") int cursor,@Param("count") int count);
	
	@DataSource("manageRead")
	@Select("select count(0) from t_blacklist where tid=#{tid}")
	public Integer findCountBlackListByTid(@Param("tid")long tid);
	
	
	@DataSource("manageWrite")
	@Select("select uid,tid,time from t_blacklist where uid=#{uid} and tid=#{tid}")
	BlackList find(@Param("uid") long uid, @Param("tid") long tid);
	
	

}
