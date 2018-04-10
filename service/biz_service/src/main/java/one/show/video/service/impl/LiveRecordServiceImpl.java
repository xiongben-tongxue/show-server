package one.show.video.service.impl;

import java.util.List;

import one.show.common.TimeUtil;
import one.show.video.dao.LiveRecordMapper;
import one.show.video.domain.LiveRecord;
import one.show.video.service.LiveRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiveRecordServiceImpl implements LiveRecordService{
	
	@Autowired
	private LiveRecordMapper liveRecordMapper;

	@Override
	public LiveRecord findById(long id) {
		return liveRecordMapper.findById(id);
	}

	@Override
	public List<LiveRecord> findByLiveid(long liveid) {
		return liveRecordMapper.findByLiveid(liveid);
	}

	@Override
	public int save(LiveRecord record) {
		return liveRecordMapper.insertLiveRecord(record);
	}

	@Override
	public void updateStatusByLiveId(long liveid, int status) {
		int updateTime = TimeUtil.getCurrentTimestamp();
		liveRecordMapper.updateStatusByLiveId(liveid, status,updateTime);
	}
	
	@Override
	public void updatePersistentIdByLiveId(long liveid,int format, String persistentId, int status) {
		int updateTime = TimeUtil.getCurrentTimestamp();
		liveRecordMapper.updatePersistentIdByLiveId(liveid,format, persistentId,status,updateTime);
	}

	@Override
	public List<LiveRecord> findByStatus(int status) {
		return liveRecordMapper.findByStatus(status);
	}

	@Override
	public void deleteLiveRecord(long id) {
		liveRecordMapper.deleteLiveRecord(id);
	}

	@Override
	public List<LiveRecord> findByPersistentId(String persistentId) {
		return liveRecordMapper.findByPersistentId(persistentId);
	}
	
}
