package one.show.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import one.show.common.Adapter;
import one.show.common.Constant;
import one.show.common.Constant.LIVE_END_REASON;
import one.show.common.Constant.LIVE_STATUS;
import one.show.common.Constant.LIVE_STREAM_STATUS;
import one.show.common.Constant.STATUS;
import one.show.common.Constant.USER_AGENT;
import one.show.common.Fetch;
import one.show.common.JacksonUtil;
import one.show.common.Utils;
import one.show.common.cache.LocalCache;
import one.show.common.client.redis.JedisUtil;
import one.show.common.exception.ServiceException;
import one.show.common.im.IMUtils;
import one.show.common.local.HeaderParams;
import one.show.common.local.XThreadLocal;
import one.show.manage.thrift.view.AdvertisementView;
import one.show.service.ManageService;
import one.show.service.StatService;
import one.show.service.UserService;
import one.show.service.VideoService;
import one.show.stat.thrift.view.VideoStatView;
import one.show.user.thrift.view.UserView;
import one.show.util.CityUtil;
import one.show.utils.IPUtil;
import one.show.video.thrift.view.LiveHistoryView;
import one.show.video.thrift.view.LiveView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tencent.service.PayService;

@Controller
@RequestMapping("/live") 
public class VideoApi extends BaseApi {

	private static final Logger log = LoggerFactory.getLogger(VideoApi.class);
	

