package one.show.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import one.show.common.Adapter;
import one.show.common.Constant;
import one.show.common.Constant.FAMILY_TYPE;
import one.show.common.Constant.ITEM_MONEY;
import one.show.common.Constant.ITEM_PROP;
import one.show.common.Constant.ITEM_TYPE;
import one.show.common.Constant.LIVE_STATUS;
import one.show.common.Constant.STATUS;
import one.show.common.Constant.STAT_ACTION;
import one.show.common.Constant.THIRD_BIND_PUBLIC_STATUS;
import one.show.common.Constant.THIRD_DATA_TYPE;
import one.show.common.DESUtil;
import one.show.common.DateCalculateUtil;
import one.show.common.JacksonUtil;
import one.show.common.Loader;
import one.show.common.MD5;
import one.show.common.TimeUtil;
import one.show.common.Utils;
import one.show.common.exception.ReturnException;
import one.show.common.exception.ServiceException;
import one.show.common.im.IMUtils;
import one.show.common.local.XThreadLocal;
import one.show.common.mq.Publisher;
import one.show.common.mq.Queue;
import one.show.manage.thrift.view.FanLevelView;
import one.show.pay.thrift.view.StockView;
import one.show.service.DeviceService;
import one.show.service.FamilyService;
import one.show.service.LoginService;
import one.show.service.ManageService;
import one.show.service.PayService;
import one.show.service.RankService;
import one.show.service.RelationService;
import one.show.service.SearchService;
import one.show.service.SensitiveService;
import one.show.service.impl.EmailLoginServiceImpl;
import one.show.service.impl.PhoneLoginServiceImpl;
import one.show.service.impl.ThirdPartyLoginServiceImpl;
import one.show.stat.thrift.view.UserStatView;
import one.show.struc.LoginParam;
import one.show.struc.SinaUserParam;
import one.show.user.thrift.view.ContactView;
import one.show.user.thrift.view.FamilyView;
import one.show.user.thrift.view.ThirdBindView;
import one.show.user.thrift.view.ThirdDataView;
import one.show.user.thrift.view.UserView;
import one.show.video.thrift.view.LiveView;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Controller
// @Scope("prototype")
public class UserApi extends BaseApi {

	private static final Logger log = LoggerFactory.getLogger(UserApi.class);
	// private ConcurrentHashMap<String,Object> current = new
	// ConcurrentHashMap<String, Object>();
	@Autowired
	RelationService relationService;
	@Autowired
	ManageService manageService;
	@Autowired
	RankService rankService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	FamilyService familyService;
	@Autowired
	SensitiveService sensitiveService;
	@Autowired
	PayService payService;
	@Autowired
	SearchService searchService;
	private String[] thirdTypeArr = new String[] { "sina",  "qq", "weixin", "phone"};

