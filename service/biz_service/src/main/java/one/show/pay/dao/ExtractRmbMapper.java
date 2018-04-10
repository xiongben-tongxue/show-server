package one.show.pay.dao;

import java.util.List;
import java.util.Map;

import one.show.common.Constant;
import one.show.pay.domain.ExtractRmb;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import one.show.pay.thrift.view.ExtractRmbView;
import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

@Component
@DAO(catalog = "pay")
public interface ExtractRmbMapper {
	@DataSource("manageWrite")
	@Insert("insert into t_extract_rmb "
			+ "(id, uid,order_id, status, item_number, rmb_number, create_time, update_time,before_change, after_change,reason,comment,alipay_account,alipay_name) "
			+ "values "
			+ "(#{id}, #{uid}, #{orderId}, #{status}, #{itemNumber}, #{rmbNumber}, #{createTime}, #{updateTime},#{beforeChange},#{afterChange},#{reason},#{comment},#{alipayAccount},#{alipayName})")
	public void saveExtractRmb(ExtractRmb extractRmb);

	@DataSource("manageRead")
	@SelectProvider(method = "getExtractRmbListByUid", type = Provider.class)
	@Results(value = { @Result(column = "item_number", property = "itemNumber"),
			@Result(column = "order_id", property = "orderId"),
			@Result(column = "alipay_account", property = "alipayAccount"),
			@Result(column = "alipay_name", property = "alipayName"),
			@Result(column = "rmb_number", property = "rmbNumber"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime"),
			@Result(column = "finish_time", property = "finishTime"),
			@Result(column = "before_change", property = "beforeChange"),
			@Result(column = "after_change", property = "afterChange")})
	public List<ExtractRmb> getExtractRmbListByUid(@Param("paramMap") Map<String, String> paramMap,  @Param("start")Integer start,  @Param("count")Integer count);
	
	@DataSource("manageRead")
	@SelectProvider(method = "getExtractRmbCountByUid", type = Provider.class)
	public Integer getExtractRmbCountByUid(@Param("paramMap") Map<String, String> paramMap);
	
	@DataSource("manageRead")
	@Select("select max(order_id) from t_extract_rmb")
	public Long getMaxBatchNo();

	@DataSource("manageRead")
	@SelectProvider(method = "getExtractRmbTotalByUid", type = Provider.class)
	public Double getExtractRmbTotalByUid(@Param("uid") Long uid,@Param("key") String key, @Param("paramMap") Map<String, String> paramMap);

	@DataSource("manageWrite")
	@UpdateProvider(method = "updateExtractRmbById", type = Provider.class)
	public void updateExtractRmbById(@Param("id") Long id,
			@Param("paramMap") Map<String, String> paramMap);
	
	@SuppressWarnings("unchecked")
	public static class Provider {

		public String getExtractRmbCountByUid(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select count(*) from t_extract_rmb where 1=1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					if(entry.getKey().equals("status")&&entry.getValue().equals("-1")){
						continue;
					}
					if(entry.getKey().equals("uid")&&StringUtils.isEmpty(entry.getValue())){
						continue;
					}
					if (entry.getKey().equals("begin_time")){
						sql += " and `create_time`" + ">="
								+ "#{paramMap." + entry.getKey() + "}";
					}else if (entry.getKey().equals("end_time")){
						sql += " and `create_time`" + "<="
								+ "#{paramMap." + entry.getKey() + "}";
					}else{
						sql += " and `" + entry.getKey() + "`" + "="
								+ "#{paramMap." + entry.getKey() + "}";
					}
					
				}
			}
			
			return sql;
		}
		
		
		public String getExtractRmbListByUid(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select id,uid,status, item_number, rmb_number, create_time, update_time,finish_time, before_change, after_change,reason,comment,order_id,alipay_name,alipay_account from t_extract_rmb where 1 = 1";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					if(entry.getKey().equals("status")&&entry.getValue().equals("-1")){
						continue;
					}
					if(entry.getKey().equals("uid")&&StringUtils.isEmpty(entry.getValue())){
						continue;
					}
					
