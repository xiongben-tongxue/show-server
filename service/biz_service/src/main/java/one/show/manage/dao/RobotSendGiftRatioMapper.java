
package one.show.manage.dao;

import java.util.List;

import one.show.manage.domain.RobotSendGiftRatio;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus  2016年7月16日 下午4:54:16
 *
 * 
 */
@Component
@DAO(catalog = "manage")
public interface RobotSendGiftRatioMapper {

	@DataSource("manageRead")
	@Results({ @Result(column = "min_count", property = "minCount"),
		@Result(column = "max_count", property = "maxCount"),
		@Result(column = "create_time", property = "createTime")})
	@Select("select giftId,min_count,max_count,ratio,create_time from t_robot_sendgift_ratio")
	public List<RobotSendGiftRatio> findList();

	/**
	 * 添加
	 * @param RobotSendgiftRatio
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_robot_sendgift_ratio(giftId,min_count,max_count,ratio,create_time) values(#{giftId},#{minCount},#{maxCount},#{ratio},#{createTime})")
	public void save(RobotSendGiftRatio robotSendGiftRatio);
}


