package one.show.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import one.show.common.JacksonUtil;
import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.Constant.USER_AGENT;
import one.show.common.cache.LocalCache;
import one.show.common.exception.ServiceException;
import one.show.common.local.HeaderParams;
import one.show.common.local.XThreadLocal;
import one.show.service.DeviceService;
import one.show.service.GiftService;
import one.show.service.ManageService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import one.show.manage.thrift.view.GiftListView;
import one.show.manage.thrift.view.GiftView;
import one.show.manage.thrift.view.ReportView;
import one.show.manage.thrift.view.VersionControlView;
import one.show.user.thrift.view.UserView;

@Controller
public class ManageApi extends BaseApi{
	private static final Logger log = LoggerFactory.getLogger(ManageApi.class);
	@Autowired
	private ManageService manageService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private GiftService giftService;
	
	
	/**
	 * 举报、问题反馈
	 * @param type 0问题反馈， 1举报
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/report")
	@ResponseBody
	public Map report(@RequestParam(required = false, value = "userId") Long userId,
			@RequestParam(required = false, value = "type") Integer type,
			@RequestParam(required = false, value = "contact") String contact,
			@RequestParam(required = false, value = "content") String content) {
		
		// 取得当前登录用户
		UserView user = getUserView(XThreadLocal.getInstance().getCurrentUser());
		if (user == null) {
			return error("2019");
		}
		
		if (type == null){
			type = 0;
		}
		
		//问题反馈
		if (type == 0){
			
			if (contact == null){
				return error("1002");
			}else if (contact.length()>=25){
				return error("4001");
			}
			
			if (content == null){
				return error("1002");
			}else if (content.length()>=500){
				return error("4002");
			}
			
			final long uid = user.getId();
			try {
				ReportView reportView = new ReportView();
				reportView.setContent(content);
				reportView.setContact(contact);
				reportView.setCreateTime((int)(System.currentTimeMillis()/1000l));
				reportView.setUid(uid);
				manageService.saveReport(reportView);
			} catch (Exception e) {
				return error("0");
			}
		}else{
			//举报
			if (userId == null){
				return error("1000");
			}
		}
		
		
		return success();
	}
	
	/**
	 * 检查新版本
	 * @param request
	 * @return
	 */
	@RequestMapping("/version_control")
	@ResponseBody
	public Map versionControl(HttpServletRequest request){
		
		HeaderParams headerParams = XThreadLocal.getInstance().getHeaderParams();
		
		String version =  "1.0.0";
		String channel =  "xiubi";
		if (headerParams != null){
			 version =  headerParams.getAppVersion();
			 channel =  headerParams.getChannel();
		}
		
		USER_AGENT userAgent = XThreadLocal.getInstance().getUserAgent();
		
		int source = 0;
		switch (userAgent) {
		case ANDROID:
			source = 0;
			break;
		case IOS:
			source = 1;
			break;
		default:
			break;
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(version)){
			
			VersionControlView versionControl = null;
			try {
				versionControl=manageService.findVersionControlByAgentType(source);
			} catch (ServiceException e) {
				log.error(e.getMessage(),e);
			}
			//初始状态不需要升级
			int version_state = 0;
			if(versionControl!=null){
				if(version.compareTo(versionControl.getCoercionVersion())<0){
					//强制
					version_state = 2;
				}else if(versionControl.getAdviceVersion().compareTo(version)>0&&version.compareTo(versionControl.getCoercionVersion())>0){
					//大于强制升级版本，小于最新可用版本
					//建议
					version_state = 1;
				}
				String downloadUrl = versionControl.getDownloadUrl();
				if (!downloadUrl.endsWith(".apk")){
					downloadUrl = downloadUrl+"_"+channel+".apk";
				}
				resultMap.put("version_state", version_state);
				resultMap.put("new_kernel_version", versionControl.getAdviceVersion());
				resultMap.put("client_download_url",downloadUrl);
				resultMap.put("update_title", versionControl.getTitle());
				resultMap.put("update_msg",versionControl.getMsg());
			}else{
				resultMap.put("version_state", "");
				resultMap.put("new_kernel_version", "");
				resultMap.put("client_download_url","");
				resultMap.put("update_title", "");
				resultMap.put("update_msg","");
			}
			resultMap.put("update_upgrade", "抢先体验");
			resultMap.put("update_ignore", "放弃机会");
			resultMap.put("update_cancel", "下次再说");
		}else{
			return error("2019");
		}
		return success(resultMap);
	}
	
	
	/**
	 * 礼物列表
	 * @return
	 */
	@RequestMapping("/gift_list")
	@ResponseBody
	public Map giftList(){
		
	
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List giftList = new ArrayList();
		
		GiftListView giftListView = null;
		try {
			giftListView = new LocalCache<GiftListView>(){

				@Override
				public GiftListView getAliveObject() throws Exception {
					return giftService.getGiftList(null, ADMIN_STATUS.ENABLE, 0, Integer.MAX_VALUE);
				}
			}.put(60 * 10, "gift_list_"+ADMIN_STATUS.ENABLE);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		
		if (giftListView != null && giftListView.giftList != null){
			
			for (GiftView giftView : giftListView.giftList){
				Map<String, Object> gift = new HashMap<String, Object>();
				gift.put("id", giftView.getId());
				gift.put("price", giftView.getPrice());
				gift.put("name", giftView.getName());
				gift.put("icon", giftView.getIcon());
				gift.put("image", giftView.getImage());
				gift.put("exp", giftView.getExp());
				gift.put("type", giftView.getType());
				
				Map extendMap =  new HashMap();
				if (giftView.getExtend() != null && !giftView.getExtend().equals("")){
					 extendMap = JacksonUtil.readJsonToObject(Map.class, giftView.getExtend());
				}
				
				gift.put("extend", extendMap);
				giftList.add(gift);
				
			}
		}
		
		resultMap.put("gift_list", giftList);
		return success(resultMap);
		
	}
	
}
