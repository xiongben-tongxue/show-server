package one.show.manage.dao;

import one.show.manage.domain.VersionControl;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

@Component
public interface VersionControlMapper {
	@DataSource("manageRead")
	@Results({ @Result(column = "coercion_version", property = "coercionVersion"), 
		@Result(column = "advice_version", property = "adviceVersion"),
		@Result(column = "download_url", property = "downloadUrl"),
		@Result(column = "agent_type", property = "agentType") })
	 @Select("select id,coercion_version,advice_version,download_url,agent_type,title,msg from t_version_control where agent_type = #{agentType}")
	 public VersionControl findVersionControlByAgentType(@Param("agentType") int  agentType);
}
