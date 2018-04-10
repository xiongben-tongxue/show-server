package one.show.user.service;

import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.domain.Setting;

/**
 * 
 * @author zhangyihu 20150804
 *
 */
public interface SettingService {
	
	public void setSwitch(String uid,Map<String, String> paramMap) throws ServiceException;
	
	public Setting getSettingByUid(String uid);
}
