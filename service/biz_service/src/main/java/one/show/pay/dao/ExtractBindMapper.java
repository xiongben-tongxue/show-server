package one.show.pay.dao;

import one.show.pay.domain.ExtractBind;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

@Component
@DAO(catalog = "pay")
public interface ExtractBindMapper {
	@DataSource("manageWrite")
	@Insert("insert into t_extract_bind "
			+ "(uid,alipay_account, alipay_name , phone_num, create_time) "
			+ "values "
			+ "(#{uid}, #{alipayAccount}, #{alipayName}, #{phoneNum}, #{createTime})")
	public void save(ExtractBind extractBind);

	@DataSource("manageRead")
	@Results(value = { @Result(column = "phone_num", property = "phoneNum"),
			@Result(column = "alipay_account", property = "alipayAccount"),
			@Result(column = "alipay_name", property = "alipayName"),
			@Result(column = "create_time", property = "createTime")})
	@Select("select uid,alipay_account,alipay_name, phone_num, create_time from t_extract_bind where uid = #{uid}")
	public ExtractBind getByUid(@Param("uid") long uid);
	
	
	@DataSource("manageWrite")
	@Update("update t_extract_bind set alipay_account=#{alipayAccount}, alipay_name=#{alipayName} where uid=#{uid}")
	public void updateAlipayByUid(@Param("uid") long uid, @Param("alipayAccount") String alipayAccount, @Param("alipayName") String alipayName);
	
	
	
}