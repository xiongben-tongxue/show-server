package one.show.manage.service.impl;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.VersionControlMapper;
import one.show.manage.domain.VersionControl;
import one.show.manage.service.VersionControlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VersionControlServiceImpl implements VersionControlService{
	@Autowired
	VersionControlMapper versionControlMapper;
	@Override
	public VersionControl findVersionControl(int agentType)
			throws ServiceException {
		VersionControl versionControl = null;
		try {
			versionControl=versionControlMapper.findVersionControlByAgentType(agentType);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return versionControl;
	}
}
