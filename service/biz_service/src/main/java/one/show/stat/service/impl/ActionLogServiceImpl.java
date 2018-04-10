
package one.show.stat.service.impl;

import one.show.common.exception.ServiceException;
import one.show.stat.dao.ActionLogMapper;
import one.show.stat.domain.ActionLog;
import one.show.stat.service.ActionLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年8月18日 下午4:43:55
 *
 * 
 */
@Component
public class ActionLogServiceImpl implements ActionLogService {

	@Autowired
	private ActionLogMapper actionLogMapper;
	
	
	@Override
	public void save(ActionLog log) throws ServiceException {

		try {
			actionLogMapper.save(log);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


