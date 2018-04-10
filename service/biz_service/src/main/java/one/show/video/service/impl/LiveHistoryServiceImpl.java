package one.show.video.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.ParallelHandler;
import one.show.common.exception.ServiceException;
import one.show.video.dao.LiveHistoryMapper;
import one.show.video.domain.LiveHistory;
import one.show.video.service.LiveHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiveHistoryServiceImpl implements LiveHistoryService {


	@Autowired
	private LiveHistoryMapper liveHistoryMapper;
	
	
	@Override
	public void save(LiveHistory liveHistory) throws ServiceException {
		try {
			liveHistoryMapper.save(liveHistory);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}


	@Override
	public List<LiveHistory> findLiveHistoryListByIds(List<Long> ids) {

		return new ParallelHandler<Long, LiveHistory>() {

			@Override
			public LiveHistory handle(Long item) {
				return liveHistoryMapper.findById(item);
			}
		}.execute(ids);
		
	}


	@Override
	public LiveHistory findLiveHistoryListById(Long liveId) throws ServiceException{
		
		try {
			return liveHistoryMapper.findById(liveId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public void update(long liveId, Map<String, String> updateContent) throws ServiceException{
		
		try {
			liveHistoryMapper.update(liveId, updateContent);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	
}
