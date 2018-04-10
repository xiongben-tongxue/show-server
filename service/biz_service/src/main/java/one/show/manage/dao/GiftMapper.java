/**
 * 
 */
package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.Gift;
import one.show.manage.domain.GiftType;

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

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2018年3月8日 下午3:22:21
 *
 */

@Component
public interface GiftMapper {
	
	@DataSource("manageWrite")
    @Delete("delete from t_gift where id=#{id}")
	public void delete(@Param("id") Integer id);
	
	@DataSource("manageWrite")
    @Insert("insert into t_gift (name, price, exp, image, icon, type, status, sort,extend, create_time) values (#{name}, #{price}, #{exp}, #{image}, #{icon}, #{type}, #{status}, #{sort},#{extend}, #{createTime})")
	public void save(Gift gift);
	
	@DataSource("manageWrite")
    @UpdateProvider(type = Provider.class, method = "update")
	public void update(@Param("updateContent") Map<String, String> updateContent, @Param("id") Integer id);

	@DataSource("manageRead")
	@Select("select id, name, price, exp, image, icon, type, status, sort,extend, create_time from t_gift where id=#{id}")
	@Results({@Result(column = "create_time", property = "createTime")})
	public Gift findGiftById(@Param("id") Integer giftId);
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findGiftTypeList")
	public List<GiftType> findGiftTypeList(@Param("status") Integer status);

	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findGiftList")
	@Results({@Result(column = "create_time", property = "createTime")})
	public List<Gift> findGiftList(@Param("type") Integer type, @Param("status") Integer status, @Param("start") Integer start, @Param("count") Integer count);
	
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "findGiftListCount")
	public Integer findGiftListCount(@Param("type") Integer type, @Param("status") Integer status);
	
	public static class Provider {
		
		public String findGiftTypeList(Map<String, Object> params) {
        	Integer status = (Integer)params.get("status");
        	
        	if (status == null){
            	return "select id, name, status from t_gift_type";
            }else{
            	return "select id, name, status from t_gift_type where status=#{status}";
            }
           
            
        }
	       
        public String findGiftList(Map<String, Object> params) {
        	Integer status = (Integer)params.get("status");
        	Integer type = (Integer)params.get("type");
        	
        	String sql = "select id, name, price, exp, image, icon, type, status, sort,extend, create_time from t_gift where 1=1";
        	if (status != null){
        		sql += " and status=#{status}";
        	}
        	if(type != null){
        		sql += " and type=#{type}";
        	}
        	
        	sql +=  " order by sort desc, price asc limit #{start},#{count}";
        	
        	return sql;
           
            
        }
        public String findGiftListCount(Map<String, Object> params) {
        	Integer status = (Integer)params.get("status");
        	Integer giftType = (Integer)params.get("type");
        	
        	String sql = "select count(*) from t_gift where 1=1";
        	if (status != null){
        		sql += " and status=#{status}";
        	}
        	if(giftType != null){
        		sql += " and type=#{type}";
        	}
        	
        	return sql;
            
        }
        
        public String update(Map<String, Object> params) {
        	Map<String, String> updateContent = (Map<String, String>) params.get("updateContent");
        	
        	int length = updateContent.size();
            String sql = "update t_gift set ";
            for (Map.Entry<String, String> entry : updateContent.entrySet()) {
                length--;
                sql += "`" + entry.getKey() + "`" + "=" + "#{updateContent."+entry.getKey()+"}";
                if (length > 0)
                    sql += ",";
            }            
            return sql + " where id = #{id}";
            
        }
        
        
    }

}


