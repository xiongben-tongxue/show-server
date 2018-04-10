
package one.show.stat.service.impl;

import one.show.common.exception.ServiceException;
import one.show.stat.dao.LogMapper;
import one.show.stat.domain.Log;
import one.show.stat.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年7月3日 下午6:03:10
 *
 * 
 */
@Component
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;
	
	@Override
	public void save(Log log) throws ServiceException{
		try {
			logMapper.save(log);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}


