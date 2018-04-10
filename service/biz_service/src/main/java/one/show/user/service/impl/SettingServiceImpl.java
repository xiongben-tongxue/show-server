package one.show.user.service.impl;

import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.dao.SettingMapper;
import one.show.user.domain.Setting;
import one.show.user.service.SettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhangyihu 2015-08-04
 * 
 */
@Component("settingService")
public class SettingServiceImpl implements SettingService {
	
	@Autowired
	SettingMapper settingMapper;
	
	@Override
	public void setSwitch(String uid,Map<String, String> paramMap) throws ServiceException{
		try {
			settingMapper.setSwitch(uid, paramMap);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
	@Override
	public Setting getSettingByUid(String uid){
		return settingMapper.getSettingByUid(uid);
	}
}
