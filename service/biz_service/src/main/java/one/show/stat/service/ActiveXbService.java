
package one.show.stat.service;

import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.stat.domain.ActiveXbStat;

/**
 * @author Haliaeetus leucocephalus  2016年10月20日 下午5:31:31
 *
 * 
 */

public interface ActiveXbService {
	
	public ActiveXbStat findByUidAndTime(Long uid, Integer time) throws ServiceException;
	
	public void insert(ActiveXbStat activeXbStat) throws ServiceException;
	
	public void updateById(Long id, Map<String, String> updateContent) throws ServiceException;


}


