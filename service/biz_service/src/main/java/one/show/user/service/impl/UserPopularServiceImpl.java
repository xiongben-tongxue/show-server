
package one.show.user.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.user.dao.UserPopularNoMapper;
import one.show.user.domain.UserPopular;
import one.show.user.service.UserPopularService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei  2016年6月10日 下午6:28:04
 *
 * 
 */
@Component
public class UserPopularServiceImpl implements UserPopularService {

	@Autowired
	UserPopularNoMapper userPopularNoMapper;
	
	@Override
	public List<UserPopular> findListByUid(long uid) throws ServiceException {
		try {
			return userPopularNoMapper.findListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	
	}

	@Override
	public void save(UserPopular userPopular) throws ServiceException {

		try {
			userPopularNoMapper.save(userPopular);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(long uid, long popularNo) throws ServiceException {

		try {
			userPopularNoMapper.delete(uid, popularNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateStatus(long uid, long popularNo, int status)
			throws ServiceException {
		try {
			userPopularNoMapper.updateStatus(uid, popularNo, status);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

}


