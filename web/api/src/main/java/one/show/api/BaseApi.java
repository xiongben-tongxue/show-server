
package one.show.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import one.show.common.Adapter;
import one.show.common.Constant.ITEM_PROP;
import one.show.common.Constant.ITEM_TYPE;
import one.show.common.IPUtil;
import one.show.common.cache.LocalCache;
import one.show.common.exception.AuthException;
import one.show.common.exception.ReturnException;
import one.show.common.local.XThreadLocal;
import one.show.manage.thrift.view.FanLevelView;
import one.show.pay.thrift.view.StockView;
import one.show.service.ManageService;
import one.show.service.StatService;
import one.show.service.ThirdpartyService;
import one.show.service.UserService;
import one.show.service.VerifyCodeService;
import one.show.service.VideoService;
import one.show.stat.thrift.view.UserStatView;
import one.show.user.thrift.view.UserView;
import one.show.util.CityUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.collect.ImmutableMap;

public class BaseApi implements HandlerExceptionResolver {
	
    private static final Map<String, String> reasons = ImmutableMap.<String, String>builder()
            .put("0", "系统异常，请重试")
            .put("1000", "参数不足")
            .put("1001"	,	"尚未支持")
            .put("1002"	,	"参数错误")

                    //用户相关
            .put("2001", "该昵称已被他人使用")
            .put("2002", "该手机还没有注册过")
            .put("2003", "密码错误")
            .put("2004"	,	"该邮箱还没有注册过")
            .put("2005"	,	"未找到该用户")
            .put("2006"	,	"已绑定其他帐号")
            .put("2007"	,	"昵称包含不支持的字符")
            .put("2008"	,	"昵称长度不能超过8个汉字")
            .put("2009"	,	"邮箱格式不正确")
            .put("2010"	,	"与绑定的第三方帐号不一致")
            .put("2011"	,	"密码长度应为6-18位")
            .put("2012"	,	"第三方验证错误")
            .put("2013"	,	"手机号格式错误")
            .put("2014"	,	"手机号码验证错误或者已经失效")
            .put("2015"	,	"验证码错误")
            .put("2016"	,	"网络繁忙，请重新登陆。")
            .put("2017"	,	"本设备已经注册成功，请返回并直接登录。请勿在短时间内，同一设备反复注册，多谢您的支持。")
            .put("2018"	,	"无法发送验证码，因为发送次数已经达到限制。")
            .put("2019"	,	"登录已过期，请重新登录")
            .put("2020"	,	"请勿在短时间内反复注册，多谢您的支持。")
            .put("2021"	,	"暂不支持直接注册，请使用第三方账号注册登录")
            .put("2022"	,	"绑定两个以上第三方账号才能使用此功能")
            .put("2023"	,	"您已注册过邮箱")
            .put("2024"	,	"尚未支持该第三方账号登录")
            .put("2025"	,	"该手机号已经注册过")
            .put("2026"	,	"昵称太火，请换一个昵称再试")
            .put("2027"	,	"手机未绑定")
            .put("2028"	,	"最多设置10个房间管理员")
            .put("2029"	,	"他已经是您的房间管理员了")
            .put("2030"	,	"不能把自己设为您的房间管理员了")
            .put("2031"	,	"签名长度不能超过32个汉字")
            .put("2032"	,	"存在非法词汇")
            .put("2033"	,	"用户数据异常，请重新登录")

                    //关系相关
            .put("2100", "不能关注自己")
            .put("2101", "你已经关注过人家了")
            .put("2102", "你还没有关注人家呢")
            .put("2103", "关注超出限制数量")

                    //图片相关
            .put("3001", "图片不能为空")
            .put("3002", "图片文件尺寸过大")
            .put("3003", "不支持的文件格式")
            .put("3004"	,	"上传失败")

                    //意见反馈
            .put("4001", "联系方式过长")
            .put("4002", "反馈意见太长了，精简点吧")

                    //权限相关
            .put("5001", "你已被对方禁止此操作")
            .put("5002", "抱歉，你被禁止此操作")
            .put("5003"	,	"抱歉，你无权进行操作")
            .put("5004"	,  "你发的太快啦，冲杯咖啡吧，稍后再来~")
            .put("5005"	,  "验证过期，请重新登陆")
            .put("5006"  ,  "会员能看到更多视频哦~")
            .put("5007"  ,  "您已经被禁止登陆，如有疑问请联系客服")
            .put("5008"  ,  "自己不能把自己踢出房间")
            .put("5009"  ,  "自己不能把自己禁言")

                    //私信相关
            .put("6001", "你打招呼太频繁了，休息一下再发吧")
            .put("6002", "你们已经是好友了，去对话页面聊天吧")

