
package one.show.stat.service.impl;

import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.stat.dao.ActiveXbStatMapper;
import one.show.stat.domain.ActiveXbStat;
import one.show.stat.service.ActiveXbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年10月20日 下午5:33:58
 *
 * 
 */
@Component
public class ActiveXbServiceImpl implements ActiveXbService {

	@Autowired
	private ActiveXbStatMapper actionXbStatMapper;
	
	
	@Override
	public ActiveXbStat findByUidAndTime(Long uid, Integer time)
			throws ServiceException {
		
		try {
			return actionXbStatMapper.findByUidAndTime(uid, time);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	
	}

	@Override
	public void insert(ActiveXbStat stat) throws ServiceException {

		try {
			actionXbStatMapper.insert(stat);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void updateById(Long id, Map<String, String> updateContent)
			throws ServiceException {

		try {
			actionXbStatMapper.updateById(id, updateContent);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


