
package one.show.user.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.RobotSendGiftRatioMapper;
import one.show.manage.domain.RobotSendGiftRatio;
import one.show.user.dao.RobotMapper;
import one.show.user.domain.Robot;
import one.show.user.service.RobotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年6月15日 下午12:50:55
 *
 * 
 */
@Component
public class RobotServiceImpl implements RobotService {

	@Autowired
	RobotMapper robotMapper;
	
	@Override
	public void save(Robot robot) throws ServiceException {
		try {
			robotMapper.save(robot);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<Robot> findList() throws ServiceException {
		try {
			return robotMapper.findList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	

	
	@Autowired
	RobotSendGiftRatioMapper robotSendGiftRatioMapper;
	
	
	@Override
	public void saveSendGiftRatio(RobotSendGiftRatio robotSendGiftRatio)
			throws ServiceException {
		try {
			robotSendGiftRatioMapper.save(robotSendGiftRatio);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<RobotSendGiftRatio> findSendGiftRatioList()
			throws ServiceException {
		
		try {
			return robotSendGiftRatioMapper.findList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


}


