package one.show.manage.dao;

import java.util.List;
import java.util.Map;

import one.show.manage.domain.SystemConfig;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

@Component
@DAO(catalog = "manage")
public interface SystemConfigMapper {
	
	@DataSource("manageRead")
	@SelectProvider(type = Provider.class, method = "getSystemConfigListByParam")
	@Results({ @Result(column = "config_id", property = "configId"),
		@Result(column = "share_sina", property = "shareSina"),
		@Result(column = "share_qq", property = "shareQq"),
		@Result(column = "share_qqzone", property = "shareQqzone"),
		@Result(column = "share_wx", property = "shareWx"),
		@Result(column = "share_wx_pyq", property = "shareWxPyq"),
		@Result(column = "show_ad", property = "showAd"),
		@Result(column = "show_qq_login", property = "showQQLogin"),
		@Result(column = "show_exchange", property = "showExchange"),
		@Result(column = "create_time", property = "createTime")})
	public List<SystemConfig> getSystemConfigList(
			@Param("paramMap") Map<String, String> paramMap,
			@Param("start") int start, @Param("count") int count);
	
	@DataSource("manageWrite")
    @Insert("insert into t_system_config(version,bitrate,frame,width,height ,alipay,weixinpay,applepay, share_sina,share_qq,share_qqzone,share_wx,share_wx_pyq,show_ad,show_qq_login, show_exchange, create_time) values(#{version},#{bitrate},#{frame},#{width},#{height},#{alipay},#{weixinpay},#{applepay}, #{shareSina},#{shareQq},#{shareQqzone},#{shareWx},#{shareWxPyq},#{showAd},#{showQQLogin},#{showExchange},#{createTime})")
    public void addSystemConfig(SystemConfig systemConfig);
	
	@DataSource("manageWrite")
    @Insert("delete from t_system_config where config_id = #{configId}")
    public void deleteSystemConfig(@Param("configId") String configId);
	
	@DataSource("manageRead")
	@Select("select config_id,version,bitrate,frame,width,height ,alipay,weixinpay,applepay, share_sina,share_qq,share_qqzone,share_wx,share_wx_pyq,show_ad,show_qq_login, show_exchange,create_time from t_system_config where config_id = #{configId}")
	@Results({ @Result(column = "config_id", property = "configId"),
		@Result(column = "share_sina", property = "shareSina"),
		@Result(column = "share_qq", property = "shareQq"),
		@Result(column = "share_qqzone", property = "shareQqzone"),
		@Result(column = "share_wx", property = "shareWx"),
		@Result(column = "share_wx_pyq", property = "shareWxPyq"),
		@Result(column = "show_ad", property = "showAd"),
		@Result(column = "show_qq_login", property = "showQQLogin"),
		@Result(column = "show_exchange", property = "showExchange"),
		@Result(column = "create_time", property = "createTime")})
	public SystemConfig getSystemConfig(@Param("configId") String configId);
	
	@DataSource("manageRead")
	@Select("select config_id,version,bitrate,frame,width,height ,alipay,weixinpay, applepay, share_sina,share_qq,share_qqzone,share_wx,share_wx_pyq,show_ad,show_qq_login, show_exchange,create_time from t_system_config where version = #{version}")
	@Results({ @Result(column = "config_id", property = "configId"),
		@Result(column = "share_sina", property = "shareSina"),
		@Result(column = "share_qq", property = "shareQq"),
		@Result(column = "share_qqzone", property = "shareQqzone"),
		@Result(column = "share_wx", property = "shareWx"),
		@Result(column = "share_wx_pyq", property = "shareWxPyq"),
		@Result(column = "show_ad", property = "showAd"),
		@Result(column = "show_exchange", property = "showExchange"),
		@Result(column = "create_time", property = "createTime")})
	public SystemConfig getSystemConfigByVersion(@Param("version") String version);
	
	@DataSource("manageWrite")
	@Update("update t_system_config set version=#{version},bitrate=#{bitrate},frame=#{frame},width=#{width},height=#{height},alipay=#{alipay},weixinpay=#{weixinpay},applepay=#{applepay}, share_sina=#{shareSina},share_qq=#{shareQq},share_qqzone=#{shareQqzone},share_wx=#{shareWx},share_wx_pyq=#{shareWxPyq},show_ad=#{showAd},show_qq_login=#{showQQLogin},show_exchange=#{showExchange} where config_id=#{configId}")
	public void updateSystemConfig(SystemConfig systemConfig);

	@SuppressWarnings("unchecked")
	public static class Provider {

		public String getSystemConfigListByParam(Map<String, Object> params) {
			Map<String, String> paramMap = (Map<String, String>) params
					.get("paramMap");
			String sql = "select config_id,version,bitrate,frame,width,height ,alipay,weixinpay,applepay, share_sina,share_qq,share_qqzone,share_wx,share_wx_pyq,show_ad,show_qq_login, show_exchange,create_time from t_system_config ";
			if (paramMap!=null&&paramMap.size()>0) {
				sql += " where 1=1 ";
				for (Map.Entry<String, String> entry : paramMap.entrySet()) {
					sql += " and `" + entry.getKey() + "`" + "=" + "#{paramMap."
							+ entry.getKey() + "}";
				}
			}
			return sql + " order by create_time asc limit #{start},#{count}";
		}
	}
}