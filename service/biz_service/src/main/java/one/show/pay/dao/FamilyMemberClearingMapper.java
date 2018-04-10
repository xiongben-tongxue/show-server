package one.show.pay.dao;

import java.util.List;

import one.show.pay.domain.FamilyMemberClearing;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

@Component
@DAO(catalog = "pay")
public interface FamilyMemberClearingMapper {


	@DataSource("manageRead")
	@Results({ @Result(column = "family_id", property = "familyId"),
		@Result(column = "user_id", property = "userId"),
		@Result(column = "user_zz", property = "userZz"),
		@Result(column = "user_income", property = "userIncome"),
		@Result(column = "user_live_duration", property = "userLiveDuration"),
		@Result(column = "user_live_effective_days", property = "userLiveEffectiveDays"),
		@Result(column = "create_time", property = "createTime")})
	@Select("select cid,family_id,user_id,role,user_zz,user_income,user_live_duration,user_live_effective_days,create_time from t_family_member_clearing where cid = #{cid} and family_id=#{familyId} order by user_income desc limit #{start}, #{count}")
	public List<FamilyMemberClearing> findFamilyMemberClearingListByCidAndFamilyId(@Param("cid") Integer cid, @Param("familyId") Long familyId, @Param("start")Integer start,  @Param("count")Integer count);

	
	@DataSource("manageRead")
	
	@Select("select count(*) from t_family_member_clearing where cid = #{cid} and family_id=#{familyId}")
	public Integer findFamilyMemberClearingListCountByCidAndFamilyId(@Param("cid") Integer cid, @Param("familyId") Long familyId);

	
	
	/**
	 * 添加
	 * @param FamilyMemberClearing
	 */
	@DataSource("manageRead")
	@Insert("insert into t_family_member_clearing(cid,family_id,user_id,role,user_zz,user_income,user_live_duration,user_live_effective_days,create_time) values(#{cid},#{familyId},#{userId},#{role},#{userZz},#{userIncome},#{userLiveDuration},#{userLiveEffectiveDays},#{createTime})")
	public int insertFamilyMemberClearing(FamilyMemberClearing clearing);
	
	
}