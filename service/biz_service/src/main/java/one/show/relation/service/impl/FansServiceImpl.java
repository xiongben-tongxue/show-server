/**
 * 
 */
package one.show.relation.service.impl;

import one.show.common.AsyncHandler;
import one.show.common.Constant;
import one.show.common.client.redis.JedisUtil;
import one.show.common.exception.ServiceException;
import one.show.relation.dao.FansMapper;
import one.show.relation.domain.Fans;
import one.show.relation.service.FansService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangwei 2015年8月5日 下午9:02:32
 *
 */

@Component
public class FansServiceImpl implements FansService {
	
	private static final Logger log = LoggerFactory.getLogger(FansServiceImpl.class);
	  

	@Autowired
	private FansMapper fansMapper;
	
	
	/* (non-Javadoc)
	 * @see one.show.relation.service.FansService#findListByUid(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Fans> findListByUid(Long uid, Integer start, Integer count) throws ServiceException{
		try{
			return fansMapper.findListByUid(uid, start, count);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.relation.service.FansService#save(one.show.relation.domain.Fans)
	 */
	

	@Override
	public void save(final Fans fans) throws ServiceException {
		
		try{
			fansMapper.save(fans);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.relation.service.FansService#delete(one.show.relation.domain.Fans)
	 */
	@Override
	public void delete(final Long uid, final Long fid) throws ServiceException {
		
		
		try{
			fansMapper.delete(uid, fid);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Integer findFansCountByUid(Long uid) throws ServiceException {
		Integer count = 0;
		try {
			count = fansMapper.findFansCountByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return count;
	}

}


