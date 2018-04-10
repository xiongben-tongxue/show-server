package one.show.manage.dao;

import java.util.List;

import one.show.manage.domain.Advertisement;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;
@Component
public interface AdvertisementMapper {
	
	
	@DataSource("manageRead")
	@Results({@Result(column = "create_time", property = "createTime")})
	@Select("select id,img,url,sort,create_time,title from t_advertisement  order by sort desc")
	public List<Advertisement> findAdvertisement();
	
}
