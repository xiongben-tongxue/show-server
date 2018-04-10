package one.show.video.thrift.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.Constant;
import one.show.common.Constant.CDN_STREAM_STATUS;
import one.show.common.Constant.LIVE_STATUS;
import one.show.common.Constant.LIVE_STREAM_STATUS;
import one.show.common.exception.ServiceException;
import one.show.video.domain.Live;
import one.show.video.domain.LiveHistory;
import one.show.video.domain.LiveRecord;
import one.show.video.domain.LiveUser;
import one.show.video.domain.ReturnList;
import one.show.video.service.LiveHistoryService;
import one.show.video.service.LiveRecordService;
import one.show.video.service.LiveService;
import one.show.video.service.LiveUserService;
import one.show.video.thrift.iface.VideoServiceProxy;
import one.show.video.thrift.view.LiveHistoryView;
import one.show.video.thrift.view.LiveHistoryViewList;
import one.show.video.thrift.view.LiveRecordView;
import one.show.video.thrift.view.LiveUserView;
import one.show.video.thrift.view.LiveView;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.user.thrift.iface.UserServiceProxy;


@Component("videoServiceProxyImpl")
public class VideoServiceProxyImpl implements VideoServiceProxy.Iface {

	private static final Logger log = LoggerFactory.getLogger(VideoServiceProxyImpl.class);

	@Autowired
	private LiveService liveService;
	
	@Autowired
	private LiveHistoryService liveHistoryService;
	
	@Autowired
	private LiveUserService liveUserService;
	
	@Autowired
	private LiveRecordService liveRecordService;
	
	@Autowired
	private UserServiceProxy.Iface userServiceClientProxy;

