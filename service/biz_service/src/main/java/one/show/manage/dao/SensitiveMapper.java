/**
 * 
 */
package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.App;
import one.show.manage.domain.Word;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2018年1月14日 下午12:18:00
 *
 */

@Component
public interface SensitiveMapper {
	
	 @DataSource("manageRead")
	 @Select("select word from t_harmful_word where appid = #{appId}")
	 public List<String> findWordListByAppId(@Param("appId") Integer appId);
	 
	 
	 @DataSource("manageRead")
	 @Select("select appid, appname from t_app")
	 @Results({ @Result(column = "appname", property = "appName"), @Result(column = "appid", property = "appId") })
	 public List<App> findAppList();

	 
	 @DataSource("manageRead")
	 @SelectProvider(type=Provider.class,method="findList")
	 @Results({ @Result(column = "create_time", property = "createTime"), @Result(column = "appid", property = "appId") })
	 public List<Word> findListByAppId(@Param("appId") Integer appId, @Param("word") String word, @Param("start") Integer start, @Param("count") Integer count);
	 
	 @DataSource("manageRead")
	 @SelectProvider(type=Provider.class,method="findListCount")
	 public Integer findCountByAppId(@Param("appId") Integer appId, @Param("word") String word);
	 
	 @DataSource("manageWrite")
	 @Select("insert into t_harmful_word(appid, word, create_time)  values(#{appId}, #{word}, #{createTime})")
	 public void save(Word word);
	 
	 @DataSource("manageWrite")
	 @Select("upate t_harmful_word set word=#{word} where id = #{id}")
	 public void update(@Param("id") Integer id, @Param("word") String word);
	 
	 @DataSource("manageWrite")
	 @Select("delete from t_harmful_word where id = #{id}")
	 public void delete(@Param("id") Integer id);
	 
	 public static class Provider {
	        public String findList(Map<String, Object> params) {
	        	Integer appId  = (Integer) params.get("appId");
	        	String word  = (String) params.get("word");
	            String sql = "select id, appid, word, create_time from t_harmful_word where 1=1 ";
	            if (appId != null){
	            	sql = sql +" and appid = #{appId} ";
	            }
	            if (word != null && !word.equals("")){
	            	sql = sql +" and word like '%"+word+"%' ";
	            }
	            
	            return sql + " order by create_time desc limit #{start}, #{count}";
	        }
	        
	        public String findListCount(Map<String, Object> params) {
	        	Integer appId  = (Integer) params.get("appId");
	        	String word  = (String) params.get("word");
	            String sql = "select count(*) from t_harmful_word where 1=1 ";
	            if (appId != null){
	            	sql = sql +" and appid = #{appId} ";
	            }
	            if (word != null && !word.equals("")){
	            	sql = sql +" and word like '%"+word+"%' ";
	            }
	            
	            return sql;
	        }
	      
	    }
	 

}


