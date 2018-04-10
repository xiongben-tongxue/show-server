/**
 * 
 */
package one.show.manage.service.impl;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.OperateLogMapper;
import one.show.manage.domain.OperateLog;
import one.show.manage.service.OperateLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2015年11月13日 上午11:13:03
 *
 */

@Component
public class OperateLogServiceImpl implements OperateLogService {
	@Autowired
	private OperateLogMapper operateLogMapper;
	/* (non-Javadoc)
	 * @see one.show.manage.service.OperateLogService#save(one.show.manage.domain.OperateLog)
	 */
	@Override
	public void save(OperateLog operateLog) throws ServiceException {

		try {
			operateLogMapper.save(operateLog);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


