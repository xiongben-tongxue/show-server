package one.show.user.service.impl;

import one.show.common.exception.ServiceException;
import one.show.user.dao.PopularNoUserMapper;
import one.show.user.domain.PopularUser;
import one.show.user.service.PopularUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PopularUserServiceImpl implements PopularUserService {
	@Autowired
	PopularNoUserMapper popularUserMapper;
	@Override
	public PopularUser findPopularUserByPopularNo(long popularNo)
			throws ServiceException {
		PopularUser popularUser = null;
		try {
			popularUser=popularUserMapper.findPopularUserByPopularNo(popularNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return popularUser;
	}

	@Override
	public void savePopularUser(PopularUser popularUser)
			throws ServiceException {
		try {
			popularUserMapper.savePopularUser(popularUser);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void deletePopularUser(long popularNo) throws ServiceException {

		try {
			popularUserMapper.deletePopularUser(popularNo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
