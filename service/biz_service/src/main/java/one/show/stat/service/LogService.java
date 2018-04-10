
package one.show.stat.service;

import one.show.common.exception.ServiceException;
import one.show.stat.domain.Log;

/**
 * @author Haliaeetus leucocephalus  2016年7月3日 下午6:01:58
 *
 * 
 */

public interface LogService {

	public void save(Log log) throws ServiceException;
}


