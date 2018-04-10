
package one.show.stat.service;

import one.show.common.exception.ServiceException;
import one.show.stat.domain.ActionLog;

/**
 * @author Haliaeetus leucocephalus  2016年7月3日 下午6:01:58
 *
 * 
 */

public interface ActionLogService {

	public void save(ActionLog log) throws ServiceException;
}