                    //视频相关
            .put("7001", "该直播不存在")
            .put("7002", "您无权关闭该直播")
            .put("7003", "直播流名称不合法")
            .put("7004", "您已经在直播了")
            .put("7005", "直播标题太长了")

                    //付费相关
            .put("8001", "余额不足")
            .put("8002", "充值失败")
            .put("8003"  ,  "https needed")
            .put("8004"  ,  "invalid")
            .put("8005"  ,  "交易失败")
            .put("8006"  ,  "礼物不存在")
            .put("8007"  ,  "不存在这个人")
            .put("8008"  ,  "自己不能送给自己礼物")

                    //地理位置相关
            .put("9001", "没有获得位置信息，试试打开gps吧~")
            .put("9002", "不支持的城市")
            
            
            //搜索相关
            .put("10001", "请输入正确的昵称或ID")
            
            //安全相关
            .put("11001", "请求头参数错误")
            .put("11002", "签名错误")
            .put("11003"  ,  "已满每日最多验证次数")
            .put("11004"  ,  "获取过于频繁")
            .put("11005", "您的手机时间不对，请调整后重试")
            .put("11006", "#msg#")
            
            //签约红人相关
            .put("12001", "您尚未签约")
            .put("12002", "您已解约")
            .put("12003", "您的珍珠不足")
            .build();


    @Autowired
    protected UserService userService;
    @Autowired
    protected StatService statService;
    @Autowired
    protected VerifyCodeService verifyCodeService;
    @Autowired
    protected VideoService videoService;
    @Autowired
    protected ThirdpartyService thirdpartyService;
    @Autowired
    protected ManageService manageService;
    
    

    private static final Logger log = LoggerFactory.getLogger(BaseApi.class);


    /**
     * 请求成功
     * @param data : 要返回的数据
     * @return : 加上成功状态返回
     *
     * 例子 :
     * Map<String, String> result = new HashMap<>();
     * return ReturnHandle.success(result);
     */
    public static Map success( Map data) {
        Map result = new HashMap();
        result.put("state", 1);
        result.put("msg", "操作成功");
        result.put("data", data);
        return result;
    }

    /**
     * 请求成功
     * @return : 加上成功状态返回
     *
     * 例子 :
     * Map<String, String> result = new HashMap<>();
     * return ReturnHandle.success(result);
     */
    public static Map success() {
    	  Map result = new HashMap();
          result.put("state", 1);
          result.put("msg", "操作成功");
          result.put("data", new HashMap());
          return result;
    }

    /**
     * 请求失败
     * @param code : 失败的code
     * @param replace : 失败原因中有#key#的可以根据这个参数进行替换
     * @return
     *
     * 例子 :
     * 		return ReturnHandle.error("1001", null);
     */
    public static Map error(String code, Map<String,String> replace) {
        Map result = new HashMap<String, Object>();
        result.put("state", Integer.parseInt(code));
        String reason = reasons.get(code);
        if (replace != null) {
            for (Map.Entry<String, String> entry : replace.entrySet()) {
                reason = reason.replace("#" + entry.getKey() + "#", entry.getValue());
            }
        }
        result.put("msg", reason);
        return result;
    }
    
    
    public static Map error(String code){
    	return error(code, null);
    }
    
    public static Map error( ){
        return error("0", null);
    }

