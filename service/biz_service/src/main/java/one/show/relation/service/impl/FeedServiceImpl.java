
package one.show.relation.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.TypeUtil;
import one.show.common.exception.ServiceException;
import one.show.relation.dao.FeedMapper;
import one.show.relation.domain.Feed;
import one.show.relation.service.FeedService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei  2016年6月7日 下午4:54:38
 *
 * 
 */
@Component
public class FeedServiceImpl implements FeedService {
	
	private static final Logger log = LoggerFactory.getLogger(FeedServiceImpl.class);
	
	@Autowired
	private FeedMapper feedMapper;

	@Override
	public List<Feed> findListByUid(Long uid, Map<String, String> condition, Integer start, Integer count)
			throws ServiceException {
		
		try{
			return feedMapper.findListByUid(uid, condition, start, count);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void save(Feed feed) throws ServiceException {

		try{
			 feedMapper.save(feed);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Long uid, Long fid, String point)
			throws ServiceException {

		try{
			 feedMapper.delete(uid, fid, point);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(Long uid, Long fid, String point,
			Map<String, String> updateContent) throws ServiceException {
		log.info("update feed.uid="+uid+",fid="+fid+",point="+point+",content="+updateContent);
		try{
			 feedMapper.update(uid, fid, point, updateContent);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}

}