					if (entry.getKey().equals("begin_time")){
						sql += " and `create_time`" + ">="
								+ "#{paramMap." + entry.getKey() + "}";
					}else if (entry.getKey().equals("end_time")){
						sql += " and `create_time`" + "<="
								+ "#{paramMap." + entry.getKey() + "}";
					}else{
						sql += " and `" + entry.getKey() + "`" + "="
								+ "#{paramMap." + entry.getKey() + "}";
					}
					
					
				}
			}
			
			sql += " order by create_time desc limit #{start}, #{count}";
			
			return sql;
		}
		
		public String getExtractRmbTotalByUid(Map<String, Object> params) {
			
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String key = (String) params.get("key");
			
			String sql = "select IFNULL(sum("+key+"),0) from t_extract_rmb where uid = #{uid}";
			
			if (paramMap != null) {
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					if(entry.getKey().equals("status")&&entry.getValue().equals("-1")){
						continue;
					}
					if(entry.getKey().equals("uid")&&StringUtils.isEmpty(entry.getValue())){
						continue;
					}
					
					if (entry.getKey().equals("begin_time")){
						sql += " and `create_time`" + ">="
								+ "#{paramMap." + entry.getKey() + "}";
					}else if (entry.getKey().equals("end_time")){
						sql += " and `create_time`" + "<="
								+ "#{paramMap." + entry.getKey() + "}";
					}else{
						sql += " and `" + entry.getKey() + "`" + "="
								+ "#{paramMap." + entry.getKey() + "}";
					}
					
				}
			}
			
			return sql;
		}
		
		public String updateExtractRmbById(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			int length = paramMap.size();
			String sql = "update t_extract_rmb set ";
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{paramMap." + entry.getKey()
						+ "}";
				if (length > 0)
					sql += ",";
			}
			sql = sql + " where id = #{id} and `status`!="+Constant.EXTRACT_STATUS.SUCCESS.getValue();
			return sql;
		}
	}

	@DataSource("manageRead")
	@Results(value = { @Result(column = "item_number", property = "itemNumber"),
			@Result(column = "order_id", property = "orderId"),
			@Result(column = "alipay_account", property = "alipayAccount"),
			@Result(column = "alipay_name", property = "alipayName"),
			@Result(column = "rmb_number", property = "rmbNumber"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime"),
			@Result(column = "finish_time", property = "finishTime"),
			@Result(column = "before_change", property = "beforeChange"),
			@Result(column = "after_change", property = "afterChange")})
	@Select("select id,uid,`status`, item_number, rmb_number, create_time, update_time, finish_time,before_change, after_change,reason,comment,order_id,alipay_name,alipay_account from t_extract_rmb where order_id= #{orderId}")
	public List<ExtractRmb> getExtractRmbListByBatchNo(@Param("orderId") long orderId);

	@DataSource("manageRead")
	@Results(value = { @Result(column = "item_number", property = "itemNumber"),
			@Result(column = "order_id", property = "orderId"),
			@Result(column = "alipay_account", property = "alipayAccount"),
			@Result(column = "alipay_name", property = "alipayName"),
			@Result(column = "rmb_number", property = "rmbNumber"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime"),
			@Result(column = "finish_time", property = "finishTime"),
			@Result(column = "before_change", property = "beforeChange"),
			@Result(column = "after_change", property = "afterChange")})
	@Select("select id,uid,`status`, item_number, rmb_number, create_time, update_time, finish_time,before_change, after_change,reason,comment,order_id,alipay_name,alipay_account from t_extract_rmb where `status`= #{status} limit #{count}")
	public List<ExtractRmb> getExtractRmbListByStatus(@Param("status") int status,@Param("count") int count);

	@DataSource("manageRead")
	@Results(value = { @Result(column = "item_number", property = "itemNumber"),
			@Result(column = "order_id", property = "orderId"),
			@Result(column = "alipay_account", property = "alipayAccount"),
			@Result(column = "alipay_name", property = "alipayName"),
			@Result(column = "rmb_number", property = "rmbNumber"),
			@Result(column = "create_time", property = "createTime"),
			@Result(column = "update_time", property = "updateTime"),
			@Result(column = "finish_time", property = "finishTime"),
			@Result(column = "before_change", property = "beforeChange"),
			@Result(column = "after_change", property = "afterChange")})
	@Select("select id,uid,`status`, item_number, rmb_number, create_time, update_time, finish_time,before_change, after_change,reason,comment,order_id,alipay_name,alipay_account from t_extract_rmb where id=#{id}")
	public ExtractRmbView getExtractRmbViewById(long id);

	@DataSource("manageRead")
	@Select("select sum(rmb_number) from t_extract_rmb where status=4;")
	public double getExtractRmbTotalCount();
	
}