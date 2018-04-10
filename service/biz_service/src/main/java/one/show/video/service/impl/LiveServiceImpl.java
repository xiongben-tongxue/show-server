package one.show.video.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.Adapter;
import one.show.common.Constant;
import one.show.common.DateCalculateUtil;
import one.show.common.JacksonUtil;
import one.show.common.Constant.LIVE_ACTION;
import one.show.common.Constant.LIVE_END_REASON;
import one.show.common.Constant.LIVE_STATUS;
import one.show.common.Constant.LIVE_STREAM_STATUS;
import one.show.common.Constant.STATUS;
import one.show.common.Constant.STAT_ACTION;
import one.show.common.client.redis.JedisUtil;
import one.show.common.exception.ServiceException;
import one.show.common.im.ChatRoomMessage;
import one.show.common.im.IMUtils;
import one.show.common.im.MessageFactory;
import one.show.common.local.HeaderParams;
import one.show.common.local.XThreadLocal;
import one.show.common.mq.Publisher;
import one.show.common.mq.Queue;
import one.show.video.dao.LiveMapper;
import one.show.video.domain.Live;
import one.show.video.domain.LiveHistory;
import one.show.video.domain.LiveUser;
import one.show.video.service.LiveHistoryService;
import one.show.video.service.LiveService;
import one.show.video.service.LiveUserService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.pay.thrift.iface.PayServiceProxy;
import one.show.stat.thrift.iface.StatServiceProxy;
import one.show.stat.thrift.view.VideoStatView;
import one.show.user.thrift.iface.UserServiceProxy;
import one.show.user.thrift.view.UserForbiddenView;

@Component
public class LiveServiceImpl implements LiveService {
	
	private static final Logger log = LoggerFactory.getLogger(LiveServiceImpl.class);

	@Autowired
	private LiveMapper liveMapper;
	
	@Autowired
	private LiveHistoryService liveHistoryService;
	
	@Autowired
	private LiveUserService liveUserService;
	
	@Autowired
	private UserServiceProxy.Iface userServiceClientProxy;
	
	@Autowired
	private PayServiceProxy.Iface payServiceClientProxy;
	
	@Autowired
	private StatServiceProxy.Iface statServiceClientProxy;
	
	

