/**
 * 
 */
package one.show.stat.dao;

import one.show.stat.domain.VideoStat;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * @author zhangyihu 2018年1月14日
 *
 */

@Component
@DAO(catalog = "stat")
public interface VideoStatMapper {
	
	 @DataSource("manageRead")
	 @Results(value={@Result(column="gift_num",property="giftNum")})
	 @Select("select vid, viewed, share, liked, receive, gift_num from t_video_stat where vid=#{vid}")
	 public VideoStat findByVid( Long vid);
	
	
	 @DataSource("manageWrite")
	 @Insert("insert into t_video_stat(vid, viewed,  share, liked, gift_num, receive) values(#{vid}, #{viewed}, #{share}, #{liked}, #{giftNum}, #{receive})")
	 public void save( VideoStat videoStat);
	 
	 
	 @DataSource("manageWrite")
	 @Update("update t_video_stat set viewed=viewed+#{viewed},share=share+#{share},liked=liked+#{liked}, gift_num=gift_num+#{giftNum}, receive=receive+#{receive}  where vid=#{vid}")
	 public void update( VideoStat videoStat);
	
}