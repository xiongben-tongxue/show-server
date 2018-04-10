/**
 * 
 */
package one.show.stat.service.impl;

import one.show.common.ParallelHandler;
import one.show.common.exception.ServiceException;
import one.show.stat.dao.UserStatMapper;
import one.show.stat.domain.UserStat;
import one.show.stat.service.UserStatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午9:50:47
 *
 */

@Component
public class UserStatServiceImpl implements UserStatService {
	

	@Autowired
	private UserStatMapper userStatMapper;

	/* (non-Javadoc)
	 * @see one.show.stat.service.UserStatService#save(one.show.stat.domain.UserStat)
	 */
	@Override
	public void save(UserStat userStat) {
		userStatMapper.save(userStat);

	}

	/* (non-Javadoc)
	 * @see one.show.stat.service.UserStatService#updateById(java.lang.String, java.lang.String)
	 */
	@Override
	public void update(UserStat userStat) {
		userStatMapper.update(userStat);

	}


	@Override
	public List<UserStat> findByUids(List<Long> uids) {
		return new ParallelHandler<Long, UserStat>() {
			@Override
			public UserStat handle(Long item) {
				UserStat userStat = userStatMapper.findByUid(item);
				if (userStat == null)  {
					userStat = new UserStat();
					userStat.setUid(item);
				}
				return userStat;
			}
		}.execute(uids);
	}


	@Override
	public UserStat findByUid(Long uid) {
		return userStatMapper.findByUid(uid);
	}

	@Override
	public void delete(Long uid) throws ServiceException{
		
		try {
			userStatMapper.delete(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


}


