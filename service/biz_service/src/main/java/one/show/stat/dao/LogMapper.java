
package one.show.stat.dao;

import one.show.stat.domain.Log;

import org.apache.ibatis.annotations.Insert;

import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * @author Haliaeetus leucocephalus  2016年7月3日 下午6:00:13
 *
 * 
 */

public interface LogMapper {
	
	/**
	 * 添加
	 * @param Log
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_log(ma,im,idfa,idfy,chl,lo,lt,mo,op,co,os,sc,ov,vc,vn,ne,uuid,uid,city,platform,log_type,live_time,send_gift,recharge_rmb,recharge_xnb,duration, time, channel, pay_type) values(#{ma},#{im},#{idfa},#{idfy},#{chl},#{lo},#{lt},#{mo},#{op},#{co},#{os},#{sc},#{ov},#{vc},#{vn},#{ne},#{uuid},#{uid},#{city},#{platform},#{logType},#{liveTime},#{sendGift},#{rechargeRmb},#{rechargeXnb},#{duration},#{time},#{channel},#{payType})")
	public void save(Log log);

}


