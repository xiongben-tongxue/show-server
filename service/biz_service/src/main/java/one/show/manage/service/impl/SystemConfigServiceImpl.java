package one.show.manage.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.SystemConfigMapper;
import one.show.manage.domain.SystemConfig;
import one.show.manage.service.SystemConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemConfigServiceImpl implements SystemConfigService{
	
	@Autowired
	SystemConfigMapper systemConfigMapper;


	@Override
	public SystemConfig getSystemConfig(String configId)
			throws ServiceException {
		try {
			return systemConfigMapper.getSystemConfig(configId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateSystemConfig(SystemConfig systemConfig)
			throws ServiceException {
		try {
			systemConfigMapper.updateSystemConfig(systemConfig);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<SystemConfig> getSystemConfigList(Map<String, String> paramMap,int start,int count) throws ServiceException {
		try {
			return systemConfigMapper.getSystemConfigList(paramMap, start, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void addSystemConfig(SystemConfig systemConfig)
			throws ServiceException {
		try {
			systemConfigMapper.addSystemConfig(systemConfig);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void deleteSystemConfig(String configId) throws ServiceException {
		try {
			systemConfigMapper.deleteSystemConfig(configId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public SystemConfig getSystemConfigByVersion(String version)
			throws ServiceException {
		try {
			return systemConfigMapper.getSystemConfigByVersion(version);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}