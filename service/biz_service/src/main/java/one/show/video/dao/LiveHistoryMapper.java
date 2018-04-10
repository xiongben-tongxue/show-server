package one.show.video.dao;

import java.util.Map;

import one.show.video.dao.LiveMapper.Provider;
import one.show.video.domain.Live;
import one.show.video.domain.LiveHistory;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * 直播Mapper
 * @author Haliaeetus leucocephalus
 * @date Thu May 19 12:18:00 CST 2016
 *
 */
@Component
@DAO
public interface LiveHistoryMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_status", property = "streamStatus"),
		@Result(column = "stream_time", property = "streamTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "cdn_type", property = "cdnType"),
		@Result(column = "record_url", property = "recordUrl"),
		@Result(column = "mp4_url", property = "mp4Url"),
		@Result(column = "city_name", property = "cityName")})
	@Select("select liveid,uid,title,topic,start_time,end_time,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name,viewed,record_url,mp4_url, status, platform from t_live_history where liveid = #{liveId}")
	public LiveHistory findById( @Param("liveId")Long liveId);
	


	/**
	 * 添加
	 * @param Live
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_live_history(liveid,uid,title,topic,start_time,end_time,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name, viewed, status, platform) values(#{liveId},#{uid},#{title},#{topic},#{startTime},#{endTime},#{snapshot},#{createTime},#{streamStatus},#{streamTime},#{streamName},#{ip},#{rtmp},#{cdnType},#{reason},#{frame},#{bitrate},#{width},#{height},#{did},#{latitude},#{longitude},#{city},#{province},#{cityName}, #{viewed}, #{status},#{platform})")
	public void save( LiveHistory liveHistory);
	
	
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	public void update( @Param("liveId")Long liveId, @Param("updateContent") Map<String, String> updateContent);

	
	
	public static class Provider {
     

        public String updateById(Map<String, Object> params) {
            Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
            int length = updateContent.size();
            String sql = "update t_live_history set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }
            sql = sql + " where liveid = #{liveId}";
            return sql;
        }
      
    }
	
}