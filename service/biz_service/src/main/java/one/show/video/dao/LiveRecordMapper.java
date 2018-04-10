package one.show.video.dao;

import java.util.List;

import one.show.video.domain.LiveRecord;

import org.apache.ibatis.annotations.Delete;
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
 * 
 * @author hank
 * @date Fri Jun 17 21:03:56 CST 2016
 *
 */
@Component
@DAO
public interface LiveRecordMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "cdn_type", property = "cdnType"),
		@Result(column = "update_time", property = "updateTime"),
		@Result(column = "persistent_id", property = "persistentId"),
		@Result(column = "create_time", property = "createTime")})
	@Select("select id,cdn_type,`status`,liveid,uid,bucket,`format`,ops,`keys`,urls,duration,create_time,update_time from t_live_record where id = #{id}")
	public LiveRecord findById(@Param("id") long id);

	@DataSource("manageRead")
	@Select("select id,cdn_type,`status`,liveid,uid,bucket,`format`,ops,`keys`,urls,duration,create_time,update_time from t_live_record where liveid = #{liveid}")
	public List<LiveRecord> findByLiveid(@Param("liveid") long liveid);
	
	@DataSource("manageRead")
	@Select("select id,cdn_type,`status`,liveid,uid,bucket,`format`,ops,`keys`,urls,duration,persistent_id,create_time,update_time from t_live_record where persistent_id = #{persistentId}")
	public List<LiveRecord> findByPersistentId(@Param("persistentId") String persistentId);
	

	@DataSource("manageRead")
	@Select("select id,cdn_type,`status`,liveid,uid,bucket,`format`,ops,`keys`,urls,duration,create_time,update_time from t_live_record where `status` = #{status}")
	public List<LiveRecord> findByStatus(@Param("status") int status);
	
	/**
	 * 添加
	 * @param LiveRecord
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_live_record(cdn_type,`status`,liveid,uid,bucket,`format`,ops,`keys`,urls,duration,create_time,update_time) values(#{cdnType},#{status},#{liveid},#{uid},#{bucket},#{format},#{ops},#{keys},#{urls},#{duration},#{createTime},#{updateTime})")
	public int insertLiveRecord(LiveRecord record);
	/**
	 * 更新
	 * @param LiveRecord
	 */
	@DataSource("manageWrite")
	@Update("update t_live_record set `status`=#{status},update_time=#{updateTime} where liveid=#{liveid}")
	void updateStatusByLiveId(@Param("liveid") long liveid, @Param("status")int status, @Param("updateTime")int updateTime);
	/**
	 * 更新
	 * @param LiveRecord
	 */
	@DataSource("manageWrite")
	@Update("update t_live_record set `persistent_id`=#{persistentId},status=#{status},update_time=#{updateTime} where liveid=#{liveid} and `format`=#{format}")
	void updatePersistentIdByLiveId(@Param("liveid") long liveid,@Param("format")int format, @Param("persistentId")String persistentId,@Param("status")int status,@Param("updateTime")int updateTime);
	
	@DataSource("manageWrite")
	@Delete("delete from t_live_record where liveid=#{liveid}")
	void deleteLiveRecord(@Param("liveid") long liveid);
}