	@Override
	public void save(Live live) throws ServiceException {
		try {
			liveMapper.save(live);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void deleteByUidAndStatus(Long uid, Integer status) throws ServiceException {

		try {
			liveMapper.deleteByUidAndStatus(uid, status);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Live findInLiveByUid(Long uid) throws ServiceException {
		try {
			return liveMapper.findInLiveByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Live findByLiveId(Long liveId) throws ServiceException {
		try {
			return liveMapper.findById(liveId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void update(Long liveId, Map<String, String> updateContent)
			throws ServiceException {
		try {
			liveMapper.update(liveId, updateContent);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void startLive(Live live) throws ServiceException {
		int now = (int) (System.currentTimeMillis() / 1000);
		
		Map<String, String> liveParams = new HashMap<String, String>();
		liveParams.put("status", String.valueOf(LIVE_STATUS.IN.ordinal()));
		liveParams.put("stream_status", String.valueOf(LIVE_STREAM_STATUS.IN.ordinal()));
		liveParams.put("stream_time", String.valueOf(now));
		liveParams.put("start_time", String.valueOf(now));
		liveParams.put("vod_status", String.valueOf(STATUS.ENABLED.ordinal()));
		
		
		update(live.getLiveId(), liveParams);
		
		try {
			
			Map<String, String> userParams = new HashMap<String, String>();
			userParams.put("islive", String.valueOf(LIVE_STATUS.IN.ordinal()));
			userParams.put("last_livetime", String.valueOf(now));
			
			userServiceClientProxy.updateUserById(live.getUid(), userParams);
			
		} catch (TException e) {
			throw new ServiceException(e);
		}
		
		//房间广播
		try {
			IMUtils.getInstants().publishChatroomMessage(String.valueOf(live.getLiveId()), MessageFactory.createBeginMessage(live.getUid(), Adapter.getStreamAddr4Flv(live.getStreamName(), live.getCdnType())));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		
		//粉丝推送
		try {

		 	Map map = new HashMap();
			map.put("uid", live.getUid());
			map.put("action", LIVE_ACTION.START.toString());
			map.put("vid", live.getLiveId());
			map.put("time", (int) (System.currentTimeMillis() / 1000));
			
			Publisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.LIVE);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void resumeLive(Live live) throws ServiceException {

		int now = (int) (System.currentTimeMillis() / 1000);
		Map<String, String> liveParams = new HashMap<String, String>();
		liveParams.put("stream_status", String.valueOf(LIVE_STREAM_STATUS.RESUME.ordinal()));
		liveParams.put("stream_time", String.valueOf(now));
	
		//房间广播
		try {
			IMUtils.getInstants().publishChatroomMessage(String.valueOf(live.getLiveId()), MessageFactory.createBeginMessage(live.getUid(), Adapter.getStreamAddr4Flv(live.getStreamName(), live.getCdnType())));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		update(live.getLiveId(), liveParams);
		
		
	}

	@Override
	public void endLive(Live live) throws ServiceException {

		int now = (int)(System.currentTimeMillis()/1000);
		Map<String, String> liveParams = new HashMap<String, String>();
		liveParams.put("status", String.valueOf(LIVE_STATUS.END.ordinal()));
		liveParams.put("reason", String.valueOf(live.getReason()));
		liveParams.put("stream_status", String.valueOf(LIVE_STREAM_STATUS.END.ordinal()));
		liveParams.put("vod_status", String.valueOf(STATUS.DISABLE.ordinal()));
		
		int end_time = now;
		if (live.getReason() == LIVE_END_REASON.EXCEPTION.ordinal()){
			end_time = live.getStreamTime();
		}
		
		liveParams.put("end_time", String.valueOf(end_time));
		update(live.getLiveId(), liveParams);
		

		
		LiveHistory liveHistory = new LiveHistory();
		BeanUtils.copyProperties(live, liveHistory);
		
		LiveUser liveUser = new LiveUser();
		//5分钟以上的直播为有效直播
		boolean isValid = end_time - live.getStartTime() >= 5*60;
		liveUser.setStatus(isValid?STATUS.ENABLED.ordinal():STATUS.DISABLE.ordinal());
		liveHistory.setStatus(isValid?STATUS.ENABLED.ordinal():STATUS.DISABLE.ordinal());

		liveUser.setLiveId(live.getLiveId());
		liveUser.setSnapshot(live.getSnapshot());
		liveUser.setUid(live.getUid());
		liveUser.setViewed(live.getViewed());
		liveUser.setStreamName(live.getStreamName());
		liveUser.setCdnType(live.getCdnType());
		liveUser.setCreateTime(now);
		liveUserService.save(liveUser);
		

		liveHistory.setReason(live.getReason());
		liveHistory.setCreateTime(now);
		liveHistory.setEndTime(end_time);
		liveHistoryService.save(liveHistory);
		//直播历史在live表里先保存几个月
//		deleteById(live.getLiveId());

		
		Map<String, String> userParams = new HashMap<String, String>();
		userParams.put("islive", String.valueOf(LIVE_STATUS.END.ordinal()));
		
		try {
			
			userServiceClientProxy.updateUserById(live.getUid(), userParams);
		} catch (TException e) {
			throw new ServiceException(e);
		}
		
		//禁播
		if (live.getReason() == LIVE_END_REASON.FORBIDDEN.ordinal()){
			try {
				 UserForbiddenView forbiddenView = new UserForbiddenView();
	             forbiddenView.setUid(live.getUid());
	             int forbiddenTime = (int)(DateCalculateUtil.addMonth(new Date(), 1).getTime()/1000);
	             forbiddenView.setExpireTime(forbiddenTime);
	             forbiddenView.setForbiddenby("");
	             forbiddenView.setAction(Constant.USER_AUTH_FORBID.LIVE.getIndex());
	             forbiddenView.setTime((int)(System.currentTimeMillis()/1000));
	             userServiceClientProxy.saveForbiddenView(forbiddenView);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		}
		
		VideoStatView videoStatView = null;
		try {
			videoStatView = statServiceClientProxy.findVideoStatByVid(live.getLiveId());
		} catch (TException e) {
			log.error(e.getMessage(), e);
		}
		
		//发送直播结束消息
		Map<String, Object> end = new HashMap<String, Object>();
		end.put("uid", live.getUid());
		end.put("reason", live.getReason());
		
		
		int trueValue = videoStatView == null?1:videoStatView.getViewed()+1;
		int total = Math.round(trueValue*1.5f)+Math.round(live.getRobotNumber()*1.1f);
		log.info("truevalue:"+trueValue+",robotNumber:"+live.getRobotNumber()+",total:"+total);
		end.put("viewed", total<live.getViewed()?live.getViewed():total);
		end.put("liked", videoStatView==null?0:videoStatView.getLiked());
		int receive = 0;
		try {
			receive = payServiceClientProxy.findTotalReceiveByVid(live.getLiveId());
		} catch (TException e) {
			log.error(e.getMessage(), e);
		}
		end.put("receive", receive);
		
		ChatRoomMessage msg = MessageFactory.createFinishMessage(end);
		try {
			IMUtils.getInstants().publishChatroomMessage(live.getLiveId()+"", msg);
		} catch (Exception e) {
			log.error("im pulic chartroomMsg error",e);
		}
		
				
		try {

		 	Map map = new HashMap();
			map.put("uid", live.getUid());
			map.put("action", LIVE_ACTION.END.toString());
			map.put("vid", live.getLiveId());
			map.put("time", (int) (System.currentTimeMillis() / 1000));
			
			Publisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.LIVE);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//视频统计
		try {

			
			HeaderParams headerParams = (HeaderParams)JedisUtil.get(Constant.REDIS_KEY_USER_HEADER+live.getUid());
			if (headerParams == null){
				headerParams = new HeaderParams();
				headerParams.setDeviceId(live.getDid());
				headerParams.setLatitude(String.valueOf(live.getLatitude()));
				headerParams.setLongitude(String.valueOf(live.getLongitude()));
			}
			JedisUtil.delete(Constant.REDIS_KEY_USER_HEADER+live.getUid());
		 	Map map = new HashMap();
			map.put("uid", live.getUid());
			map.put("action", STAT_ACTION.LIVE.toString());
			map.put("vid", live.getLiveId());
			map.put("header", headerParams);
			map.put("platform", live.getPlatform());
			map.put("live_time", end_time-live.getStartTime());
			map.put("time", (int) (System.currentTimeMillis() / 1000));
			
			Publisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.STAT);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		
	}

	@Override
	public void streamBreak(Live live) throws ServiceException {
		int now = (int)(System.currentTimeMillis()/1000);
		Map<String, String> updateContent = new HashMap<String, String>();
		updateContent.put("stream_time", String.valueOf(now));
		if (live.getStatus() == LIVE_STATUS.IN.ordinal()) {
			updateContent.put("stream_status", String.valueOf(LIVE_STREAM_STATUS.BREAKING.ordinal()));
			
			//房间广播
			try {
				IMUtils.getInstants().publishChatroomMessage(String.valueOf(live.getLiveId()), MessageFactory.createInterruptMessage(live.getUid()));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		}else if (live.getStatus() == LIVE_STATUS.END.ordinal()) {
			updateContent.put("stream_status", String.valueOf(LIVE_STREAM_STATUS.END.ordinal()));
		}
		
		update(live.getLiveId(), updateContent);
		
	}

	@Override
	public void deleteById(Long liveId) throws ServiceException {

		try {
			liveMapper.deleteByLiveId(liveId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Live> findLiveList(Map<String, String> condition, String sort, Integer start, Integer count) throws ServiceException {

		try {
			return liveMapper.findLiveList(condition, sort, start, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer findLiveListCount(Map<String, String> condition) throws ServiceException {
		try {
			return liveMapper.findLiveListCount(condition);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Live> findByLiveIds(List<Long> ids) throws ServiceException {
		
		List<Live> list = new ArrayList<Live>();
		try {
			for(Long id : ids){
				Live live = liveMapper.findById(id);
				if (live == null){
					live = new Live();
					live.setLiveId(id);
				}
				list.add(live);
			}
			
			return list;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Live> findLiveList4Square(String sort, Integer start, Integer count) throws ServiceException {
		
		try {
			return liveMapper.findLiveList4Square(sort, start, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	
	}
	
	@Override
	public Integer findLiveDurationByUid(Long uid, Integer startTime,
			Integer endTime) throws ServiceException{
		
		try {
			return liveMapper.findLiveDurationByUid(uid, startTime, endTime);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public Integer findLiveEffectiveDaysByUid(Long uid, Integer startTime,
			Integer endTime) throws ServiceException{
		try {
			return liveMapper.findLiveEffectiveDaysByUid(uid, startTime, endTime);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Live> findLiveList4HandSort(Integer start, Integer count)
			throws ServiceException {
		try {
			return liveMapper.findLiveList4HandSort(start, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	

}
