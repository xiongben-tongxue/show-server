package one.show.video.dao;

import java.util.List;
import java.util.Map;

import one.show.video.domain.Live;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

/**
 * 直播Mapper
 * @author Haliaeetus leucocephalus
 * @date Thu May 19 12:18:00 CST 2016
 *
 */
@Component("liveMapper")
@DAO
public interface LiveMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_status", property = "streamStatus"),
		@Result(column = "stream_time", property = "streamTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "cdn_type", property = "cdnType"),
		@Result(column = "robot_number", property = "robotNumber"),
		@Result(column = "record_url", property = "recordUrl"),
		@Result(column = "vod_status", property = "vodStatus"),
		@Result(column = "city_name", property = "cityName"),
		@Result(column = "square_pos", property = "squarePos")})
	@Select("select liveid,uid,title, topic,start_time,end_time,status,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name,viewed,robot_number,record_url,vod_status, platform, square_pos from t_live where liveid = #{liveId}")
	public Live findById(@Param("liveId")Long liveId);
	
	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_status", property = "streamStatus"),
		@Result(column = "stream_time", property = "streamTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "cdn_type", property = "cdnType"),
		@Result(column = "robot_number", property = "robotNumber"),
		@Result(column = "record_url", property = "recordUrl"),
		@Result(column = "vod_status", property = "vodStatus"),
		@Result(column = "city_name", property = "cityName"),
		@Result(column = "square_pos", property = "squarePos")})
	@Select("select liveid,uid,title,topic, start_time,end_time,status,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name,viewed,robot_number,record_url,vod_status, platform, square_pos from t_live where uid = #{uid} and status=1")
	public Live findInLiveByUid(@Param("uid")Long uid);
	
	
	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_status", property = "streamStatus"),
		@Result(column = "stream_time", property = "streamTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "cdn_type", property = "cdnType"),
		@Result(column = "robot_number", property = "robotNumber"),
		@Result(column = "record_url", property = "recordUrl"),
		@Result(column = "vod_status", property = "vodStatus"),
		@Result(column = "city_name", property = "cityName"),
		@Result(column = "square_pos", property = "squarePos")})
	@SelectProvider(method="findLiveList",type=Provider.class)
	public List<Live> findLiveList(@Param("condition")Map<String, String> condition, @Param("sort")String sort, @Param("start")Integer start, @Param("count")Integer count);
	
	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_status", property = "streamStatus"),
		@Result(column = "stream_time", property = "streamTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "cdn_type", property = "cdnType"),
		@Result(column = "robot_number", property = "robotNumber"),
		@Result(column = "record_url", property = "recordUrl"),
		@Result(column = "vod_status", property = "vodStatus"),
		@Result(column = "city_name", property = "cityName"),
		@Result(column = "square_pos", property = "squarePos")})
	@SelectProvider(method="findLiveList4Square",type=Provider.class)
	public List<Live> findLiveList4Square(@Param("sort")String sort, @Param("start")Integer start, @Param("count")Integer count);
	
	
	@DataSource("manageRead")
	@Results({ @Result(column = "liveid", property = "liveId"),
		@Result(column = "start_time", property = "startTime"),
		@Result(column = "end_time", property = "endTime"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "stream_status", property = "streamStatus"),
		@Result(column = "stream_time", property = "streamTime"),
		@Result(column = "stream_name", property = "streamName"),
		@Result(column = "cdn_type", property = "cdnType"),
		@Result(column = "robot_number", property = "robotNumber"),
		@Result(column = "record_url", property = "recordUrl"),
		@Result(column = "vod_status", property = "vodStatus"),
		@Result(column = "city_name", property = "cityName"),
		@Result(column = "square_pos", property = "squarePos")})
	@Select("select liveid,uid,title, topic, start_time,end_time,status,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name,viewed,robot_number,record_url,vod_status, platform, square_pos from t_live where status=1 order by square_pos desc,  viewed desc limit #{start}, #{count}")
	public List<Live> findLiveList4HandSort(@Param("start")Integer start, @Param("count")Integer count);

	
	
	@DataSource("manageRead")
	@SelectProvider(method="findLiveListCount",type=Provider.class)
	public Integer findLiveListCount(@Param("condition")Map<String, String> condition);
	
	
	
	@DataSource("manageWrite")
	@Select("delete from t_live where liveid = #{liveId}")
	public void deleteByLiveId(@Param("liveId")Long liveId);
	
	
	@DataSource("manageWrite")
	@Select("delete from t_live where uid = #{uid} and status=#{status}")
	public void deleteByUidAndStatus(@Param("uid")Long uid, @Param("status")Integer status);
	
	/*直播时长*/
	@DataSource("manageRead")
	@Select("select IFNULL(sum(end_time-start_time),0) from t_live where uid = #{uid} and (status=0 or status=3) and start_time>=#{startTime} and start_time<=#{endTime}")
	public Integer findLiveDurationByUid(@Param("uid")Long uid,  @Param("startTime")Integer startTime,  @Param("endTime")Integer endTime);

	/*直播有效天数*/
	@DataSource("manageRead")
	@Select("select count(*) from (SELECT sum(end_time-start_time) as totaltime, SUBSTRING(FROM_UNIXTIME(start_time), 1 , 10) as `day` from t_live where  uid = #{uid}  and (status=0 or status=3) and start_time>=#{startTime} and start_time<=#{endTime} group by `day` HAVING totaltime>=3600) a")
	public Integer findLiveEffectiveDaysByUid(@Param("uid")Long uid,  @Param("startTime")Integer startTime,  @Param("endTime")Integer endTime);


	/**
	 * 添加
	 * @param Live
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_live(liveid,uid,title,topic, start_time,end_time,status,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name,robot_number, platform, square_pos) values(#{liveId},#{uid},#{title},#{topic},#{startTime},#{endTime},#{status},#{snapshot},#{createTime},#{streamStatus},#{streamTime},#{streamName},#{ip},#{rtmp},#{cdnType},#{reason},#{frame},#{bitrate},#{width},#{height},#{did},#{latitude},#{longitude},#{city},#{province},#{cityName},#{robotNumber},#{platform},#{squarePos})")
	public void save(Live live);
	
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	public void update(@Param("liveId")Long liveId, @Param("updateContent") Map<String, String> updateContent);

	
	
	public static class Provider {
     
		public String findLiveList(Map<String, Object> params) {
			String sql = "select liveid,uid,title, topic, start_time,end_time,status,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name,viewed,robot_number,record_url,vod_status, platform, square_pos from t_live where 1=1 ";
			Map<String, String> paramMap = (Map<String, String>) params.get("condition");
			String sort = (String) params.get("sort");
			if(paramMap!=null){
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+ "}";
				}
			}
			sql +=" order by status desc,  "+sort+" desc limit #{start}, #{count}"; 
			return sql;
		}
		
		public String findLiveListCount(Map<String, Object> params) {
			String sql = "select count(*) from t_live where 1=1 ";
			Map<String, String> paramMap = (Map<String, String>) params.get("condition");			
			if(paramMap!=null){
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+ "}";
				}
			}
			return sql;
		}

        public String updateById(Map<String, Object> params) {
            Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
            int length = updateContent.size();
            String sql = "update t_live set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }
            sql = sql + " where liveid = #{liveId}";
            return sql;
        }
        
        public String findLiveList4Square(Map<String, Object> params) {
			String sql = "select liveid,uid,title, topic, start_time,end_time,status,snapshot,create_time,stream_status,stream_time,stream_name,ip,rtmp,cdn_type,reason,frame,bitrate,width,height,did,latitude,longitude,city,province, city_name,viewed,robot_number,record_url,vod_status,square_pos from t_live ";
			
			String nsql = "select max(create_time) create_time from t_live where vod_status = 0 and (status = 0 or status = 1) group by uid ";
			
			
			sql += " where create_time in("+nsql+")";
			
			String sort = (String) params.get("sort");
			sql += " order by status desc,  "+sort+" desc limit #{start}, #{count}"; 
			
			return sql ;
		}
      
    }

	
}