package one.show.manage.dao;

import java.util.List;

import one.show.manage.domain.SendExchange;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

@Component
public interface SendExchangeMapper {
	@DataSource("manageWrite")
	@Insert("insert into t_send_exchange (send_type,send_by,uid,vip_type,create_at,gold_num,reason) values (#{sendType},#{sendBy},#{uid},#{vipType},#{createAt},#{goldNum},#{reason})")
	public void saveSendExchange(SendExchange sendExchange);
	
	@DataSource("manageRead")
	@Results(value = { @Result(column = "send_type", property = "sendType"),
			@Result(column = "send_by", property = "sendBy"),
			@Result(column = "vip_type", property = "vipType"),
			@Result(column = "create_at", property = "createAt"),
			@Result(column = "gold_num", property = "goldNum")
	})
	@Select("select id,send_type,send_by,uid,vip_type,create_at,gold_num,reason from t_send_exchange where uid=#{uid} and send_type=#{sendType} order by create_at desc limit #{start},#{count}")
	public List<SendExchange> findSendExchangeByUid(@Param("uid")Long uid,@Param("sendType")int sendType,@Param("start")int start,@Param("count")int count);
	
	@DataSource("manageRead")
	@Select("select count(0) from t_send_exchange where uid=#{uid} and send_type=#{sendType}")
	public Integer findSendExchangeCountByUid(@Param("uid")Long uid,@Param("sendType")int sendType);
}
