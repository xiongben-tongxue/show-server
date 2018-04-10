/**
 * 
 */
package one.show.manage.service;

import one.show.common.exception.ServiceException;
import one.show.manage.domain.OperateLog;

/**
 * @author Haliaeetus leucocephalus 2015年11月13日 上午11:11:59
 *
 */
public interface OperateLogService {

	public void save(OperateLog operateLog) throws ServiceException;
	
}


