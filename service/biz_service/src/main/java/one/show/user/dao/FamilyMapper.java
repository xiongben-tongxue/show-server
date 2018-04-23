package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.domain.Family;

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
import com.souyu.shard.annotation.ShardBy;

/**
 * 家族Mapper
 * @author hank
 * @date Fri Aug 05 13:04:11 CST 2016
 *
 */
@Component("familyMapper")
@DAO
public interface FamilyMapper{

	@DataSource("manageRead")
	@Results({ @Result(column = "owner_id", property = "ownerId"),
		@Result(column = "owner_name", property = "ownerName"),
		@Result(column = "owner_sex", property = "ownerSex"),
		@Result(column = "contact_no", property = "contactNo"),
		@Result(column = "alipay_account", property = "alipayAccount"),
		@Result(column = "alipay_name", property = "alipayName"),
		@Result(column = "create_time", property = "createTime")})
	@Select("select id,name,owner_id,owner_name,owner_sex,contact_no,status,alipay_account,alipay_name,create_time,type from t_family where id = #{id}")
	public Family findById(long id);

	/**
	 * 添加
	 * @param Family
	 */
	@DataSource("manageWrite")
	@Insert("insert into t_family(name,owner_id,owner_name,owner_sex,contact_no,status,alipay_account,alipay_name,create_time,type) values(#{name},#{ownerId},#{ownerName},#{ownerSex},#{contactNo},#{status},#{alipayAccount},#{alipayName},#{createTime},#{type})")
	public void insertFamily(Family family);
	/**
	 * 更新
	 * @param Family
	 */
	@DataSource("manageWrite")
	@UpdateProvider(type = Provider.class, method = "updateById")
	void updateById(@Param("id") long id, @Param("updateContent") Map<String, String> updateContent);
	
	
	@DataSource("manageRead")
	@Results({ @Result(column = "owner_id", property = "ownerId"),
		@Result(column = "owner_name", property = "ownerName"),
		@Result(column = "owner_sex", property = "ownerSex"),
		@Result(column = "contact_no", property = "contactNo"),
		@Result(column = "alipay_account", property = "alipayAccount"),
		@Result(column = "alipay_name", property = "alipayName"),
		@Result(column = "create_time", property = "createTime")})
	@SelectProvider(type = Provider.class, method = "findFamilyList")
	List<Family> findFamilyList(@Param("condition")Map<String, String> condition, @Param("start")Integer start, @Param("count")Integer count);
	
    @DataSource("manageRead")
    @SelectProvider(type = Provider.class, method = "findByIds")
    @Options(useCache=false, flushCache=true)
    public List<Family> findByIds(@ShardBy(value = "id", name = "ids") @Param("ids") List<Long> ids);
	
	@DataSource("manageRead")
	@SelectProvider(method="findFamilyListCount",type=Provider.class)
	public Integer findFamilyListCount(@Param("condition")Map<String, String> condition);
	
	public static class Provider {
		
		public String updateById(Map<String, Object> params) {
			Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
			int length = updateContent.size();
			String sql = "update t_family set ";
			for (Map.Entry<String, String> entry : updateContent.entrySet()) {
				length--;
				sql += entry.getKey() + " = " + "#{updateContent." + entry.getKey()+"}";
				if (length > 0)
				sql += ",";
			}
			sql = sql + " where id = #{id}";
			return sql;
		}
		
		public String findFamilyList(Map<String, Object> params) {
			String sql = "select id,name,owner_id,owner_name,owner_sex,contact_no,status,alipay_account,alipay_name,create_time,type from t_family where 1=1 ";
			Map<String, String> paramMap = (Map<String, String>) params.get("condition");
			if(paramMap!=null){
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+ "}";
				}
			}
			sql +=" order by create_time desc limit #{start}, #{count}"; 
			return sql;
		}
		
		public String findFamilyListCount(Map<String, Object> params) {
			String sql = "select count(*) from t_family where 1=1 ";
			Map<String, String> paramMap = (Map<String, String>) params.get("condition");			
			if(paramMap!=null){
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and "+entry.getKey() + " = " + "#{condition." + entry.getKey()+ "}";
				}
			}
			return sql;
		}
		
        public String findByIds(Map<String, Object> params) {
            List<Long> ids = (List<Long>) params.get("ids");

            return "select id,name,owner_id,owner_name,owner_sex,contact_no,status,alipay_account,alipay_name,create_time,type from t_family where id in (" + Joiner.on(",").join(ids) + ") ";
        }
	}
}