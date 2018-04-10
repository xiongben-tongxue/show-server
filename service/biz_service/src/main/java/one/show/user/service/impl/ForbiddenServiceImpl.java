package one.show.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.user.dao.UserForbiddenMapper;
import one.show.user.domain.Forbidden;
import one.show.user.service.ForbiddenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhangyihu 2015-08-18
 * 
 */
@Component("forbiddenService")
public class ForbiddenServiceImpl implements ForbiddenService {
	
	@Autowired
	UserForbiddenMapper forbiddenMapper;
	
	private static final Logger log = LoggerFactory.getLogger(ForbiddenServiceImpl.class);
	@Override
	public List<Forbidden> findForbiddenListByUid(long uid) throws ServiceException{
		try {
			return forbiddenMapper.findForbiddenListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
	
	@Override
	public List<Integer> getForbiddenActionListByUid(long uid) throws ServiceException{
		try {
			return forbiddenMapper.getForbiddenActionListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public void saveForbidden(Forbidden forbidden) throws ServiceException {
		try {
			forbiddenMapper.saveForbidden(forbidden);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public void deleteForbiddenByUid(long uid) throws ServiceException {
		try {
			forbiddenMapper.deleteForbiddenByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public void deleteForbiddenByUidAndAction(long uid, int action)
			throws ServiceException {
		try {
			forbiddenMapper.deleteForbiddenByUidAndAction(uid, action);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public boolean findForbiddenByActionAndUid(long uid, int action)
			throws ServiceException {
		boolean flag = false;
		try {
			Forbidden forbidden=forbiddenMapper.findForbiddenByActionAndUid(uid, action);
			if(forbidden!=null){
				flag=true;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return flag;
	}


	@Override
	public List<Boolean> findForbiddenListByUidsAndAction(List<Long> uids,
			int action) throws ServiceException {
		if (uids==null||uids.size()==0) {
			return null;
		}
		List<Boolean> list = new ArrayList<Boolean>();
		for (long uid : uids) {
			boolean flag = false;
			try {
				Forbidden forbidden=forbiddenMapper.findForbiddenByActionAndUid(uid, action);
				if(forbidden==null){
					flag=true;
				}
				list.add(flag);
			} catch (Exception e) {
				list.add(true);
			}
		}
		return list;
	}
	
}