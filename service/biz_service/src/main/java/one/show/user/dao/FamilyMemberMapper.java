package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.dao.FamilyMapper.Provider;
import one.show.user.domain.Family;
import one.show.user.domain.FamilyMember;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;


/**
 * 用户家族关系Mapper
 * @author hank
 * @date Fri Aug 05 15:16:00 CST 2016
 *
 */
@Component("familyMemberMapper")
@DAO
public interface FamilyMemberMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "family_id", property = "familyId"),
		@Result(column = "user_id", property = "userId"),
		@Result(column = "join_time", property = "joinTime")})
	@Select("select family_id,user_id,role,join_time from t_family_member where family_id = #{familyId} and user_id = #{userId}")
	public FamilyMember findById(@Param("familyId") long familyId,@Param("userId") long userId);

	@DataSource("manageRead")
	@Results({ @Result(column = "family_id", property = "familyId"),
		@Result(column = "user_id", property = "userId"),
		@Result(column = "join_time", property = "joinTime")})
	@Select("select family_id,user_id,role,join_time from t_family_member where family_id = #{familyId} order by role, join_time desc, id limit #{start}, #{count}")
	public List<FamilyMember> findListByFamilyId(@Param("familyId") long familyId, @Param("start")int start, @Param("count")int count);
	
	@DataSource("manageRead")
	@Select("select count(*) from t_family_member where family_id = #{familyId}")
	public Integer findCountByFamilyId(@Param("familyId") long familyId);
	
	/**
	 * 添加
	 * @param FamilyMember
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_family_member(family_id,user_id,role,join_time) values(#{familyId},#{userId},#{role},#{joinTime})")
	public void insertFamilyMember(FamilyMember member);
	
	
	/**
	 * 删除
	 */
	@DataSource("manageWrite")
	@Delete("delete  from t_family_member where family_id = #{familyId} and user_id = #{userId}")
	public void deleteById(@Param("familyId") long familyId,@Param("userId") long userId);

}