	@Override
	public void initLive(LiveView liveView) throws TException {
		
		Live live = null;
		try {
			live = liveService.findInLiveByUid(liveView.getUid());
		} catch (Exception e) {
			throw new TException(e); 
		}
		
		if (live != null){
			throw new TException("已经在直播"); 
		}else{
			//删除之前的ready状态数据
			try {
				liveService.deleteByUidAndStatus(liveView.getUid(), LIVE_STATUS.READY.ordinal());
			} catch (ServiceException e) {
				throw new TException(e);
			}
		}
			
		try {
			live = new Live();
			BeanUtils.copyProperties(liveView, live);
			liveService.save(live);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}


	@Override
	public void endLive(long liveId, int reason) throws TException {
		
		Live live = null;
		try {
			 live = liveService.findByLiveId(liveId);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (live != null){
			if (live.getStatus() == LIVE_STATUS.READY.ordinal()){
				try {
					liveService.deleteById(liveId);
				} catch (ServiceException e) {
					throw new TException(e);
				}
			}else if (live.getStatus() == LIVE_STATUS.IN.ordinal()){
				live.setReason(reason);
				try {
					liveService.endLive(live);
				} catch (ServiceException e) {
					throw new TException(e);
				}
			}else{
//				throw new TException("直播已经结束");
			}
		}
		
	}

	@Override
	public void updateStreamStatus(String streamName, int status) throws TException {
		
		log.info("STREAM STATUS : streamName=" + streamName + ", status=" + status + "["
                + CDN_STREAM_STATUS.values()[status] + "]");
		
		Long liveId = null;
        try {
            liveId = Long.parseLong(streamName.split("_")[0]);
        } catch (NumberFormatException e) {
            throw new TException(e);
        }
        
        
        Live live = null;
        try {
            live = liveService.findByLiveId(liveId);

        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
        }
        if (live == null) {
            log.error("live is null , liveId=" + liveId + " streamName=" + streamName);
            throw new TException("直播不存在");
        }
        
        if (status == CDN_STREAM_STATUS.BREAK.ordinal()){
        	//流中断
        	if (live.getStatus() == LIVE_STATUS.IN.ordinal() && live.getStreamStatus() != LIVE_STREAM_STATUS.BREAKING.ordinal()) {
        		try {
        			
        			liveService.streamBreak(live);
        			
        			log.info("updateStreamStatus[BREAK] processed successfully [直播状态:" + LIVE_STATUS.values()[live.getStatus()]  + "],streamName=" + streamName +
                            ", streamStatus=" + live.getStreamStatus() +
                            " [" + LIVE_STREAM_STATUS.values()[live.getStreamStatus()] + "]");
        		} catch (ServiceException e) {
        			log.error(e.getMessage(), e);
        			throw new TException(e);
        		}
            } 
        	
        }else if (status == CDN_STREAM_STATUS.STOP.ordinal()){
        	//流结束, 暂不处理，定时任务会关闭BREAKING状态超过5分钟的直播
        	/*
        	if (live.getStatus() == LIVE_STATUS.IN.ordinal()) {
        		try {
        			
        			liveService.endLive(live);
        			
        			log.info("updateStreamStatus[STOP] processed successfully [直播状态:" + LIVE_STATUS.values()[live.getStatus()]  + "],streamName=" + streamName +
                            ", streamStatus=" + live.getStreamStatus() +
                            " [" + LIVE_STREAM_STATUS.values()[live.getStreamStatus()] + "]");
        		} catch (ServiceException e) {
        			log.error(e.getMessage(), e);
        			throw new TException(e);
        		}
            } 
            */
        	
        }else if (status == CDN_STREAM_STATUS.START.ordinal()){
        	if (live.getStatus() == LIVE_STATUS.READY.ordinal()){
        		//新直播
        		
        		try {
					liveService.startLive(live);
					
					log.info("updateStreamStatus[START] processed successfully [直播状态:" + LIVE_STATUS.values()[live.getStatus()] + "],streamName=" + streamName +
		                    ", streamStatus=" + live.getStreamStatus() +
		                    " [" + LIVE_STREAM_STATUS.values()[live.getStreamStatus()] + "]");
				} catch (ServiceException e) {
					log.error(e.getMessage(), e);
        			throw new TException(e);
				}
        		
        	}else if (live.getStatus() == LIVE_STATUS.IN.ordinal() && live.getStreamStatus() == LIVE_STREAM_STATUS.BREAKING.ordinal()){
        		//续播
        		try {
        			
        			liveService.resumeLive(live);
        			log.info("updateStreamStatus[START] processed successfully [直播状态:" + LIVE_STATUS.values()[live.getStatus()]  + "],streamName=" + streamName +
                            ", streamStatus=" + live.getStreamStatus() +
                            " [" + LIVE_STREAM_STATUS.values()[live.getStreamStatus()] + "]");
        			
        			
        		} catch (ServiceException e) {
        			log.error(e.getMessage(), e);
        			throw new TException(e);
        		}
        	}else{
        		log.error("直播状态错误["+live.getStatus()+"]：liveid[" + live.getLiveId()
                                        + "], streamName[" + live.getStreamName() + "], "
                                        + "livestatus[" + live.getStatus() + "], streamstatus["
                                        + live.getStreamStatus() + "]");
        	}
        }
        
		
	}


	@Override
	public LiveView findLiveById(long liveId) throws TException {
		LiveView liveView = new LiveView();
		
		Live live = null;
		try {
			live = liveService.findByLiveId(liveId);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (live == null){
			return null;
		}else{
			BeanUtils.copyProperties(live, liveView);
			return liveView;
		}
		
	}




	@Override
	public LiveView findInLiveByUid(long uid) throws TException {
		LiveView liveView = new LiveView();
		
		Live live = null;
		try {
			live = liveService.findInLiveByUid(uid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (live == null){
			return null;
		}else{
			BeanUtils.copyProperties(live, liveView);
			return liveView;
		}
	}



	@Override
	public int findLiveHistoryCountByUid(long uid, Map<String, String> condition)
			throws TException {
		try {
			return liveUserService.getLiveCountByUid(uid, condition);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public LiveHistoryViewList findLiveHistoryByUid(long uid, String sort,Map<String, String> condition, int start,
			int count) throws TException {

		LiveHistoryViewList liveHistoryViewList = new LiveHistoryViewList();
		List<LiveHistory> liveHistoryList = null;
		try {
			ReturnList<LiveUser> liveList = liveUserService.getLiveListByUid(uid, sort, condition, start, count);
			
			if (liveList != null){
				List<Long> idList = new ArrayList<Long>();
				for(LiveUser liveUser : liveList.objects){
					idList.add(liveUser.getLiveId());
				}
				
				liveHistoryViewList.count = liveList.count;
				liveHistoryList = liveHistoryService.findLiveHistoryListByIds(idList);
				
				if (liveHistoryList != null){
					liveHistoryViewList.liveHistoryViewList = new ArrayList<LiveHistoryView>();
					
					for(int i=0; i<liveHistoryList.size() ; i++){
						LiveHistory liveHistory = liveHistoryList.get(i);
						LiveUser liveUser = liveList.objects.get(i);
						
						LiveHistoryView liveHistoryView = new LiveHistoryView();
						BeanUtils.copyProperties(liveHistory, liveHistoryView);
						liveHistoryView.setStatus(liveUser.getStatus());
						liveHistoryView.setVodStatus(liveUser.getVodStatus());
						
						liveHistoryViewList.liveHistoryViewList.add(liveHistoryView);
					}
					
				}
			}
			
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		return liveHistoryViewList;
	}


	@Override
	public void updateLive(long liveId, Map<String, String> paramMap)
			throws TException {
		try {
			liveService.update(liveId, paramMap);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public List<LiveHistoryView> findLiveHistoryByIds(List<Long> ids)
			throws TException {
		
		List<LiveHistoryView> liveHistoryViewList = new ArrayList<LiveHistoryView>();
		
		List<LiveHistory> liveHistoryList = liveHistoryService.findLiveHistoryListByIds(ids);
		 
		if (liveHistoryList != null){
			liveHistoryViewList =  (List<LiveHistoryView>) CollectionUtils.collect(liveHistoryList, new Transformer<LiveHistory, LiveHistoryView>() {
				@Override
				public LiveHistoryView transform(LiveHistory liveHistory) {
					if (liveHistory == null){
						return new LiveHistoryView();
					}
					LiveHistoryView LiveHistoryView = new LiveHistoryView();
					BeanUtils.copyProperties(liveHistory, LiveHistoryView);
					return LiveHistoryView;
				}
			});
			return liveHistoryViewList;
		}else{
			return null;
		}
		
	}


	@Override
	public LiveHistoryView findLiveHistoryById(long liveId) throws TException {

		LiveHistoryView liveHistoryView = new LiveHistoryView();
		LiveHistory liveHistory = null;
		try {
			liveHistory = liveHistoryService.findLiveHistoryListById(liveId);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (liveHistory != null){
			BeanUtils.copyProperties(liveHistory, liveHistoryView);
			return liveHistoryView;
		}
		return null;
	}


	@Override
	public LiveUserView findLiveUser(long uid, long liveid) throws TException {
		LiveUser liveUser = null;
		try {
			liveUser = liveUserService.findLiveUser(uid, liveid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		if(liveUser!=null){
			LiveUserView liveUserView = new LiveUserView();
			BeanUtils.copyProperties(liveUser, liveUserView);
		}
		return null;
	}


	@Override
	public void updateLiveUser(long uid, long liveid,
			Map<String, String> updateContent) throws TException {
		try {
			liveUserService.updateLiveUser(uid, liveid, updateContent);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}



	@Override
	public int findLiveListCount(Map<String, String> condition) throws TException {
		Integer count = null;
		try {
			count = liveService.findLiveListCount(condition);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		if (count == null){
			return 0;
		}else{
			return count;
		}
	}
	
	
	@Override
	public List<LiveView> findLiveList(Map<String, String> condition,String sort, int start, int count)
			throws TException {
		
		List<Live> liveList = null;
		try {
			liveList = liveService.findLiveList(condition, sort, start, count);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (liveList != null){
			return (List<LiveView>) CollectionUtils.collect(liveList, new Transformer<Live, LiveView>() {
				@Override
				public LiveView transform(Live live) {
					if (live == null){
						return new LiveView();
					}
					LiveView liveView = new LiveView();
					BeanUtils.copyProperties(live, liveView);
					return liveView;
				}
			});
		}
		return null;
	
	}


	@Override
	public void updateLiveHistory(long liveid, Map<String, String> paramMap)
			throws TException {
		try {
			liveHistoryService.update(liveid, paramMap);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public LiveRecordView findById(long id) throws TException {
		LiveRecordView liveRecordView = null;
		
		try {
			LiveRecord record = liveRecordService.findById( id);
			if(record!=null){
				liveRecordView = new LiveRecordView();
				BeanUtils.copyProperties(record, liveRecordView);
			}
		} catch (Exception e) {
			log.error("findrecordbyid error.",e);
			throw new TException(e);
		}
		return liveRecordView;
	}


	@Override
	public List<LiveRecordView> findByLiveid(long liveid) throws TException {
		List<LiveRecordView> list = new ArrayList<LiveRecordView>();
		
		try {
			List<LiveRecord> records = liveRecordService.findByLiveid(liveid);
			if(records!=null&&records.size()>0){
				for(LiveRecord r:records){
					LiveRecordView liveRecordView = new LiveRecordView();
					BeanUtils.copyProperties(r, liveRecordView);
					list.add(liveRecordView);
				}
			}
		} catch (Exception e) {
			log.error("findrecordbyid error.",e);
			throw new TException(e);
		}
		return list;
	}


	@Override
	public int save(LiveRecordView record) throws TException {
		if(record==null){
			return -1;
		}
		LiveRecord r = new LiveRecord();
		BeanUtils.copyProperties(record, r);
		return liveRecordService.save(r);
	}


	@Override
	public void updateStatusByLiveId(long liveid, int status) throws TException {
		liveRecordService.updateStatusByLiveId(liveid, status);
	}


	@Override
	public List<LiveRecordView> findByStatus(int status) throws TException {
		List<LiveRecordView> list = new ArrayList<LiveRecordView>();
		
		try {
			List<LiveRecord> records = liveRecordService.findByStatus(status);
			if(records!=null&&records.size()>0){
				for(LiveRecord r:records){
					LiveRecordView liveRecordView = new LiveRecordView();
					BeanUtils.copyProperties(r, liveRecordView);
					list.add(liveRecordView);
				}
			}
		} catch (Exception e) {
			log.error("findrecordbyid error.",e);
			throw new TException(e);
		}
		return list;
	}


	@Override
	public void deleteLiveRecord(long id) throws TException {
		liveRecordService.deleteLiveRecord(id);
	}


	@Override
	public int finishRecord(long uid, long liveid, String recordUrl,String mp4Url,double duration)
			throws TException {
		LiveUser liveUser = null;
		try {
			liveUser = liveUserService.findLiveUser(uid, liveid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		log.info("finishRecord .liver:"+liveUser +",liveid="+liveid);
		int status = liveUser.getStatus();
		boolean isValid = duration<300?false:true;
		//直播未删除且长度达到5分钟
		boolean liveStatus = (status== Constant.STATUS.ENABLED.ordinal()) && isValid;
		log.info("liveid="+liveid+",liveuser status:"+status+",isvalid="+isValid+",result status="+liveStatus);
		Map<String,String> updateLiveMap  = new HashMap<String, String>();
		updateLiveMap.put("record_url", recordUrl);
		updateLiveMap.put("vod_status", Constant.STATUS.ENABLED.ordinal()+"");
		if(!liveStatus){
			updateLiveMap.put("status", Constant.LIVE_STATUS.INVALID.ordinal()+"");
		}
		updateLive(liveid, updateLiveMap);
		
		Map<String,String> updateHistoryMap  = new HashMap<String, String>();
		updateHistoryMap.put("record_url", recordUrl==null?"":recordUrl);
		if(mp4Url!=null){
			updateHistoryMap.put("mp4_url", mp4Url);
		}
//		updateHistoryMap.put("status", liveStatus?Constant.STATUS.ENABLED.ordinal()+"":Constant.STATUS.DISABLE.ordinal()+"");
		updateLiveHistory(liveid, updateHistoryMap);
		
		Map<String,String> paramMap  = new HashMap<String, String>();  
		paramMap.put("vod_status", Constant.STATUS.ENABLED.ordinal()+"");
//		paramMap.put("status", liveStatus?Constant.STATUS.ENABLED.ordinal()+"":Constant.STATUS.DISABLE.ordinal()+"");
		try {
			updateLiveUser(uid, liveid, paramMap);
		} catch (Exception e) {
			log.error("record updateLiveUser error",e);
		}
		return liveStatus?Constant.STATUS.ENABLED.ordinal():Constant.STATUS.DISABLE.ordinal();
	}


	@Override
	public List<LiveRecordView> findByPersistentId(String persistentId)
			throws TException {
		List<LiveRecordView> list = new ArrayList<LiveRecordView>();
		
		try {
			List<LiveRecord> records = liveRecordService.findByPersistentId(persistentId);
			if(records!=null&&records.size()>0){
				for(LiveRecord r:records){
					LiveRecordView liveRecordView = new LiveRecordView();
					BeanUtils.copyProperties(r, liveRecordView);
					list.add(liveRecordView);
				}
			}
		} catch (Exception e) {
			log.error("findByPersistentId error.",e);
			throw new TException(e);
		}
		return list;
	}


	@Override
	public void updatePersistentIdByLiveId(long liveid,int format, String persistentId, int status)
			throws TException {
		liveRecordService.updatePersistentIdByLiveId(liveid,format, persistentId,status);
	}


	@Override
	public List<LiveView> findLiveByIds(List<Long> ids) throws TException {

		List<LiveView> liveViewList = new ArrayList<LiveView>();
		
		List<Live> liveList = null;
		try {
			liveList = liveService.findByLiveIds(ids);
		} catch (ServiceException e) {
			
		}
		 
		if (liveList != null){
			liveViewList =  (List<LiveView>) CollectionUtils.collect(liveList, new Transformer<Live, LiveView>() {
				@Override
				public LiveView transform(Live live) {
					if (live == null){
						return new LiveView();
					}
					LiveView liveView = new LiveView();
					BeanUtils.copyProperties(live, liveView);
					return liveView;
				}
			});
			return liveViewList;
		}else{
			return null;
		}
		
	
	}


	@Override
	public List<LiveView> findLiveList4Square(String sort, int start, int count)
			throws TException {


		List<Live> liveList = null;
		try {
			liveList = liveService.findLiveList4Square(sort, start, count);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (liveList != null){
			return (List<LiveView>) CollectionUtils.collect(liveList, new Transformer<Live, LiveView>() {
				@Override
				public LiveView transform(Live live) {
					if (live == null){
						return new LiveView();
					}
					LiveView liveView = new LiveView();
					BeanUtils.copyProperties(live, liveView);
					return liveView;
				}
			});
		}
		return null;
	
	}


	@Override
	public int findLiveDurationByUid(long uid, int startTime, int endTime)
			throws TException {
		try {
			return liveService.findLiveDurationByUid(uid, startTime, endTime);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


	@Override
	public int findLiveEffectiveDaysByUid(long uid, int startTime, int endTime)
			throws TException {
		
		Integer count = null;
		try {
			count = liveService.findLiveEffectiveDaysByUid(uid, startTime, endTime);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		if (count == null){
			return 0;
		}else{
			return count;
		}
	
	}


	@Override
	public List<LiveView> findLiveList4HandSort(int start, int count)
			throws TException {
		
		List<Live> liveList = null;
		try {
			liveList = liveService.findLiveList4HandSort(start, count);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
		if (liveList != null){
			return (List<LiveView>) CollectionUtils.collect(liveList, new Transformer<Live, LiveView>() {
				@Override
				public LiveView transform(Live live) {
					if (live == null){
						return new LiveView();
					}
					LiveView liveView = new LiveView();
					BeanUtils.copyProperties(live, liveView);
					return liveView;
				}
			});
		}
		return null;
	
	}
}