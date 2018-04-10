
package one.show.manage.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.LevelMapper;
import one.show.manage.domain.FanLevel;
import one.show.manage.domain.MasterLevel;
import one.show.manage.service.LevelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年5月27日 下午4:23:26
 *
 * 
 */
@Component
public class LevelServiceImpl implements LevelService {
	
	@Autowired
	private LevelMapper levelMapper;

	@Override
	public List<MasterLevel> findMasterLevelList() throws ServiceException {
		try {
			return levelMapper.findMasterLevelList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<FanLevel> findFanLevelList() throws ServiceException {
		
		try {
			return levelMapper.findFanLevelList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