	@Autowired
	private VideoService videoService;
	@Autowired
	private UserService userService;
	@Autowired
	private StatService statService;
	@Autowired
	private PayService payService;
	@Autowired
	private ManageService manageService;
	
	
	/**
	 * 删除回放视频
	 * @return
	 */
	@RequestMapping(value = "/delete_history")
	@ResponseBody
	public Map<String, String> deleteHistory(@RequestParam(required = false, value = "liveId") Long liveId) {
		// 当前登陆用户
		Long uid = XThreadLocal.getInstance().getCurrentUser();
		if (uid == null) {
			return error("2019");
		}
		
		LiveHistoryView liveView = null;
		try {
			liveView = videoService.getLiveHistoryById(liveId);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		
		if (liveView != null && liveView.getUid() == uid){
			try {
				videoService.deleteHistory(uid, liveId);
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
		
		}
		return success();
	}
	
	/**
	 * 判断用户直播状态
	 * @return
	 */
	@RequestMapping(value = "/status")
	@ResponseBody
	public Map<String, String> status(@RequestParam(required = false, value = "uid") Long uid,
			@RequestParam(required = false, value = "liveid") Long liveid) {
		
		// 当前登陆用户
		Long current = XThreadLocal.getInstance().getCurrentUser();
		if (current == null) {
			return error("2019");
		}
		
		long target = current;
		if (uid != null){
			target = uid;
		}
		LiveView inLive = null;
		try {
			inLive = videoService.getInLiveByUid(target);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		
		Map map = new HashMap();
		
		if (inLive != null){
			map.put("status", LIVE_STATUS.IN.ordinal());
			map.put("stream_status", inLive.getStreamStatus());
			map.put("liveId", inLive.getLiveId());
			map.put("streamName", inLive.getStreamName());
			map.put("rtmp", inLive.getRtmp());
			map.put("roomId", inLive.getLiveId());
			map.put("cdnType", inLive.getCdnType());
			map.put("share_addr", Adapter.getShareAddr4Live(inLive.getLiveId()));
			map.put("receive", getReceive(current));
		}else{
			map.put("status", LIVE_STATUS.END.ordinal());
			map.put("stream_status", LIVE_STREAM_STATUS.END.ordinal());
			map.put("liveId", "");
			map.put("streamName", "");
			map.put("rtmp", "");
			map.put("roomId", "");
			map.put("cdnType", "");
			map.put("share_addr", "");
			map.put("receive", getReceive(current));
			
			if(liveid!=null){
				VideoStatView videoStatView = null;
				try {
					videoStatView = statService.findVideoStatByVid(liveid);
				} catch (ServiceException e) {
					log.error(e.getMessage(), e);
				}
				
				LiveView liveView=null;
				try {
					liveView = videoService.getLiveById(liveid);
				} catch (ServiceException e) {
					log.error(e.getMessage(), e);
				}
				if(liveView!=null){
					int trueValue = 1;
					int total = Math.round(trueValue*1.5f)+Math.round(liveView.getRobotNumber()*1.1f);
					int liked = 0;
					if (videoStatView != null){
						trueValue = videoStatView.getViewed()+1;
						total = total+videoStatView.getGiftNum()+videoStatView.getLiked()+videoStatView.getShare();
						liked = videoStatView.getLiked();
					}
					
					log.info("truevalue:"+trueValue+",robotNumber:"+liveView.getRobotNumber()+",total:"+total);
				
					map.put("viewed", total<liveView.getViewed()?liveView.getViewed():total);
					map.put("liked", liked);
				}else{
					map.put("viewed", 0);
					map.put("liked", 0);
				}
			}
		}
		
		return success(map);
	}
	
	/**
	 * 创建直播
	 * @return
	 */
	@RequestMapping(value = "/init")
	@ResponseBody
	public Map<String, String> init(@RequestParam(required = false, value = "title") String title,
			@RequestParam(required = false, value = "topic") String topic,
			@RequestParam(required = false, value = "pos") Integer pos,
			HttpServletRequest request) {

		
		// 当前登陆用户
		UserView userView = getUserView();
		if (userView == null) {
			return error("2019");
		}
		
		if (pos == null){
			pos = 1;
		}
		
		if(isAllowedForbidden(userView.getId(), Constant.USER_AUTH_FORBID.LIVE.getIndex())){
			return error("5002");
		}
		
		LiveView inLive = null;
		try {
			inLive = videoService.getInLiveByUid(userView.getId());
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		
		if (inLive != null){
			return error("7004");
		}
		
		
		if (title == null || title.trim().equals("")){
			title = userView.getNickname()+"的直播";
		}else{
			if (Utils.getByteLength(title) > 40){
				return error("7005");
			}
		}
		
		HeaderParams headerParams = XThreadLocal.getInstance().getHeaderParams();
		
		LiveView liveView = new LiveView();
		liveView.setUid(userView.getId());
		liveView.setTitle(title);
		liveView.setTopic(topic);
		liveView.setIp(IPUtil.getIP(request));
		
		if (pos == 1){
			liveView.setCityName(getCity(request));
			
			if (headerParams.getLatitude() != null){
				liveView.setLatitude(Double.parseDouble(headerParams.getLatitude()));
			}
			if (headerParams.getLongitude() != null){
				liveView.setLongitude(Double.parseDouble(headerParams.getLongitude()));
			}
			
			 Map<String, Integer> cityMap = CityUtil.getCityNumDetail(
					 liveView.getLongitude(), liveView.getLatitude(), liveView.getIp());
			 if (cityMap != null){
				 liveView.setProvince(cityMap.get("province"));
				 liveView.setCity(cityMap.get("city"));
			 }
		}
		
		liveView.setDid(headerParams.getDeviceId());
		liveView.setPlatform(XThreadLocal.getInstance().getUserAgent().ordinal());
		try {
			JedisUtil.set(Constant.REDIS_KEY_USER_HEADER+liveView.getUid(), XThreadLocal.getInstance().getHeaderParams(), 21600);
			videoService.initLive(liveView);
			
			IMUtils.getInstants().createChatRoom(String.valueOf(liveView.getLiveId()), liveView.getTitle());
		} catch (ServiceException e) {
			return error();
		}
		
		Map map = new HashMap();
		map.put("liveId", liveView.getLiveId());
		map.put("streamName", liveView.getStreamName());
		map.put("rtmp", liveView.getRtmp());
		map.put("roomId", liveView.getLiveId());
		map.put("cdnType", liveView.getCdnType());
		map.put("share_addr", Adapter.getShareAddr4Live(liveView.getLiveId()));
		return success(map);
		
		
	}
	
	/**
	 * 分发直播流
	 * @return
	 */
	@RequestMapping(value = "/open")
	@ResponseBody
	public Map<String, String> open(@RequestParam(required = true, value = "streamName") String streamName) {

		// 当前登陆用户
		Long uid = XThreadLocal.getInstance().getCurrentUser();
		if (uid == null) {
			return error("2019");
		}
		
		Long liveId = 0l;
		
		if (streamName.split("_").length !=2){
			return error("7003");
		}else{
			try {
				liveId = Long.parseLong(streamName.split("_")[0]);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		}
		
		
		
		LiveView liveView = null;
		try {
			liveView = videoService.getLiveById(liveId);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		
		if (liveView != null && liveView.getUid() == uid){
			
			try {
				videoService.openStream(liveView);
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
				return error();
			}
		}
		
		return success();
	}
	
	
	/**
	 * 直播流中断
	 * @return
	 */
	@RequestMapping(value = "/break")
	@ResponseBody
	public Map<String, String> breakStream(@RequestParam(required = true, value = "streamName") String streamName) {

		// 当前登陆用户
		Long uid = XThreadLocal.getInstance().getCurrentUser();
		if (uid == null) {
			return error("2019");
		}
		
		Long liveId = 0l;
		
		if (streamName.split("_").length !=2){
			return error("7003");
		}else{
			try {
				liveId = Long.parseLong(streamName.split("_")[0]);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
		}
		
		
		
		LiveView liveView = null;
		try {
			liveView = videoService.getLiveById(liveId);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		
		if (liveView != null && liveView.getUid() == uid){
			try {
				videoService.breakStream(liveView);
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
		
		}
		return success();
	}
	
	/**
	 * 关闭直播
	 * @return
	 */
	@RequestMapping(value = "/close")
	@ResponseBody
	public Map<String, String> close(@RequestParam(required = true, value = "liveId") Long liveId) {

		// 当前登陆用户
		UserView user = getUserView();
		if (user == null) {
			return error("2019");
		}
		
		if (liveId == null){
			return error("1002");
		}
		
		LiveView liveView = null;
		try {
			liveView = videoService.getLiveById(liveId);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		
		if (liveView == null){
			return error("7001");
		}
		
		//自己关闭
		if (user.getId() == liveView.getUid()){
			liveView.setReason(LIVE_END_REASON.NORMAL.ordinal());
		}else{
			//巡管关闭
			if (user.isAdmin == 1){
				liveView.setReason(LIVE_END_REASON.AUDIT.ordinal());
			}else{
				return error("7002");
			}
		}
		
		
		
		try {
			videoService.endLive(liveView);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return error();
		}
	
		VideoStatView videoStatView = null;
		try {
			videoStatView = statService.findVideoStatByVid(liveView.getLiveId());
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		
		Map map = new HashMap();
		
		int trueValue = videoStatView == null?1:videoStatView.getViewed()+1;
		int total = Math.round(trueValue*1.5f)+Math.round(liveView.getRobotNumber()*1.1f);
		if(videoStatView!=null){
			total+=videoStatView.getGiftNum()+videoStatView.getLiked()+videoStatView.getShare();
		}
		log.info("truevalue:"+trueValue+",robotNumber:"+liveView.getRobotNumber()+",total:"+total);
		map.put("viewed", total<liveView.getViewed()?liveView.getViewed():total);
		map.put("liked", videoStatView == null?0:videoStatView.getLiked());

		
		return success(map);
	}
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping(value = "/getUserList")
	@ResponseBody
	public Map<String, String> getUserList(@RequestParam(required = true, value = "roomId") Long roomId,
			@RequestParam(value = "cursor", required = false) Integer cursor,
			@RequestParam(value = "count", required = false) Integer count) {

		// 当前登陆用户
		UserView user = getUserView();
		if (user == null) {
			return error("2019");
		}
		
		if (roomId == null){
			return error("1002");
		}
		
		if (cursor==null) {
			cursor = 0;
		}
		if (count==null || count < 0 || count>20) {
			count = 20;
		}
		
		LiveView live = null;
		try {
			live = videoService.getLiveById(roomId);
		} catch (ServiceException e) {
			log.error("get liveview error.",e);
		}
		if(live==null){
			return error("7001");
		}
		int next_cursor = cursor + count;
		
		List<UserView> all = null;
		
		try {
		  	final LiveView liveView = live;
	        all = new LocalCache<List<UserView>>() {
	
	            @Override
	            public List<UserView> getAliveObject() throws Exception {
	            	List<Long> ids = IMUtils.getInstants().queryChatRoomUsers(liveView.getLiveId()+"");
	    			log.info("room user size======="+ids.size()+",master uid========"+liveView.getUid());
	    			int total = videoService.getRoomTotal(ids.size(),liveView.getRobotNumber());
	    			return videoService.getRoomUserList(liveView,ids,total);
	    			
	            }
	        }.put(60 * 2, "userlist_live_"+live.getLiveId());
        } catch (Exception e) {
        	log.error("getRoomUserList error",e);
        }
		  
		
		List<Map<String, Object>> userList = new ArrayList<Map<String,Object>>();
		if(cursor<0){
			Map map = new HashMap();
			map.put("userList", userList);
			map.put("next_cursor", -1);
			map.put("total", live.getViewed());
			return success(map);
		}
		
		if(all!=null && all.size()>cursor){
			int max = all.size()<=next_cursor?all.size():next_cursor;
			for (int i = cursor; i < max; i++) {
				UserView uv = all.get(i);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("uid", uv.getId());
				map.put("profileImg", uv.getProfileImg());
				map.put("fanLevel", uv.getFanLevel());
				userList.add(map);
			}
		}
		log.info("getUserList size:"+userList.size()+",start="+cursor);
		if(all==null||userList.size()<count){
			next_cursor=-1;
		}
		
		Map map = new HashMap();
		map.put("userList", userList);
		map.put("next_cursor", next_cursor);
		map.put("total", live.getViewed());
		return success(map);
	}
	
	/**
	 * 根据ID刷新直播信息
	 * @param ids 逗号分隔
	 * @return
	 */
	@RequestMapping(value = "/listInfo")
	@ResponseBody
	public Map<String, Object> infos(
			@RequestParam(value = "ids", required = true) String ids,
			HttpServletRequest request
	){
		Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		if (currentUser == null) {
			return error("2019");
		}
		
		if (ids == null){
			return error("1002");
		}
		
		List<Long> idList = new ArrayList<Long>();
		String[] idArr = ids.split(",");
		for(String id : idArr){
			idList.add(Long.parseLong(id));
		}
		
		List<LiveView> list = null;
//		try {
//			list = videoService.getLiveByIds(idList);
//		} catch (ServiceException e) {
//			return error();
//		}
		
		Integer platformValue = null;
		Enum platform = XThreadLocal.getInstance().getUserAgent();
		if(platform.ordinal()!=USER_AGENT.UNKNOW.ordinal()){
			platformValue = platform.ordinal();
		}
		List<Map<String, Object>> result = getListData(list, IPUtil.getIP(request),platformValue);
		
		Map map = new HashMap();
		map.put("list", result);
		map.put("expire_time", 15);
				
		return success(map);
	}
	
	/**
	 * 首页定时刷新前5个接口
	 * @param sort
	 * @param cursor
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/topList")
	@ResponseBody
	public Map<String, Object> topList(HttpServletRequest request){
		Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		if (currentUser == null) {
			return error("2019");
		}
		List<LiveView>  list = null;
		/*
		String sort = "viewed";
		
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("vod_status", String.valueOf(STATUS.ENABLED.ordinal()));
		condition.put("status", String.valueOf(LIVE_STATUS.IN.ordinal()));
		
		try {
			list = videoService.getLiveList(condition, sort, 0, 5);
//			list = videoService.getLiveList4Square(sort, 0, 5);
		} catch (ServiceException e) {
			return error();
		}
		*/
		
		
		try {
			list = new LocalCache<List<LiveView>>() {
				
	            @Override
	            public List<LiveView> getAliveObject() throws Exception {
	            	return videoService.getLiveList4HandSort(0, 5);
	    			
	            }
	        }.put(60 , "live_top_list");
		} catch (Exception e) {
			return error();
		}
		
		Integer platformValue = null;
		Enum platform = XThreadLocal.getInstance().getUserAgent();
		if(platform.ordinal()!=USER_AGENT.UNKNOW.ordinal()){
			platformValue = platform.ordinal();
		}
		List<Map<String, Object>> result = getListData(list, IPUtil.getIP(request),platformValue);
		
	
		Map map = new HashMap();
		map.put("list", result);
		map.put("expire_time", 5);
	
		return success(map);
	}
	
	
	
	/**
	 * 滑屏用，获取前后2条
	 * @param cursor
	 * @param from 来源 hot热门  time最新  follow关注
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/next")
	@ResponseBody
	public Map<String, Object> next(
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "vid", required = true) Long vid,
			HttpServletRequest request
	){
		Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		if (currentUser == null) {
			return error("2019");
		}
		
		if (vid == null){
			return error("1002");
		}
		
		
		List<LiveView> nextList = new ArrayList<LiveView>();
		
		if (from == null){
			from = "top";
		}
		
		if (from.equals("follow")){
			
			
		}else{
			String sort = "";
			if (from.equals("top")){
				sort = "viewed";
			}else{
				sort = "create_time";
			}
			
			Map<String, String> condition = new HashMap<String, String>();
			condition.put("vod_status", String.valueOf(STATUS.ENABLED.ordinal()));
			condition.put("status", String.valueOf(LIVE_STATUS.IN.ordinal()));
			List<LiveView>  list = null;
			try {
				list = videoService.getLiveList(condition, sort, 0, 100);
			} catch (ServiceException e) {
				return error();
			}
			
			for(int i=0 ; i < list.size(); i++){
				LiveView liveView = list.get(i);
				if (liveView.getLiveId() == vid){
					if (i > 0){
						nextList.add(list.get(i-1));
					}else{
						nextList.add(list.get(list.size()-1));
					}
					
					if (i == list.size() - 1){
						nextList.add(list.get(0));
					}else{
						nextList.add(list.get(i+1));
					}
					break;
				}
			}
			
		}
		
		
		Integer platformValue = null;
		Enum platform = XThreadLocal.getInstance().getUserAgent();
		if(platform.ordinal()!=USER_AGENT.UNKNOW.ordinal()){
			platformValue = platform.ordinal();
		}
		List<Map<String, Object>> result = getListData(nextList, IPUtil.getIP(request),platformValue);
		
		Map map = new HashMap();
		map.put("list", result);
		
		return success(map);
		
		
	}
	
	/**
	 * 
	 * @param sort hot热度  time时间 排序
	 * @param cursor
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "cursor", required = false) Integer cursor,
			@RequestParam(value = "count", required = false) Integer count,
			HttpServletRequest request
	){
		
		Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		if (currentUser == null) {
			return error("2019");
		}
		
		if (cursor == null || cursor < 0){
			cursor = 0;
		}
		if (count == null || count < 0){
			count = 20;
		}
		if (sort == null){
			sort = "create_time";
		}else if (sort.equals("top")){
			sort = "viewed";
		}else{
			sort = "create_time";
		}
		
//		Map ykMap = getYinKeList(cursor, count);
		
		List<LiveView>  list = null;
		if (sort.equals("viewed")){
			try {
				final int fcursor = cursor;
				final int fcount = count;
				list = new LocalCache<List<LiveView>>() {
					
		            @Override
		            public List<LiveView> getAliveObject() throws Exception {
		            	return videoService.getLiveList4HandSort(fcursor, fcount);
		    			
		            }
		        }.put(60 , "live_list_viewed_"+cursor+"_"+count);
			} catch (Exception e) {
				return error();
			}
		}else{
			final Map<String, String> condition = new HashMap<String, String>();
			condition.put("status", String.valueOf(LIVE_STATUS.IN.ordinal()));
			final String fsort = sort;
			final int fcursor = cursor;
			final int fcount = count;
			try {
				list = new LocalCache<List<LiveView>>() {
					
		            @Override
		            public List<LiveView> getAliveObject() throws Exception {
		            	return videoService.getLiveList(condition, fsort, fcursor, fcount);
		    			
		            }
		        }.put(60 , "live_list_"+sort+"_"+cursor+"_"+count);
			} catch (Exception e) {
				return error();
			}
		}
		
		
		
		Integer platformValue = null;
		Enum platform = XThreadLocal.getInstance().getUserAgent();
		if(platform.ordinal()!=USER_AGENT.UNKNOW.ordinal()){
			platformValue = platform.ordinal();
		}
		List<Map<String, Object>> result = getListData(list, IPUtil.getIP(request),platformValue);

//		List tmp = (ArrayList)ykMap.get("list");
//		tmp.addAll(0, result);
		
		int next_cursor = cursor + count;
		
		if (list == null || list.size() == 0) {
            next_cursor = -1;
		}
		
		Map map = new HashMap();
		map.put("list", result);
		map.put("next_cursor", next_cursor);
		map.put("count", count);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (cursor == 0){
			resultList = getAdvList();
		}
		map.put("adv_list", resultList);

		
		return success(map);
	}

	private List<Map<String, Object>> getListData(List<LiveView> list, String ip, Integer platform) {
		
		Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Long> uids = new ArrayList<Long>();
		
		if (list != null){
			for(LiveView liveView : list){
				Map<String, Object> map = new HashMap<String, Object>();
				//直播
				
				if(liveView.getStatus() == LIVE_STATUS.IN.ordinal()){
					
					/*
					if (liveView.getCdnType() == CDN.WANGSU.ordinal()){
						try {
							String url = new WCNGBSDK().ngbRequest(Adapter.getStreamAddr4Flv(liveView.getStreamName(), liveView.getCdnType()), ip, 2);
							map.put("stream_addr", url);
						} catch (Exception e) {
							map.put("stream_addr", Adapter.getStreamAddr4Flv(liveView.getStreamName(), liveView.getCdnType()));
						}
						
					}else{
						map.put("stream_addr", Adapter.getStreamAddr4Flv(liveView.getStreamName(), liveView.getCdnType()));
					}
					*/
					map.put("stream_addr", Adapter.getStreamAddr4Flv(liveView.getStreamName(), liveView.getCdnType()));
					map.put("share_addr", Adapter.getShareAddr4Live(liveView.getLiveId()));
				}else if (liveView.getStatus() == LIVE_STATUS.END.ordinal()){
					//回放
					map.put("stream_addr", liveView.getRecordUrl());
					map.put("share_addr", Adapter.getShareAddr4Vod(liveView.getLiveId()));
				}else{
					continue;
				}
				
				UserView userView = getUserView(liveView.getUid());

				if (userView == null){
					continue;
				}
				
				uids.add((Long) liveView.getUid());
				
				map.put("city", liveView.getCityName());
				map.put("id", liveView.getLiveId());
				map.put("vid", liveView.getLiveId());
				map.put("room_id", liveView.getLiveId());
				boolean roomAdmin = false;
				try {
					roomAdmin = userService.isRoomAdmin(liveView.getUid(), currentUser);
				} catch (ServiceException e) {
					log.error(e.getMessage(), e);
				}
				map.put("room_admin", roomAdmin?1:0);
				
				map.put("receive", getReceive(liveView.getUid()));
				map.put("online_users", liveView.getViewed());
				if(platform!=null&&platform==USER_AGENT.IOS.ordinal()){
					map.put("is_live", liveView.getStatus()+"");
				}else{
					map.put("is_live", liveView.getStatus());
				}
				map.put("stream_status", liveView.getStreamStatus());
				map.put("topic", liveView.getTopic());
				map.put("title", liveView.getTitle());
				map.put("cover", Adapter.getAvatar(userView.getProfileImg()));
				
				
				Map master = new HashMap();
				master.put("avatar" , Adapter.getAvatar(userView.getProfileImg()));
				master.put("nick_name", userView.getNickname());
				master.put("master_level", userView.getMasterLevel());
				master.put("fan_level", userView.getFanLevel());
				master.put("gender", userView.getGender());
				master.put("description", userView.getDescription());
				master.put("birth", userView.getAge());
				master.put("uid", userView.getId());
				master.put("pid", userView.getPopularNo());
				map.put("master", master);
				
				result.add(map);
			}
			


		}
		return result;
	}

	private Map getYinKeList(Integer cursor, Integer count) {
		List res = new ArrayList();
		Fetch fetch = new Fetch();
		String json = fetch.get("http://service.ingkee.com/api/live/simpleall?imsi=&uid=11590577&proto=5&idfa=41FBD56F-A4CA-49AB-AAA7-1B9791097D89&lc=0000000000000025&cc=TG0001&imei=&sid=20uRlmauouzU68Fm5zW5BPQ4JKceY5ubHCh9i1gu3pMEi2A6NJki3&cv=IK3.0.20_Iphone&devi=48e84496440b05e363aa374421ed326b9d77a823&conn=Wifi&ua=iPhone&idfv=99739E68-5241-4C8C-83D8-407C71259899&osversion=ios_9.200000&");
		if (json != null){
			Map result = JacksonUtil.readJsonToObject(Map.class, json);
			if (result != null){
				List<Map> list = (List)result.get("lives");
				
				int size = list.size();
				cursor = size - 10;
				int toIndex = cursor+count;
				if (toIndex >= list.size()){
					toIndex = list.size();
				}
				List<Map> sublist = list.subList(cursor, toIndex);
				count = sublist.size();
				
				for(Map live : sublist){
					
					Map creator = (Map)live.get("creator");
					Map my = new HashMap();
					
					my.put("receive", getReceive(753815397945442304L));
					my.put("city", live.get("city"));
					my.put("id", 754690550325706752L);
					my.put("vid", 754690550325706752L);
					my.put("room_id", 754690550325706752L);
					my.put("room_admin", 0);
					my.put("stream_addr", live.get("stream_addr"));
					my.put("share_addr", "http://www.xiubi.com/share/live/754689995905826816");
					my.put("online_users", live.get("online_users"));
					
					my.put("is_live", 1);
					my.put("topic", "");
					my.put("is_vip", 0);
					my.put("stream_status", 1);
					
					Map master = new HashMap();
					try {
						String url = URLEncoder.encode("http://img.meelive.cn/"+creator.get("portrait"),"utf-8");
						//直播封面
						my.put("cover", "http://image.scale.a8.com/imageproxy2/dimgm/scaleImage?url="+url+"&w=640&s=80&h=640&c=0&o=0");
						//头像
						master.put("avatar", "http://image.scale.a8.com/imageproxy2/dimgm/scaleImage?url="+url+"&w=640&s=80&h=640&c=0&o=0");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					master.put("nick_name", creator.get("nick"));
					master.put("master_level", creator.get("level"));
					master.put("fan_level", creator.get("level"));
					master.put("gender", 0);
					master.put("description", "kskskskssk");
					master.put("birth", "2010-07-08");
					master.put("uid", 753815397945442304L);
					master.put("pid", "23220761");
					my.put("master", master);
					res.add(my);
				}
			}
		}
		
		
		Map map = new HashMap();
		map.put("list", res);
		map.put("next_cursor", count<cursor?-1:cursor+count);
		map.put("count", count);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (cursor == 0){
			resultList = getAdvList();
		}
		map.put("adv_list", resultList);
		
		return map;
	}

	private List<Map<String, Object>> getAdvList() {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

		try {
			List<AdvertisementView> advs = new LocalCache<List<AdvertisementView>>() {
	
				@Override
				public List<AdvertisementView> getAliveObject() throws Exception {
					return manageService.findAdvertisementView();
				}
			}.put(60*30, "advs_list");
			
			if(advs!=null){
				for(AdvertisementView adv:advs){
					Map<String,Object> advMap = new HashMap<String,Object>();
					advMap.put("id",adv.getId());
					advMap.put("image_url",adv.getImg());
					advMap.put("link_url",adv.getUrl());
					advMap.put("link_title",adv.getTitle());
					resultList.add(advMap);
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return resultList;
	}
	
	
}