    protected List<Integer> getForbiddenList(final long uid) {
    	
    	try {
			List<Integer> forbiddenList = new LocalCache<List<Integer>>(){
				@Override
				public List<Integer> getAliveObject() throws Exception {
					return userService.getForbiddenActionListByUid(uid);
				}
				
			}.put(60 * 10, "user_forbidden_list_"+uid);
			
			return forbiddenList;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
      
    }

    protected UserView getUserView(final Long uid) {
    	if (uid == null){
    		return null;
    	}
    	
        UserView user = null;

        try {
            user = new LocalCache<UserView>() {

                @Override
                public UserView getAliveObject() throws Exception {
                	return userService.findUserById(uid);
                }
            }.put(60 * 1, "user_"+uid);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
        }
        return user;
    }

	public FanLevelView getFanLevelView(int level) {
		List<FanLevelView> fanLevelList = null;

		try {
			fanLevelList = new LocalCache<List<FanLevelView>>() {
				@Override
				public List<FanLevelView> getAliveObject() throws Exception {
					return manageService.findFanLevelList();
				}

			}.put(60 * 60 * 5, "fanLevelList");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		FanLevelView fanLevelView = null;
		if(fanLevelList!=null){
			for (FanLevelView fv:fanLevelList) {
				if(fv.getFanLevel()==level){
					fanLevelView = fv;
					break;
				}
			}
		}
		return fanLevelView;
	}

    protected UserView getUserView() {
        Long uid = XThreadLocal.getInstance().getCurrentUser();
    	return getUserView(uid);
    }

    protected UserStatView getUserStatView(final Long uid) {
        if (uid == null){
            return null;
        }

        UserStatView userStat = null;

        try {
            userStat = new LocalCache<UserStatView>() {

                @Override
                public UserStatView getAliveObject() throws Exception {
                    return statService.findUserStatByUid(uid);
                }
            }.put(60 * 5, "user_stat_"+uid);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
        }

        if (userStat == null) {
            userStat = new UserStatView();
            userStat.setUid(uid);
        }

        return userStat;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception exception) {
    	log.error(exception.getMessage()+"["+httpServletRequest.getRequestURI()+"]", exception);
    	httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	
        if (exception instanceof MaxUploadSizeExceededException) {
            httpServletResponse.setStatus(200);
            return new ModelAndView(new MappingJackson2JsonView(),error("3002"));
        }
        else if (exception instanceof MissingServletRequestParameterException) {
            httpServletResponse.setStatus(200);
            log.info("miss parameter : {}", ((MissingServletRequestParameterException) exception).getParameterName());
            return new ModelAndView(new MappingJackson2JsonView(),error("1000"));
        }
        else if (exception instanceof ReturnException) {
            httpServletResponse.setStatus(200);
            return new ModelAndView(new MappingJackson2JsonView(),error(exception.getMessage()));
        }
        else if (exception instanceof AuthException) {
            httpServletResponse.setStatus(200);
            return new ModelAndView(new MappingJackson2JsonView(),error(exception.getMessage()));
        }
        else if (exception instanceof SecurityException) {
            httpServletResponse.setStatus(200);
            return new ModelAndView(new MappingJackson2JsonView(),error(exception.getMessage()));
        }else if (exception instanceof HttpRequestMethodNotSupportedException) {
            httpServletResponse.setStatus(200);
            Map<String, String> replace = new  HashMap<String, String>();
            replace.put("msg", exception.getMessage());
            return new ModelAndView(new MappingJackson2JsonView(),error("11006", replace));
        } else if (exception instanceof InvalidMediaTypeException) {
                //Content-Type ERROR
            httpServletResponse.setStatus(200);
            return new ModelAndView(new MappingJackson2JsonView(), error("1"));
        }else if (exception instanceof MultipartException) {
            httpServletResponse.setStatus(200);
            return new ModelAndView(new MappingJackson2JsonView(), error("1"));
        }else if (exception.getMessage() != null && exception.getMessage().trim().equals("org.eclipse.jetty.io.EofException")) {
                //jetty准备回写response发现connection已经被关闭导致。
            httpServletResponse.setStatus(200);
            return new ModelAndView(new MappingJackson2JsonView(), error("1"));
        }
        
        else{
        	return new ModelAndView(new MappingJackson2JsonView(),error("0"));
        }

        
    }
    /**
     *判断字符串中全部为数字 
     * @param str
     * @return
     */
    protected static boolean isNumber(String str){
		return str.matches("^?(([0-9a-f]+))$");
	}
    
    protected  boolean isAllowedForbidden(Long uid,int action){
        boolean flag = false;
        try {
            	flag=userService.userIsForbidden(uid, action);
            } catch (Exception e) {
                    log.error(e.getMessage(), e);
            }
        return flag;
    }
    
    
    protected int getReceive(final long uid){
    	int receive = 0;
    	try {
    		receive = new LocalCache<Integer>() {

				@Override
				public Integer getAliveObject() throws Exception {
					
					UserStatView userStatView = statService.findUserStatByUid(uid);
					if (userStatView == null){
						return 0;
					}else{
						return (int)userStatView.getReceive();
					}
					
				}
				
			}.put(10, "master_receive_"+uid);
		} catch (Exception e) {
			 log.error(e.getMessage(), e);
		}
    	
		return receive;
    }
    

    protected String getCity(HttpServletRequest request) {
		
		String city =  null;
		String lng = XThreadLocal.getInstance().getHeaderParams().getLongitude();
		String lat = XThreadLocal.getInstance().getHeaderParams().getLatitude();
		String enCity = XThreadLocal.getInstance().getHeaderParams().getCity();
		
		if (enCity != null){
			try {
				city = URLDecoder.decode(XThreadLocal.getInstance().getHeaderParams().getCity(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		try {
			if (city == null){
				if (!StringUtils.isBlank(lng) && !StringUtils.isBlank(lat)){
					
					city = CityUtil.getCityName(Double.parseDouble(lat), Double.parseDouble(lng));
					log.info("get city from lat-lng, city="+city);
				}else{
					city = CityUtil.getCityName(IPUtil.getIP(request));
					log.info("get city from ip, city="+city);
				}
			}else{
				log.info("get city from header, city="+city);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return city;
		
	}

    public static void main(String[] args) {
    	System.out.println(isNumber("7BEA7020-D4E1-42CB-B3B0-AAFF9C7384BE"));
	}

}
