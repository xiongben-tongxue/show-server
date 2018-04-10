/**
 * 
 */
package one.show.manage.dao;

import one.show.manage.domain.OperateLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

import com.souyu.shard.annotation.DataSource;

/**
 * @author Haliaeetus leucocephalus 2015年11月13日 上午11:06:45
 *
 */

@Component
public interface OperateLogMapper {
	
	@DataSource("manageWrite")
	@Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_operate_log (operator, `desc`, operate_time, ip) values (#{operator},  #{desc}, #{operateTime},#{ip})")
	public void save(OperateLog operateLog);

}


