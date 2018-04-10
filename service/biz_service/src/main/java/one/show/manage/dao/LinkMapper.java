package one.show.manage.dao;

import com.souyu.shard.annotation.DataSource;

import one.show.manage.domain.Link;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LinkMapper {
	@DataSource("manageRead")
	@Select("select * from t_links")
	List<Link> findLinkList();
}
