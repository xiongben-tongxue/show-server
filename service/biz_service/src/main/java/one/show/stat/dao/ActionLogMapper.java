
package one.show.stat.dao;

import one.show.stat.domain.ActionLog;

import org.apache.ibatis.annotations.Insert;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus  2016年7月3日 下午6:00:13
 *
 * 
 */

public interface ActionLogMapper {
	
	/**
	 * 添加
	 * @param Log
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_action_log(id,ma,im,idfa,idfy,chl,lo,lt,mo,op,co,os,sc,ov,vc,vn,ne,uuid,uid,tn,pname,content,aid,vid,city,platform,event,gift_id,send_gift,amount,pay_type,duration,channel,time,ip) values(#{id},#{ma},#{im},#{idfa},#{idfy},#{chl},#{lo},#{lt},#{mo},#{op},#{co},#{os},#{sc},#{ov},#{vc},#{vn},#{ne},#{uuid},#{uid},#{tn},#{pname},#{content},#{aid},#{vid},#{city},#{platform},#{event},#{giftId},#{sendGift},#{amount},#{payType},#{duration},#{channel},#{time},#{ip})")
	public int save(ActionLog log);


}


