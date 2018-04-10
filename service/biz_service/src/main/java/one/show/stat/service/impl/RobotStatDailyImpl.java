
package one.show.stat.service.impl;

import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.stat.dao.RobotStatDailyMapper;
import one.show.stat.domain.RobotStatDaily;
import one.show.stat.service.RobotStatDailyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年7月16日 下午6:13:08
 *
 * 
 */
@Component
public class RobotStatDailyImpl implements RobotStatDailyService {

	@Autowired
	private RobotStatDailyMapper robotStatDailyMapper;
	
	@Override
	public RobotStatDaily findByDate(int date) throws ServiceException {
		try {
			return robotStatDailyMapper.findByDate(date);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void save(RobotStatDaily robotStatDaily) throws ServiceException {

		try {
			robotStatDailyMapper.save(robotStatDaily);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateByDate(int date, Map<String, String> updateContent) throws ServiceException{

		try {
			robotStatDailyMapper.updateByDate(date, updateContent);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


