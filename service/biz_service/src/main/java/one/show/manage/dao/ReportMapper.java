package one.show.manage.dao;

import one.show.manage.domain.Report;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DAO;
import com.souyu.shard.annotation.DataSource;

@Component
@DAO(catalog = "manage")
public interface ReportMapper {
	@DataSource("manageWrite")
	@Insert("insert into t_report (uid,content,contact,create_time) values(#{uid},#{content},#{contact}, #{createTime})")
	@Options(useGeneratedKeys=false)
	public void saveReport(Report report);
}
