package one.show.manage.dao;

import java.util.List;

import one.show.manage.domain.FanLevel;
import one.show.manage.domain.MasterLevel;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

@Component
public interface LevelMapper {
	@DataSource("manageRead")
	@Select("select * from t_masterlevel order by exp asc")
	@Results({ @Result(column = "masterlevel", property = "masterLevel"), 
		@Result(column = "levelname", property = "levelName")})
	List<MasterLevel> findMasterLevelList();
	
	@DataSource("manageRead")
	@Select("select * from t_fanlevel order by exp asc")
	@Results({ @Result(column = "fanlevel", property = "fanLevel"), 
		@Result(column = "levelname", property = "levelName")})
	List<FanLevel> findFanLevelList();
}
