
package one.show.user.service;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.manage.domain.RobotSendGiftRatio;
import one.show.user.domain.Robot;

/**
 * @author Haliaeetus leucocephalus  2016年6月15日 下午12:50:12
 *
 * 
 */

public interface RobotService {

	public void save(Robot robot)throws ServiceException;
	
	public List<Robot> findList() throws ServiceException;

	public void saveSendGiftRatio(RobotSendGiftRatio robotSendGiftRatio) throws ServiceException;
	
	public List<RobotSendGiftRatio> findSendGiftRatioList() throws ServiceException;
}


