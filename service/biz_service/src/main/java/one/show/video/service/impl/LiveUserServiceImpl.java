package one.show.video.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.video.dao.LiveUserMapper;
import one.show.video.domain.LiveUser;
import one.show.video.domain.ReturnList;
import one.show.video.service.LiveUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiveUserServiceImpl implements LiveUserService{


	@Autowired
	private LiveUserMapper liveUserMapper;
	
	@Override
	public void save(LiveUser liveUser) throws ServiceException {
		try {
			liveUserMapper.save(liveUser);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
	@Override
	public ReturnList<LiveUser> getLiveListByUid(Long uid, String sort, Map<String, String> condition, Integer start,
			Integer count) throws ServiceException {

		try {
			List<LiveUser>  idList = liveUserMapper.findLiveUserListByUid(uid, sort, condition,  start, count);
			
			int number = liveUserMapper.findCountByUid(uid, condition);
			
			ReturnList<LiveUser> list = new ReturnList<LiveUser>();
			
			list.setObjects(idList);
			list.setCount(number);
			
			return list;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public Integer getLiveCountByUid(Long uid, Map<String, String> condition) throws ServiceException{
		try {
			return liveUserMapper.findCountByUid(uid, condition);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public LiveUser findLiveUser(long uid, long liveId) throws ServiceException {
		return liveUserMapper.findLiveUser(uid, liveId);
	}

	@Override
	public void updateLiveUser(long uid, long liveId,Map<String, String> updateContent) throws ServiceException {
		try {
			liveUserMapper.updateLiveUser(uid, liveId, updateContent);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
