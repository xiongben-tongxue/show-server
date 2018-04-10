package one.show.user.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.user.dao.DeviceForbiddenMapper;
import one.show.user.domain.DeviceForbidden;
import one.show.user.service.DeviceForbiddenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhangyihu 2015-08-18
 * 
 */
@Component("deviceForbiddenService")
public class DeviceForbiddenServiceImpl implements DeviceForbiddenService {
	
	@Autowired
	DeviceForbiddenMapper deviceForbiddenMapper;
	
	@Override
	public List<DeviceForbidden> findDeviceForbiddenListByDid(String did)
			throws ServiceException {
		try {
			return deviceForbiddenMapper.findDeviceForbiddenListByDid(did);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void saveForbidden(DeviceForbidden deviceForbidden)
			throws ServiceException {
		try {
			deviceForbiddenMapper.saveForbidden(deviceForbidden);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void deleteForbidden(String did) throws ServiceException {
		try {
			deviceForbiddenMapper.deleteForbidden(did);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
}