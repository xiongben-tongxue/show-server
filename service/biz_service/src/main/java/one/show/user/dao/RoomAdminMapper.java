package one.show.user.dao;

import java.util.List;

import one.show.user.domain.RoomAdmin;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

/**
 * Created by zhangwei on 15/7/14.
 */

@DAO
@Component
public interface RoomAdminMapper {

	
	@DataSource("manageWrite")
    @Insert("insert into t_room_admin (uid,tid,time) values (#{uid},#{tid},#{time})")
	public int save(@Param("uid") long uid, @Param("tid") long tid, @Param("time") int time);

	@DataSource("manageWrite")
	@Select("delete from t_room_admin where uid=#{uid} and tid=#{tid}")
	void delete(@Param("uid") long uid, @Param("tid") long tid);
	
	@DataSource("manageWrite")
	@Select("select uid,tid,time from t_room_admin where uid=#{uid} and tid=#{tid}")
	RoomAdmin find(@Param("uid") long uid, @Param("tid") long tid);


	@DataSource("manageRead")
	@Select("select tid from t_room_admin where uid=#{uid} order by time asc limit #{cursor},#{count}")
	List<Long> findAdminListByUid(@Param("uid") long uid, @Param("cursor") int cursor,@Param("count") int count);
	
	@DataSource("manageRead")
	@Select("select count(0) from t_room_admin where uid=#{uid}")
	public Integer findCountAdminListByUid(@Param("uid")long uid);
	
	
	

}
