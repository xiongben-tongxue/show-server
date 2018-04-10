package one.show.pay.dao;

import java.util.List;
import java.util.Map;

import one.show.common.Constant;
import one.show.pay.dao.ExtractRmbMapper.Provider;
import one.show.pay.domain.FamilyClearing;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

@Component
@DAO(catalog = "pay")
public interface FamilyClearingMapper {


	@DataSource("manageRead")
	@Results({ @Result(column = "family_id", property = "familyId"),
		@Result(column = "user_zz_total", property = "userZzTotal"),
		@Result(column = "user_income_total", property = "userIncomeTotal"),
		@Result(column = "user_live_duration_total", property = "userLiveDurationTotal"),
		@Result(column = "effective_users", property = "effectiveUsers"),
		@Result(column = "family_clearing_ratio", property = "familyClearingRatio"),
		@Result(column = "family_income", property = "familyIncome"),
		@Result(column = "owner_id", property = "ownerId"),
		@Result(column = "order_id", property = "orderId"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "finish_time", property = "finishTime"),
		@Result(column = "total_payment", property = "totalPayment")})
	@Select("select id,family_id,user_zz_total,user_income_total,user_live_duration_total,effective_users,family_clearing_ratio,family_income,owner_id,status,order_id,reason,create_time,finish_time,total_payment from t_family_clearing where id = #{id}")
	public FamilyClearing findById(@Param("id") String id);

	/**
	 * 添加
	 * @param FamilyClearing
	 */
	@DataSource("manageRead")
	@Options(useGeneratedKeys = true, keyProperty="id")
	@Insert("insert into t_family_clearing(id,family_id,user_zz_total,user_income_total,user_live_duration_total,effective_users,family_clearing_ratio,family_income,owner_id,status,create_time,finish_time,total_payment) values(#{id},#{familyId},#{userZzTotal},#{userIncomeTotal},#{userLiveDurationTotal},#{effectiveUsers},#{familyClearingRatio},#{familyIncome},#{ownerId},#{status},#{createTime},#{finishTime},#{totalPayment})")
	public void insertFamilyClearing(FamilyClearing clearing);
	
	@DataSource("manageRead")
	@Select("select id,family_id,user_zz_total,user_income_total,user_live_duration_total,effective_users,family_clearing_ratio,family_income,owner_id,status,order_id,reason,create_time,finish_time,total_payment from t_family_clearing where id=#{id}")
	@Results({ @Result(column = "family_id", property = "familyId"),
		@Result(column = "user_zz_total", property = "userZzTotal"),
		@Result(column = "user_income_total", property = "userIncomeTotal"),
		@Result(column = "user_live_duration_total", property = "userLiveDurationTotal"),
		@Result(column = "effective_users", property = "effectiveUsers"),
		@Result(column = "family_clearing_ratio", property = "familyClearingRatio"),
		@Result(column = "family_income", property = "familyIncome"),
		@Result(column = "owner_id", property = "ownerId"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "finish_time", property = "finishTime"),
		@Result(column = "total_payment", property = "totalPayment")})
	public FamilyClearing getFamilyClearingById(long id);
	
	@DataSource("manageRead")
	@SelectProvider(method = "getFamilyClearingList", type = Provider.class)
	@Results({ @Result(column = "family_id", property = "familyId"),
		@Result(column = "user_zz_total", property = "userZzTotal"),
		@Result(column = "user_income_total", property = "userIncomeTotal"),
		@Result(column = "user_live_duration_total", property = "userLiveDurationTotal"),
		@Result(column = "effective_users", property = "effectiveUsers"),
		@Result(column = "family_clearing_ratio", property = "familyClearingRatio"),
		@Result(column = "family_income", property = "familyIncome"),
		@Result(column = "owner_id", property = "ownerId"),
		@Result(column = "create_time", property = "createTime"),
		@Result(column = "finish_time", property = "finishTime"),
		@Result(column = "total_payment", property = "totalPayment")})
	public List<FamilyClearing> getFamilyClearingList(@Param("paramMap") Map<String, String> paramMap,  @Param("start")Integer start,  @Param("count")Integer count);

	@DataSource("manageRead")
	@SelectProvider(method = "getFamilyClearingListCount", type = Provider.class)
	public Integer getFamilyClearingListCount(@Param("paramMap") Map<String, String> paramMap);
	
	@DataSource("manageWrite")
	@UpdateProvider(method = "updateFamilyClearing", type = Provider.class)
	public void updateFamilyClearing(@Param("id") Long id,
			@Param("updateContent") Map<String, String> updateContent);
	
	public static class Provider {

		public String getFamilyClearingListCount(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select count(*) from t_family_clearing where 1 = 1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					
					sql += " and `" + entry.getKey() + "`" + "="
							+ "#{paramMap." + entry.getKey() + "}";
					
				}
			}
			
			return sql;
		}
		
		
		public String getFamilyClearingList(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select id,family_id,user_zz_total,user_income_total,user_live_duration_total,effective_users,family_clearing_ratio,family_income,owner_id,status,order_id,reason,create_time,finish_time,total_payment from t_family_clearing where 1 = 1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					
					sql += " and `" + entry.getKey() + "`" + "="
							+ "#{paramMap." + entry.getKey() + "}";
					
				}
			}
			
			sql += " order by create_time desc limit #{start}, #{count}";
			
			return sql;
		}
		

		public String updateFamilyClearing(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("updateContent");
			int length = paramMap.size();
			String sql = "update t_family_clearing set ";
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()
						+ "}";
				if (length > 0)
					sql += ",";
			}
			sql = sql + " where id = #{id} and `status`!="+Constant.EXTRACT_STATUS.SUCCESS.getValue();
			return sql;
		}
		
	}

}