package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.Register;
import one.show.manage.domain.SystemConfig;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

@Component
@DAO(catalog = "manage")
public interface RegisterMapper {
	@DataSource("manageWrite")
	@Insert("insert into t_register (uid,status,reason,create_time,update_time,user_name,bank_name,bank_place,bank_branch,bank_no,identity_no,identity_front,identity_back,identity_verify,account_type,alipay_no,alipay_name,phone_no,qq) values(#{uid},#{status},#{reason},#{createTime},#{updateTime},#{userName},#{bankName},#{bankPlace},#{bankBranch},#{bankNo},#{identityNo},#{identityFront},#{identityBack},#{identityVerify},#{accountType},#{alipayNo},#{alipayName},#{phoneNo},#{qq})")
	public void saveRegister(Register register);
	
	@DataSource("manageRead")
	@Results({@Result(column="create_time", property="createTime"),
		@Result(column="update_time", property="updateTime"),
		@Result(column="user_name", property="userName"),
		@Result(column="bank_name", property="bankName"),
		@Result(column="bank_place", property="bankPlace"),
		@Result(column="bank_branch", property="bankBranch"),
		@Result(column="bank_no", property="bankNo"),
		@Result(column="identity_no", property="identityNo"),
		@Result(column="identity_front", property="identityFront"),
		@Result(column="identity_back", property="identityBack"),
		@Result(column="identity_verify", property="identityVerify"),
		@Result(column="account_type", property="accountType"),
		@Result(column="alipay_no", property="alipayNo"),
		@Result(column="alipay_name", property="alipayName"),
		@Result(column="phone_no", property="phoneNo")})
	@Select("select uid,status,reason,create_time,update_time,user_name,bank_name,bank_place,bank_branch,bank_no,identity_no,identity_front,identity_back,identity_verify,account_type,alipay_no,alipay_name,phone_no,qq from t_register where uid = #{uid} ")
	public Register getRegisterByUid(@Param("uid") String uid);
	
	@DataSource("manageWrite")
	@UpdateProvider(method = "updateRegisterByUid", type = Provider.class)
	public void updateRegisterByUid(@Param("uid") String uid,
			@Param("paramMap") Map<String, String> paramMap);
	
	@DataSource("manageWrite")
	@Update("update t_register set status=#{status},create_time=#{createTime},update_time=#{updateTime},user_name=#{userName},bank_name=#{bankName},bank_place=#{bankPlace},bank_branch=#{bankBranch},bank_no=#{bankNo},identity_no=#{identityNo},identity_front=#{identityFront},identity_back=#{identityBack},identity_verify=#{identityVerify},account_type=#{accountType},alipay_no=#{alipayNo},alipay_name=#{alipayName},phone_no=#{phoneNo},qq=#{qq},reason=#{reason} where uid=#{uid}")
	public void updateRegister(Register register);
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "getRegisterList")
	@Results({@Result(column="create_time", property="createTime"),
		@Result(column="update_time", property="updateTime"),
		@Result(column="user_name", property="userName"),
		@Result(column="bank_name", property="bankName"),
		@Result(column="bank_place", property="bankPlace"),
		@Result(column="bank_branch", property="bankBranch"),
		@Result(column="bank_no", property="bankNo"),
		@Result(column="identity_no", property="identityNo"),
		@Result(column="identity_front", property="identityFront"),
		@Result(column="identity_back", property="identityBack"),
		@Result(column="identity_verify", property="identityVerify"),
		@Result(column="account_type", property="accountType"),
		@Result(column="alipay_no", property="alipayNo"),
		@Result(column="alipay_name", property="alipayName"),
		@Result(column="phone_no", property="phoneNo")})
	public List<Register> getRegisterList(@Param("paramMap") Map<String, String> paramMap,
			@Param("start") Integer start, @Param("count") Integer count);
	
	@DataSource("manageRead")
	@SelectProvider(method = "getRegisterCount", type = Provider.class)
	public Integer getRegisterCount(@Param("paramMap") Map<String, String> paramMap);
	
	@SuppressWarnings("unchecked")
	public static class Provider {
		
		public String updateRegisterByUid(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			int length = paramMap.size();
			String sql = "update t_register set ";
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{paramMap." + entry.getKey()
						+ "}";
				if (length > 0)
					sql += ",";
			}
			sql = sql + " where uid = #{uid}";
			return sql;
		}

		public String getRegisterList(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			
			String sql = "select uid,status,reason,create_time,update_time,user_name,bank_name,bank_place,bank_branch,bank_no,identity_no,identity_front,identity_back,identity_verify,account_type,alipay_no,alipay_name,phone_no,qq from t_register where 1 = 1  ";
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and `" + entry.getKey() + "`" + "=" + "#{paramMap."
							+ entry.getKey() + "}";
				}
			}
			
			return sql + " order by create_time asc limit #{start},#{count}";
		}
		
		public String getRegisterCount(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select count(*) from t_register where 1 = 1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and `" + entry.getKey() + "`" + "="
							+ "#{paramMap." + entry.getKey() + "}";
				}
			}
			
			return sql;
		}
	}
}