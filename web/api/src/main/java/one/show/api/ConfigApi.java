/**
 * 
 */
package one.show.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.Constant;
import one.show.common.cache.LocalCache;
import one.show.common.im.IMUtils;
import one.show.common.local.XThreadLocal;
import one.show.manage.thrift.view.AdvertisementView;
import one.show.manage.thrift.view.SystemConfigView;
import one.show.service.ManageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Haliaeetus leucocephalus 2018年3月18日 下午4:57:16
 *
 */
@Controller
public class ConfigApi extends BaseApi{

	private static final Logger log = LoggerFactory.getLogger(ConfigApi.class);
	
	@Autowired
	private ManageService manageService;
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/config")
	@ResponseBody
	public Map config(){

	
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//主播协议
		resultMap.put("agreement_page", "http://www.xiubi.com/agreement.html");
		//隐私策略
		resultMap.put("privacy_page", "http://www.xiubi.com/privacy.html");
		//服务条款
		resultMap.put("service_page", "http://www.xiubi.com/service.html");
		//联系我们
		resultMap.put("contact_page", "http://www.xiubi.com/contact.html");
		//社区公约
		resultMap.put("convention_page", "http://www.xiubi.com/convention.html");
	
		List<String> noticeList = new ArrayList<String>();
		noticeList.add("我们提倡绿色文明直播，封面和直播内容包含低俗、引诱、暴露、暴力、吸烟等将会被屏蔽或者封停帐号，网警24小时在线巡查！");
		
		
		//房间公告
		resultMap.put("noticeList", noticeList);
		
		resultMap.put("rongyun_key", IMUtils.getInstants().getKey());
		
		
		//查询系统配置表 参数
		try {
			final String clientVersion = XThreadLocal.getInstance().getHeaderParams().getAppVersion();
			SystemConfigView systemConfigView = new LocalCache<SystemConfigView>() {
				@Override
				public SystemConfigView getAliveObject() throws Exception {
					SystemConfigView sysView = manageService.getSystemConfigByVersion(clientVersion);
					if (sysView==null) {
						sysView = manageService.getSystemConfig(Constant.SYSTEM_CONFIG_ID);
					}
					return sysView;
				}
			}.put(60*5, "systemConfig_"+clientVersion);
			

			resultMap.put("alipay",systemConfigView.getAlipay());
			resultMap.put("weixinpay",systemConfigView.getAlipay());
			resultMap.put("applepay",systemConfigView.getApplepay());
			resultMap.put("bitrate",systemConfigView.getBitrate());
			resultMap.put("frame",systemConfigView.getFrame());
			resultMap.put("width",systemConfigView.getWidth());
			resultMap.put("height",systemConfigView.getHeight());
			resultMap.put("shareQq",systemConfigView.getShareQq());
			resultMap.put("shareQqzone",systemConfigView.getShareQqzone());
			resultMap.put("shareWx",systemConfigView.getShareWx());
			resultMap.put("shareWxPyq",systemConfigView.getShareWxPyq());
			resultMap.put("show_ad", systemConfigView.getShowAd());
			//隐藏qq登陆
			resultMap.put("show_qq_login", systemConfigView.getShowQQLogin());
			
			
			
		} catch (Exception e) {
			resultMap.put("alipay",0);
			resultMap.put("weixinpay",0);
			resultMap.put("applepay",0);
			resultMap.put("bitrate",800);
			resultMap.put("frame",25);
			resultMap.put("width",540);
			resultMap.put("height",960);
			resultMap.put("shareQq","我下载了ShowLive，我和我的小伙伴都惊呆了！(他提到了你)");
			resultMap.put("shareQqzone","我下载了ShowLive，我和我的小伙伴都惊呆了！(他提到了你)");
			resultMap.put("shareWx","我下载了ShowLive，我和我的小伙伴都惊呆了！(他提到了你)");
			resultMap.put("shareWxPyq","我下载了ShowLive，我和我的小伙伴都惊呆了！(他提到了你)");
			resultMap.put("show_ad", 0);
			//隐藏qq登陆
			resultMap.put("show_qq_login", 0);
			
			log.error("-----getSystemConfig error!!-----"+e.getMessage(),e);
		}
		
		
		
		return success(resultMap);
	}


	
	@RequestMapping("/get_advertisement")
	@ResponseBody
	public Map getAdvertisement(@RequestParam(required=true,value="type") final String type){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		try {
			List<AdvertisementView> list = new LocalCache<List<AdvertisementView>>() {
				
				@Override
				public List<AdvertisementView> getAliveObject() throws Exception {
					return manageService.findAdvertisementView();
				}
			}.put(60*30, "advs_list");
			
			if(list!=null){
				for(AdvertisementView adv:list){
					Map<String,String> resultMap = new HashMap<String,String>();
					map.put("id",adv.getId());
					map.put("image_url",adv.getImg());
					map.put("link_url",adv.getUrl());
					map.put("link_title",adv.getTitle());
					resultList.add(resultMap);
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
		map.put("adlist", resultList);
		return success(map);
	}
	
	

}


