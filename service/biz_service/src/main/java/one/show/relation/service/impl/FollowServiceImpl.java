/**
 * 
 */
package one.show.relation.service.impl;

import java.util.ArrayList;
import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.relation.dao.FollowMapper;
import one.show.relation.domain.Follow;
import one.show.relation.service.FollowService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei 2015年8月5日 下午9:09:02
 *
 */

@Component
public class FollowServiceImpl implements FollowService {
	
	private static final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);
	
	
	@Autowired
	private FollowMapper followMapper;
	

	/* (non-Javadoc)
	 * @see one.show.relation.service.FollowService#findListByUid(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Follow> findListByUid(Long uid, Integer start, Integer count)throws ServiceException {
		try{
			return followMapper.findListByUid(uid, start, count);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see one.show.relation.service.FollowService#save(one.show.relation.domain.Follow)
	 */
	
	
	@Override
	public void save(final Follow follow) throws ServiceException {
		
		try{
			followMapper.save(follow);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.relation.service.FollowService#delete(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void delete(final Long uid, final Long fid) throws ServiceException {

		
		try{
			followMapper.delete(uid, fid);
		}catch(Exception e){
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Boolean> isFollowed(Long currentUser, List<Long> uids) {
		List<Long> found = followMapper.isFollowed(currentUser, uids);
		List<Boolean> result = new ArrayList<>();
		for (Long uid : uids) {
			if (found != null && found.contains(uid)) {
				result.add(true);
			}
			else {
				result.add(false);
			}
		}
		return result;
	}

	@Override
	public Integer findFollowCountByUid(Long uid) throws ServiceException{
		Integer count = 0;
		try {
			count = followMapper.findFollowCountByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return count;
	}

	@Override
	public List<Boolean> isFans(Long currentUser, List<Long> uids) {
		
		List<Long> found = followMapper.isFans(currentUser, uids);
		List<Boolean> result = new ArrayList<>();
		for (Long uid : uids) {
			if (found != null && found.contains(uid)) {
				result.add(true);
			}
			else {
				result.add(false);
			}
		}
		return result;
	
	}

}