	/**
	 * 个人资料
	 * 
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/getRyToken",method = RequestMethod.POST)
	@ResponseBody
	public Map getRongyunToken() {
		UserView userView = getUserView();
		String ryToken = IMUtils.getInstants().getToken(userView.getId()+"", userView.getNickname(), Adapter.getAvatar(userView.getProfileImg()));
		Map<String, String> updateContent = new HashMap<String, String>();
		updateContent.put("ry_token", ryToken);
		try {
			userService.updateUserById(userView.getId(), updateContent);
		} catch (ServiceException e) {
			log.error("update user error",e);
		}
		
		Map<String, String>  map = new HashMap<String, String>();
		map.put("ryToken", ryToken);
		return success(map);
	}

	/**
	 * 个人资料
	 * 
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/userInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map getUserInfo(
			@RequestParam(required = false, value = "user_id") final Long userid) {

		Map<String, Object> result = new HashMap<String, Object>();

		Long uid = XThreadLocal.getInstance().getCurrentUser();

		if (uid == null) {
			return error("2019");
		}
		
		Long target = uid;
		if (userid != null) {
			target = userid;
		}

		UserView userView = null;
		try {
			userView = userService.findUserById(target);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}

		if (userView == null) {
			return error("2005");
		}
		result.put("userid", userView.getId());
		result.put("nickname", userView.getNickname());
		result.put("popularNo", userView.getPopularNo());
		result.put("receive", getReceive(userView.getId()));
		result.put("isNew", userView.getIsNew());
		result.put("phoneNumber", userView.getPhoneNumber());
		result.put("profileImg", userView.getProfileImg());
		result.put("description", userView.getDescription());
		result.put("city", userView.getCity());
		result.put("constellation", userView.getConstellation());
		result.put("gender", userView.getGender());
		result.put("fanLevel", userView.getFanLevel());
		result.put("masterLevel", userView.getMasterLevel());
		result.put("lastLoginType", userView.getLastLoginType());
		result.put("islive", userView.getIslive());
		result.put("notifyConfig", userView.getNotifyConfig());
		result.put("is_vip",isVip(userView.getId()));
		
		//是否能提现
		result.put("enable_tx",1);
		if (userView.getFamilyId() > 0){
			FamilyView familyView = null;
			try {
				familyView = familyService.findFamilyById(userView.getFamilyId());
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
			if (familyView != null && familyView.getType().equals(FAMILY_TYPE.A.toString())){
				result.put("enable_tx",0);
			}
		}
		
		
		if(userid!=null && uid.longValue()!=userid.longValue()){
			try {
				UserView self = userService.findUserById(uid);
				if(self.getIslive()==LIVE_STATUS.IN.ordinal()){
					boolean roomAdmin = false;
					try {
						roomAdmin = userService.isRoomAdmin(uid,userid);
					} catch (ServiceException e) {
						log.error(e.getMessage(), e);
					}
					result.put("is_my_admin", roomAdmin?1:0);
				}
			} catch (ServiceException e	) {
				log.error("finduser error.",e);
			}
		}
		
		if(userView.getIslive()==LIVE_STATUS.IN.ordinal()){
			LiveView liveView = null;
			try {
				liveView = videoService.getInLiveByUid(target);
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
			if(liveView==null){
				result.put("islive", LIVE_STATUS.END.ordinal());
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("city", liveView.getCityName());
				map.put("id", liveView.getLiveId());
				map.put("room_id", liveView.getLiveId());
				map.put("stream_addr", Adapter.getStreamAddr4Flv(liveView.getStreamName(), liveView.getCdnType()));
				map.put("share_addr", Adapter.getShareAddr4Live(liveView.getLiveId()));
				map.put("online_users", liveView.getViewed());
				map.put("topic", liveView.getTopic());
				map.put("title", liveView.getTitle());
				map.put("cover", Adapter.getAvatar(userView.getProfileImg()));
				boolean roomAdmin = false;
				try {
					roomAdmin = userService.isRoomAdmin(liveView.getUid(), uid);
				} catch (ServiceException e) {
					log.error(e.getMessage(), e);
				}
				map.put("room_admin", roomAdmin?1:0);
				
				result.put("liveView", map);
			}
		}
		//
		
		Boolean isBlack = false;
		try {
			isBlack = userService.isBlack(uid, target);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		result.put("isBlack", isBlack?1:0);
		
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("item_type",String.valueOf(ITEM_TYPE.MONEY.getId()));
		List<StockView> stockGoldList = null;
		try {
			stockGoldList = payService.findStockByUidAndParam(target, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		int showCoinNum = 0;
		if (stockGoldList!=null&&stockGoldList.size()>0) {
			for(StockView stockView : stockGoldList){
				if (stockView.getItemId() == ITEM_MONEY.SHOWCOIN.getId()){
					showCoinNum =(int) (Math.round(stockView.getItemNumber()));
				}
			}
		}
		result.put("beikeNum", showCoinNum);
		//
		UserStatView statView = null;
		try {
			statView = statService.findUserStatByUid(target);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		Integer fans = 0;
		try {
			fans = relationService.findFansCount(target);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		result.put("fans", fans);
		
		Integer follow = 0;
		try {
			follow = relationService.findFollowCount(target);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		result.put("follow", follow);
		int videos = 0;
		try {
			Map<String, String> condition = new HashMap<String, String>();
			condition.put("status", String.valueOf(STATUS.ENABLED.ordinal()));
			condition.put("vod_status", String.valueOf(STATUS.ENABLED.ordinal()));
			condition.put("uid", String.valueOf(target));
			videos = videoService.getLiveListCount(condition);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		result.put("videos", videos);
		
		result.put("totalMasterValue", statView==null?0:((int)statView.getReceive()));
		result.put("totalFanValue", statView==null?0:((int)statView.getSpend()));
		
		FanLevelView currFanLevelView = getFanLevelView(userView.getFanLevel());
		FanLevelView fanLevelView = getFanLevelView(userView.getFanLevel()+1);
		int totalExp = 0;
		if(userView.isrobot==1){
			totalExp = (int)currFanLevelView.getExp();
			if(fanLevelView!=null){
				totalExp += (int)(System.currentTimeMillis()%(fanLevelView.getExp()-currFanLevelView.getExp()));
			}
		}else{
			totalExp = statView==null?0:((int)statView.getSpendExp());
		}
		result.put("totalExp", totalExp);
		result.put("toNextLevel", fanLevelView!=null? ((int)(fanLevelView.getExp()-totalExp)):0);
		
		boolean isFollowed = false;
		if(uid!=target){
			try {
				List<Boolean> list = relationService.isFollowed(uid, ImmutableList.of(target));
				if(list!=null && list.get(0).booleanValue()){
					isFollowed=true;
				}
			} catch (ServiceException e) {
				log.error("isfollowed error.",e);
			}
		}
		result.put("isFollowed", isFollowed);
		
		boolean isFans = false;
		if(uid!=target){
			try {
				List<Boolean> list = relationService.isFans(uid, ImmutableList.of(target));
				if(list!=null && list.get(0).booleanValue()){
					isFans=true;
				}
			} catch (ServiceException e) {
				log.error("isfans error.",e);
			}
		}
		result.put("isFans", isFans);
		
		
		//绑定情况
		List<ThirdDataView> thirdDataViewList = null;
		try {
			thirdDataViewList = userService.findThirdDataListByUid(target);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		List<String> binds = new ArrayList<String>();
		if(thirdDataViewList!=null){
			for(ThirdDataView tbv:thirdDataViewList){
				binds.add(tbv.getType());
			}
		}
		result.put("thirdBinds", binds);
		
		//守护榜
		List<Map<String, Object>> resultList = null;
        try {
        	resultList = rankService.findDefenderList(target, 0, 20);
        } catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	  
		List<String> imgs = new ArrayList<String>();
		if(resultList!=null){
			for(Map<String, Object> map:resultList){
				imgs.add((String)map.get("profileImg"));
				if (imgs.size() == 3){
					break;
				}
			}
		}
		if(imgs.size()<3){
			for (int i = imgs.size(); i < 3; i++) {
				imgs.add("");
			}
		}
		result.put("defendUserImgs", imgs);
		return success(result);
	}

	/**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	 * 获取某个第三方id是否已经被其他用户绑定
	 */
	@RequestMapping(value = "/get_userbind_status",method = RequestMethod.POST)
	@ResponseBody
	public Map getUserBindStatus(
			@RequestParam(required = true, value = "thirdType") String thirdType,
			@RequestParam(required = true, value = "thirdUserId") String thirdUserId) {

		// 当前登陆用户
		Long uid = XThreadLocal.getInstance().getCurrentUser();
		if (uid == null) {
			return error("2019");
		}
		UserView user = getUserView(uid);

		ThirdDataView thirdDataView = null;
		try {
			thirdDataView = userService.findThirdDataByTidAndType(thirdUserId,
					thirdType);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("thirdState",
				(thirdDataView == null || thirdDataView.getUid() == user
						.getId()) ? "0" : "1");
		return success(map);

	}

	/**
	 * 获取用户通讯录
	 */
	@RequestMapping(value = "/get_addressBook",method = RequestMethod.POST)
	@ResponseBody
	public Map getUserContactList(
			@RequestParam(required = true, value = "thirdType") String thirdType,
			@RequestParam(required = true, value = "thirdUserId") String thirdUserId) {

		// 当前登陆用户
		Long uid = XThreadLocal.getInstance().getCurrentUser();
		if (uid == null) {
			return error("2019");
		}

		UserView user = getUserView(uid);

		List<ContactView> ContactViewList = null;
		try {
			ContactViewList = userService.findContactListByUid(user.getId()+"");
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}

		Map<String, List<Map>> map = new HashMap<String, List<Map>>();
		List<Map> contactList = new ArrayList<Map>();
		for (ContactView contactView : ContactViewList) {
			Map<String, String> contactMap = new HashMap<String, String>();
			contactMap.put("get_addressBook", contactView.getName());
			contactMap.put("contactPhone", contactView.getNumber());
			contactList.add(contactMap);
		}

		map.put("addressBookList", contactList);
		return success(map);

	}

	/**
	 * 手机注册
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/reg",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reg(
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "tid", required = true) String tid,
			@RequestParam(value = "pwd", required = true) String pwd,
			@RequestParam(value = "verify_code", required = true) String verifyCode,HttpServletRequest request)
			throws ServiceException, ReturnException {
		if (!Arrays.asList("phone").contains(type))
			return error("1002");

		// 手机号，密码格式验证

		// 验证码是否正确
//		String verifyCodeExpected = verifyCodeService.getVerifyCode(tid, "reg",
//				verifyCode);
//		if (verifyCodeExpected == null || !verifyCodeExpected.equals("1")) {
//			return error("2014");
//		}


		ThirdDataView thirdDataView = new ThirdDataView();
		thirdDataView.setType("phone");
		thirdDataView.setToken(MD5.md5(pwd));
		thirdDataView.setTid(tid);
		
		UserView userView = new UserView();
		userView.setCity(getCity(request));

		userView = userService.registerUser(userView, thirdDataView);
		
		return success(getLoginOkData(userView));
	}

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/get_verified_code",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get_verified_code(
			@RequestParam(value = "phone_number", required = true) String phoneNumber,
			@RequestParam(value = "type", required = true) String type)
			throws ServiceException, ReturnException {
		Map<String, Object> result = new HashMap<>();

		if (!Arrays.asList("reg", "bind", "forget").contains(type))
			return error("1002");
		log.info("type="+type+":"+type.equals("reg")+":"+phoneNumber+":"+Constant.THIRD_DATA_TYPE.T_PHONE.getTypeName());
		if(type.equals("reg")){
			ThirdDataView thirdDataView = userService.findThirdDataByTidAndType(phoneNumber, Constant.THIRD_DATA_TYPE.T_PHONE.getTypeName());
			if(thirdDataView!=null){
				return error("2025");
			}
		}
		verifyCodeService.sendVerifyCode(phoneNumber, type);

		return success(result);
	}

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(
			@RequestParam(value = "third_type", required = true) final String thirdType,
			@RequestParam(value = "third_token", required = false) final String thirdToken,
			@RequestParam(value = "third_id", required = false) final String thirdId,
			@RequestParam(value = "pwd", required = false) final String pwd,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "third_nickname", required = false) String thirdNickname,
			@RequestParam(value = "third_sex", required = false) Integer thirdSex,
			@RequestParam(value = "third_headimage", required = false) String thirdHeadimage,
			@RequestParam(value = "third_userdesc", required = false) String thirdUserdesc,
			HttpServletRequest request, HttpServletResponse response) throws ReturnException, ServiceException {
	
			
			LoginParam loginParam = new LoginParam();
			loginParam.setThirdType(thirdType);
			loginParam.setThirdToken(thirdToken);
			loginParam.setThirdId(thirdId);
			loginParam.setPwd(pwd);
			loginParam.setPhone(phone);
			loginParam.setEmail(email);
			loginParam.setThirdNickname(thirdNickname);
			loginParam.setThirdSex(thirdSex);
			loginParam.setThirdHeadimage(thirdHeadimage);
			loginParam.setThirdUserdesc(thirdUserdesc);
			loginParam.setCity(getCity(request));
			
			LoginService loginService = null;
			
			if (thirdType.equals(THIRD_DATA_TYPE.T_PHONE.getTypeName())) {
				loginService = new PhoneLoginServiceImpl();
			} else if (thirdType.equals(THIRD_DATA_TYPE.T_EMAIL.getTypeName())) {
				loginService = new EmailLoginServiceImpl();
			}else {
				loginService = new ThirdPartyLoginServiceImpl();
			}
			
			
			UserView userView = loginService.login(loginParam, userService);
		
			Map<String, String> updatecontent = new HashMap<String, String>();
			updatecontent.put("last_login_type",thirdType);
			updatecontent.put("last_logintime", ""+(int)(System.currentTimeMillis()/1000));
			userService.updateUserById(userView.getId(), updatecontent);
			
			userService.updateUserDevice(userView);
		
			try {
	
			 	Map map = new HashMap();
				map.put("uid", userView.getId());
				map.put("action", STAT_ACTION.LOGIN.toString());
				map.put("header", XThreadLocal.getInstance().getHeaderParams());
				map.put("platform", XThreadLocal.getInstance().getUserAgent().ordinal());
				map.put("time", (int) (System.currentTimeMillis() / 1000));
				
				
				
				Publisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.STAT);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			
			return success(getLoginOkData(userView));
	}

	private Map<String, Object> getLoginOkData(UserView userView)
			throws ServiceException {
		
		Map<String, Object> result = new HashMap<>();
		
		result.put("userid", userView.getId());
		result.put("nickname", userView.getNickname());
		result.put("ry_token", userView.getRyToken());
		result.put("popularNo", userView.getPopularNo());
		result.put("isNew", userView.getIsNew());
		result.put("phoneNumber", userView.getPhoneNumber());
		result.put("isAdmin", userView.getIsAdmin());
		
		try {
			String token = new DESUtil().encryptToken(userView.getId()+"");
			result.put("token", token);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		result.put("profileImg", userView.getProfileImg());
		
		return result;
	}



	/**
	 * 
	 * @return
	 * @throws ReturnException 
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/forgetPwd")
	@ResponseBody
	public Map<String, Object> forgetPwd(
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
			@RequestParam(value = "verify_code", required = true) String verifyCode,
			@RequestParam(required = true, value = "new_pwd") String newPwd) throws ReturnException, ServiceException {
		Map<String, Object> result = new HashMap<>();
		
		try {
			ThirdDataView tdv = userService.findThirdDataByTidAndType(phoneNumber, "phone");
			if (tdv == null) {
				return error("2002");
			}
			UserView userView = userService.findUserById(tdv.getUid());
			// 验证码是否正确
			String verifyCodeExpected = verifyCodeService.getVerifyCode(userView.getPhoneNumber(), "forget",verifyCode);
			if (verifyCodeExpected==null || !verifyCodeExpected.equals("1")) {
				return error("2014");
			}

			tdv.setToken(MD5.md5(newPwd));
			Map<String, String> map = new HashMap<String, String>();
			map.put("token", MD5.md5(newPwd));

			userService.updateThirdData(tdv.getTid(), tdv.getType(), map);
		} catch (ServiceException e) {
			log.error("forgetPwt run error."+e);
			throw new ReturnException(e);
		}
		
		LoginParam loginParam = new LoginParam();
		loginParam.setPwd(newPwd);
		loginParam.setPhone(phoneNumber);
		
		LoginService loginService = new PhoneLoginServiceImpl();
		UserView userView = loginService.login(loginParam, userService);
		
		userService.updateUserDevice(userView);
	
		try {

		 	Map map = new HashMap();
			map.put("uid", userView.getId());
			map.put("action", STAT_ACTION.LOGIN.toString());
			map.put("time", (int) (System.currentTimeMillis() / 1000));
			
			Publisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.STAT);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return success(getLoginOkData(userView));
	}

	
	/**
	 *  更改密码
	 * 
	 * @param id
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping(value = "/updatePwd")
	@ResponseBody
	public Map updatePwd(@RequestParam(required = true, value = "old_pwd") String oldPwd,
			@RequestParam(required = true, value = "new_pwd") String newPwd) {
		try {
			Long uid = XThreadLocal.getInstance().getCurrentUser();
			if (uid == null) {
				return error("2019");
			}

			UserView userView = userService.findUserById(uid);
			if(StringUtils.isEmpty(userView.getPhoneNumber())){
				return error("2027");
			}
			
			ThirdDataView tdv = userService.findThirdDataByTidAndType(userView.getPhoneNumber(), "phone");
			if (tdv == null) {
				return error("2003");
			}
			if (!tdv.getToken().equals(MD5.md5(oldPwd))) {
				return error("2003");
			}
			tdv.setToken(MD5.md5(newPwd));
			Map<String, String> map = new HashMap<String, String>();
			map.put("token", MD5.md5(newPwd));

			userService.updateThirdData(tdv.getTid(), tdv.getType(), map);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(), e);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		return success();
	}
	
	/**
	 * 修改消息提醒
	 * 
	 * @param id
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping(value = "/updateNotifyConfig")
	@ResponseBody
	public Map updateNotifyConfig(@RequestParam(required = true, value = "notify_config") Integer notifyConfig) {
		Long uid = XThreadLocal.getInstance().getCurrentUser();
		if (uid == null) {
			return error("2019");
		}
		
		try {
			UserView userView = userService.findUserById(uid);
			Map<String, String> map = new HashMap<String, String>();
			map.put("notify_config", notifyConfig.toString());
			userService.updateUserById(uid, map);
		} catch (ServiceException e) {
			log.error("update notifyConfig error.",e);
		}
		return success();
	}

	

	/**
	 * 更新第三方token
	 * 
	 * @author zhangyihu 2015/08/07
	 */
	@RequestMapping(value = "/update_third_token")
	@ResponseBody
	public Map updateThirdToken(
			@RequestParam(required = true, value = "third_type") String thirdType,
			@RequestParam(required = true, value = "third_id") String thirdId,
			@RequestParam(required = true, value = "third_token") String thirdToken) {

		Long uid = XThreadLocal.getInstance().getCurrentUser();
		if (uid == null) {
			return error("2019");
		}

		ThirdDataView thirdDataView = null;
		try {

			thirdDataView = userService.findThirdDataByTidAndType(thirdId,
					thirdType);
			if (thirdDataView != null) {
				thirdDataView.setToken(thirdToken);
				Map<String, String> map = new HashMap<String, String>();
				map.put("token", thirdToken);
				userService.updateThirdData(thirdId, thirdType, map);
			} else {
				return error("0");
			}
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			return error("0");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("thirdState", "1");
		return success(map);

	}

	/**
	 * 第三方解绑
	 * 
	 * @param thirdType
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/unbind_account")
	@ResponseBody
	public Map unbindAccount(
			@RequestParam(required = true, value = "third_type") String thirdType,
			@RequestParam(required = false, value = "user_id") Long userId)
			throws ServiceException {

		Long uid = XThreadLocal.getInstance().getCurrentUser();

		if (uid == null) {
			return error("2019");
		}

		try {
			List<ThirdDataView> thirdDataViewList = userService
					.findThirdDataListByUid(uid);
			if (thirdDataViewList != null && thirdDataViewList.size() > 1) {

				for (ThirdDataView thirdDataView : thirdDataViewList) {
					if (thirdType.equals(thirdDataView.getType())) {
						userService.deleteThirdData(thirdDataView.getTid(),
								thirdType);
					}
				}

			} else {
				return error("2022");
			}
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		return success();
	}

	/**
	 * 第三方绑定
	 * 
	 * @param thirdType
	 * @param userId
	 * @param thirdId
	 * @param thirdToken
	 * @return
	 */
	@RequestMapping("/bind_account")
	@ResponseBody
	public Map bindAccount(
			@RequestParam(required = true, value = "third_type") String thirdType,
			@RequestParam(required = true, value = "third_id") String thirdId,
			@RequestParam(required = false, value = "third_token") String thirdToken,
			@RequestParam(required = false, value = "verification_code") String verificationCode,
			@RequestParam(required = false, value = "passwd") String passwd) {
		
		UserView userView = getUserView();
		if (userView == null) {
			return error("2019");
		}
		if (verificationCode != null) {
			try {
				String verifyCodeExpected = verifyCodeService.getVerifyCode(
						thirdId, "bind", verificationCode);
				if (verifyCodeExpected != null) {
					if (!verifyCodeExpected.equals("1")) {
						return error("2015");
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		try {
			ThirdDataView thirdDataViews = userService
					.findThirdDataByTidAndType(thirdId, thirdType);

			if (thirdDataViews != null) {
				if (!"phone".equals(thirdType)){
					return error("2006");
				}
			}
			String pwd = MD5.md5(passwd);
			if (THIRD_DATA_TYPE.T_PHONE.getTypeName().equals(thirdType)) {
				userService.deleteThirdDataByUidAndType(userView.getId(), thirdType);
				String token = pwd;


				int now = TimeUtil.getCurrentTimestamp();
				
				ThirdDataView thirdDataView = new ThirdDataView();
				thirdDataView.setTid(thirdId);
				thirdDataView.setToken(token);
				thirdDataView.setUid(userView.getId());
				thirdDataView.setType(thirdType);
				thirdDataView.setCreateTime(now);
				userService.saveThirdData(thirdDataView);
				
				Map<String, String> updateContent = new HashMap<String, String>();
				updateContent.put("phone_number",thirdId);
				userService.updateUserById(userView.getId(), updateContent);
			} else if (THIRD_DATA_TYPE.T_EMAIL.getTypeName().equals(thirdType)) {
				//TODO 暂时不知道咋处理
			}else {
				ThirdDataView thirdDataView  = userService
						.findThirdDataViewByUidAndType(userView.getId(),
								thirdType);
				if (thirdDataView == null) {
					thirdDataView = new ThirdDataView();
					thirdDataView.setTid(thirdId);
					if (thirdToken == null) {
						thirdDataView.setToken(pwd);
					} else {
						thirdDataView.setToken(thirdToken);
					}

					thirdDataView.setUid(userView.getId());
					thirdDataView.setType(thirdType);
					userService.saveThirdData(thirdDataView);
				}
			}
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (String type : thirdTypeArr) {
				ThirdDataView thirdDataView  = userService
						.findThirdDataViewByUidAndType(userView.getId(),
								type);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", type);
				if (thirdDataView != null) {
					map.put("binded", 1);
					map.put("third_id", thirdDataView.getTid());
					map.put("third_token", thirdDataView.getToken());
				} else {
					map.put("binded", 0);
					map.put("third_id", "");
					map.put("third_token", "");
				}
				list.add(map);
			}
			
			resultMap = getLoginOkData(userView);
			resultMap.put("third_part", list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return success(resultMap);
	}

	/**
	 * 创建email帐号
	 * 
	 */
	@RequestMapping(value = "/create_account")
	@ResponseBody
	public Map createAccount(
			@RequestParam(required = true, value = "email") String email,
			@RequestParam(required = true, value = "pwd") String pwd) {
		Map<String, String> result = new HashMap<String, String>();
		if (!StringUtils.isEmpty(email) && !StringUtils.isEmpty(pwd)) {
			if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
				return error("2009");
			}
			if (pwd.length() > 18 || pwd.length() < 6) {
				return error("2011");
			}

			try {
				UserView userView = getUserView(XThreadLocal.getInstance()
						.getCurrentUser());
				if (userView == null) {
					return error("2019");
				}
				ThirdDataView thirdDataView  = userService
						.findThirdDataViewByUidAndType(userView.getId(),
								THIRD_DATA_TYPE.T_EMAIL.getTypeName());

				if (thirdDataView != null) {
					return error("2023");
				} else {
					ThirdDataView thirdView = new ThirdDataView(
							userView.getId(),
							THIRD_DATA_TYPE.T_EMAIL.getTypeName(),
							MD5.md5(pwd), email,
							Constant.THIRD_BIND_PUBLIC_STATUS.YES.ordinal());
					userService.saveThirdData(thirdView);
					// cx更改部分－－start
					Map<String, String> params = new HashMap<String, String>();
					params.put("email", email);
					userService.updateUserById(XThreadLocal.getInstance()
							.getCurrentUser(), params);
					// cx 更改部分－－end
					result.put("email", email);
				}
			} catch (Exception e) {
				return error("0");
			}
		} else {
			return error("1000");
		}
		return success(result);
	}

	/**
	 * 判断该第三方帐号是否已经被绑定
	 * 
	 */
	@RequestMapping(value = "/is_binded")
	@ResponseBody
	public Map isBinded(
			@RequestParam(required = true, value = "third_type") String thirdType,
			@RequestParam(required = true, value = "third_id") String tid) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			ThirdDataView thirdDataView = userService
					.findThirdDataByTidAndType(tid, thirdType);
			if (thirdDataView == null) {
				result.put("binded", "0");
				return error("2006", result);
			} else {
				UserView userView = userService.findUserById(thirdDataView
						.getUid());
				result.put("binded", "1");
				result.put("nickname",
						userView == null ? "" : userView.getNickname());
				return success(result);
			}
		} catch (Exception e) {
			return error("0");
		}
	}

	/**
	 * 设置是否公开
	 * 
	 * @param thirdType
	 * @param userId
	 * @param display
	 * @return
	 */
	@RequestMapping("/show_bind")
	@ResponseBody
	public Map showBind(
			@RequestParam(required = true, value = "third_type") String thirdType,
			@RequestParam(required = true, value = "user_id") String userId,
			@RequestParam(required = true, value = "display") String display) {
		Long uid = XThreadLocal.getInstance().getCurrentUser();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("public_status", display);
		try {
			ThirdDataView thirdDataView  = userService
					.findThirdDataViewByUidAndType(uid,
							thirdType);
			userService.updateThirdData(thirdDataView.getTid(), thirdType, params);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}

		return success();
	}




	@RequestMapping("/third_token_refresh")
	@ResponseBody
	public Map thirdTokenRefresh(
			@RequestParam(required = true, value = "third_type") String thirdType,
			@RequestParam(required = true, value = "third_id") String thirdId,
			@RequestParam(required = true, value = "third_token") String thirdToken) {
		UserView userView = getUserView(XThreadLocal.getInstance()
				.getCurrentUser());
		if (userView != null) {
			try {
				ThirdDataView thirdDataView = userService
						.findThirdDataViewByUidAndType(userView.getId(),
								thirdType);
				if (thirdDataView != null) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("token", thirdToken);
					userService.updateThirdData(thirdDataView.getTid(), thirdType,
							map);
				} else {
					return error("0");
				}
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
		} else {
			return error("2019");
		}
		return success();
	}

	/**
	 * 渠道激活统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activate")
	@ResponseBody
	public Map<String, String> activate() {

		return success();
	}

	/**
	 * 个人信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserInfo(
			@RequestParam Map<String, String> params) throws ServiceException {

		Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		if (currentUser == null) {
			return error("2019");
		}
		Map update = new HashMap<String, String>();
		update.put("is_new", "0");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			// todo value有效性检查
			switch (key) {
			case "nickname":
				key = "nickname";
				break;
			case "gender":
				key = "gender";
				break;
			case "city":
				key = "city";
				break;
			case "constellation":
				key = "constellation";
				break;
			case "description":
				key = "description";
				break;
			case "profileImg":
				key = "profile_img";
				break;
			case "birthday":
				key = "birth";
				log.info("-------------birth--------" + value);
				try {
					value = String.valueOf(new SimpleDateFormat("yyyy-MM-dd")
							.parse(value).getTime() / 1000);
					log.info("-------------birth--------" + value);
				} catch (ParseException e) {
					log.error(e.getMessage(), e);
					return error("1002");
				}
				break;
			default:
				return error("1002");

			}
			// if("status".equals(key)){
			// update.put("mood", value);
			// }
			update.put(key, value);
		}
		String nickName = (String)update.get("nickname");
		if(nickName!=null){
			if(isAllowedForbidden(currentUser, Constant.USER_AUTH_FORBID.CHANGE_NICKNAME.getIndex())){
				return error("5002");
			}
			if (!nickName.equals(sensitiveService.filter(nickName, 1))) {
				return error("2032");
			}
			try {

				if (Utils.getByteLength(nickName) > 16) {
					return error("2008");
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			boolean flag = userService.isAllow(nickName);
			if (!flag) {
				return error("2001");
			}
			

		}
		
		String description = (String)update.get("description");
		if (description != null){
			if(isAllowedForbidden(currentUser, Constant.USER_AUTH_FORBID.CHANGE_DESC.getIndex())){
				return error("5002");
			}
			
			if (Utils.getByteLength(description) > 64) {
				return error("2031");
			}
			
			if (!description.equals(sensitiveService.filter(description, 1))) {
				return error("2032");
			}
		}
		
		if (!update.isEmpty()) {
			log.info("update profile : " + update.toString());
			userService.updateUserById(currentUser, update);
		}
		
		return success();
	}

	/**
	 * 上传头像
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadAvatar",method = RequestMethod.POST)
	@ResponseBody
	// debug
	public Map<String, String> uploadAvatar(
			@RequestParam("avatar_file") MultipartFile avatar_file,
			@RequestParam(required = false, value = "is_update") String isUpdate)
			throws ServiceException {

		Long uid = XThreadLocal.getInstance().getCurrentUser();

		if (uid == null) {
			return error("2019");
		}

		Map<String, String> result = new HashMap<>();
		
		if(isAllowedForbidden(uid, Constant.USER_AUTH_FORBID.CHANGE_AVATAR.getIndex())){
			return error("5002");
		}
		
		if (avatar_file.isEmpty()) {
			return error("3001");
		}

		String ext = FilenameUtils.getExtension(
				avatar_file.getOriginalFilename()).toLowerCase();
		if(isAllowedForbidden(XThreadLocal.getInstance().getCurrentUser(), Constant.USER_AUTH_FORBID.CHANGE_AVATAR.getIndex())){
			return error("5002");
		}

		if (ext != null && !"".equals(ext)) {
			if (!Arrays.asList("jpg", "jpeg", "bmp", "png", "gif", "webp")
					.contains(ext)) {
				return error("3003");
			}
		}
		// 获取指定文件的输入流
		InputStream content = null;
		try {
			content = avatar_file.getInputStream();
		} catch (IOException e) {
			return error("3004");
		}

		String date = new SimpleDateFormat("YMM/dd/HH/").format(new Date());
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		String remotePath = "/avatar/" + date  + "/" + timestamp + ".jpg";
		File tempFile = new File("/data/temp"+remotePath);
		
		try {
			avatar_file.transferTo(tempFile);
//			TencentSDK.uploadFile("showone", remotePath, tempFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = Loader.getInstance().getProps(
				"file.xiubi.url")+remotePath;

		result.put("profileImg", url);
		// 3. 更新数据库
		if (isUpdate!=null&&isUpdate.equals("1")) {
			userService
					.updateUserById(uid, ImmutableMap.of("profile_img", url));
			UserView u = userService.findUserById(uid);
			u.setProfileImg(url);
			IMUtils.getInstants().refreshUser(uid.toString(), u.getNickname(),u.getProfileImg());
		}

		return success(result);
	}

	@RequestMapping(value = "/third_friends")
	public Map thirdFriends() {

		// 当前登陆用户
		UserView user = getUserView(XThreadLocal.getInstance().getCurrentUser());
		if (user == null) {
			return error("2019", null);
		}

		try {
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			long uid = user.getId();
			ThirdDataView thirdDataView = userService
					.findThirdDataViewByUidAndType(uid, "sina");
			// TODO 从数据库获取第三方关注关系 从而拉取数据
			if (thirdDataView == null) {
				return error("2019");
			}
			List<SinaUserParam> list = thirdpartyService.getFoucsList(
					thirdDataView.getTid(), "");
			for (SinaUserParam sinaUserParam : list) {
				Map<String, Object> friendMap = new HashMap<String, Object>();
				friendMap.put("wpUserId", sinaUserParam.getUid());
				friendMap.put("avatar", user.getProfileImg());
				friendMap.put("wpName", user.getNickname());
//				GpsView gpsView = gpsService.findGpsByUid(sinaUserParam
//						.getUid());
//				double latitude = Double.parseDouble(XThreadLocal.getInstance()
//						.getHeaderParams().getLatitude());
//				double longitude = Double.parseDouble(XThreadLocal
//						.getInstance().getHeaderParams().getLongitude());
//				double distance = GpsUtil.distance(longitude, latitude,
//						gpsView.getLongitude(), gpsView.getLatitude());
//				int km = (int) (distance / 1000);
//				friendMap.put("distance", km + "公里");
				
				/*
				List<VideoView> videoViews = relationService
						.findVideosByUidAndTime(uid,
								(int) (System.currentTimeMillis() / 1000l),
								null, "desc");
							
				friendMap.put("hasVideo", videoViews.size());
				*/
				friendMap.put("thdFrom", "sina");
				friendMap.put("thdName", sinaUserParam.getScreen_name());
				dataList.add(friendMap);
			}
			resultMap.put("friend_list", dataList);
			return success(resultMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return error("0");
		}
	}

	/**
	 * 个人资料
	 * 
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/vip_info")
	@ResponseBody
	public Map vipInfo(
			@RequestParam(required = false, value = "uid") final Long uid) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		final Long userId = XThreadLocal.getInstance().getCurrentUser();

		try {
			if (userId == null) {
				return error("2019", null);
			}
//			UserPayInfoView userPayInfo = new LocalCache<UserPayInfoView>() {
//
//				@Override
//				public UserPayInfoView getAliveObject() throws Exception {
//					return manageService.findUserPayInfo(userId);
//				}
//			}.put(5, "user_payinfo_" + userId);

			Integer vipExpire = 0;
            try {
            	StockView stockView = payService.findStockByUidAndItem(uid, ITEM_TYPE.PROP.getId(), ITEM_PROP.VIP.getId());
            	if (stockView != null){
            		vipExpire = stockView.getDeadLine();
            	}
                
            }catch(Exception e){
            	log.error(e.getMessage(), e);
            	
            }
			if (vipExpire == 0) {
				resultMap.put("vip_expire", "00-00-00");
			} else {
				resultMap
						.put("vip_expire", DateCalculateUtil.intDate2String(
								vipExpire, "yy-MM-dd"));
			}
			
			Map<String, String> goldMap = new HashMap<String, String>();
			goldMap.put("item_id", String.valueOf(ITEM_MONEY.SHOWCOIN.getId()));
			goldMap.put("item_type",String.valueOf(ITEM_TYPE.MONEY.getId()));
			List<StockView> stockGoldList = payService.findStockByUidAndParam(
					userId, goldMap);
			if (stockGoldList!=null&&stockGoldList.size()>0) {
				resultMap.put("s_account",stockGoldList.get(0).getItemNumber());
			}else {
				resultMap.put("s_account", 0);
			}
			resultMap.put("is_vip", Adapter.isVip(vipExpire));
			resultMap.put("vip_number", 287232);
			return success(resultMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return error();
		}

	}



	private boolean isLegitimate(String str) {
		boolean flag = true;
		// log.info("str-------"+str);
		// String regEx =
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher(str);
		// log.info("---m.find()----"+m.find());
		// if(!m.find()){
		// flag = true;
		// }
		if (str.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "")
				.length() == 0) {
			// 不包含特殊字符
			flag = false;
		}
		return flag;
	}

	public static void main(String[] args) {
		try {
			System.out.println(DateUtils.parseDate("2016-11-09 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// try {
		// String value = String.valueOf(new SimpleDateFormat("yyyy-MM-dd")
		// .parse("1996-05-18").getTime() / 1000);
		// System.out.println(value);
		// System.out.println(new
		// SimpleDateFormat("yyyy-MM-dd").parse("1996-05-18").getTime());
		// } catch (Exception e) {
		// System.out.println(e);
		// }
		// try {
		// Date birthday = new Date((long)832348800*1000);
		// Period period = new Period(new DateTime(birthday), new DateTime(),
		// PeriodType.yearMonthDay());
		// System.out.println(period);
		// int age = period.getYears();
		// System.out.println("5.0.0".compareTo("5.0.0"));
		// System.out.println(TimeUtil.intDate2String(832348800, "yyyy-MM-dd"));
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// String nickName="1234567812345678";
		// try {
		// if(nickName.getBytes("GB2312").length>16){
		// System.out.println("超了＋"+nickName.getBytes("GB2312").length);
		// }
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Date birthday;
		// int age;
		// birthday = new Date((long)596822400*1000);
		// // Period period = new Period(new DateTime(birthday), new DateTime(),
		// PeriodType.yearDay());
		// // age = period.getYears();
		// Date now = new Date();
		// SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		// String birth_year =format_y.format(birthday);
		// String this_year =format_y.format(now);
		// age =Integer.parseInt(this_year) - Integer.parseInt(birth_year);
		// System.out.println(age);
		String aaa = "+18.9999";
		String bbb = "18.9999";
		System.out.println(aaa.indexOf("+"));
		if (aaa.indexOf("+") == 0) {
			aaa = aaa.replaceAll("\\+", "");
		}
		System.out.println(aaa);
		System.out.println(bbb.indexOf("+"));
		if (bbb.indexOf("+") == 0) {
			bbb = bbb.replaceAll("\\+", "");
		}
		System.out.println(bbb);
	}
}