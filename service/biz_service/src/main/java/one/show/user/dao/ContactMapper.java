/**
 * 
 */
package one.show.user.dao;

import java.util.List;
import java.util.Map;

import one.show.user.domain.Contact;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;
import com.souyu.shard.annotation.ShardBy;

/**
 * @author zhangwei 2015年8月4日 下午5:35:45
 * 用户手机通讯录
 */

@Component
@DAO
public interface ContactMapper {
	
	 @DataSource("manageRead")
	 @Select("select id, uid,name,email,number from t_contact where uid = #{uid}")
	 public List<Contact> findListByUid( @Param("uid") String uid);
	 
	 
	 @DataSource("manageWrite")
	 @Insert("insert into t_contact(id, uid,name,email,number) values(#{phone.id},#{phone.uid},#{phone.name},#{phone.email},#{phone.number})")
	 public void save( @Param("phone") Contact phone);
	 
	 @DataSource("manageWrite")
	 @InsertProvider(type = Provider.class,method="batchSave")
	 @Options(useGeneratedKeys = false)
	 public void batchSave(String uid, @Param("list")  List<Contact> contactList);

	 public static class Provider {

	        public String batchSave(Map<String, Object> params) {
	            List<Contact> list = (List<Contact>) params.get("list");
	            String sql = "insert into t_contact (uid,name,email,number) values ";
	            for (Contact contact : list) {
					sql += "('"+contact.getUid()+"','"+contact.getName()+"','"+contact.getEmail()+"','"+contact.getNumber()+"')";
					sql += ",";
				}
	            sql = sql.substring(0,sql.length()-1);
	            return sql;
	        }
	      
	    }
}


