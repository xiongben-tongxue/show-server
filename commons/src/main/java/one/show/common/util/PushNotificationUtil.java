package one.show.common.util;


import java.util.HashMap;
import java.util.Map;

import one.show.common.TypeUtil;

import org.apache.log4j.Logger;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * @Description:极光推送通知
 * @author hank
 * @version v4.0
 */
public class PushNotificationUtil {
	
	public static final String MASTER_SECRET = "540b1548107e56f5b052ec0d";
	public static final String APPKEY = "6e29666452911257935a595d";
	
	protected static final Logger LOG = Logger.getLogger(PushNotificationUtil.class);
	
	private static boolean production = true;
	
	public static boolean isProduction() {
		return production;
	}

	public static void setProduction(boolean production) {
		PushNotificationUtil.production = production;
	}

	/**
	 * @Title: sendNotificationAllpush
	 * @Description: 向android_ios平台的所有设备上推送通知
	 * @param content
	 * @param response
	 *            设定文件
	 * @throws
	 */
	public void sendNotificationAllAllpush(String content) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY);

		PushPayload payload = PushPayload.newBuilder()
											.setPlatform(Platform.android_ios())
											.setAudience(Audience.all())
											.setNotification(Notification.alert(content))
											.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * @Title: sendNotificationAllAliasPush
	 * @Description: 向android_ios平台的指定目标推送通知
	 * @param alias
	 * @param content
	 * @param response
	 *            设定文件
	 * @throws
	 */
	public static void sendNotificationAndroidAliasPush(PushVo vo,String... alias) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY,5);
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android_ios())
											.setAudience(Audience.alias(alias))
											// 推送目标							
											.setOptions(Options.newBuilder().setApnsProduction(production).build())
											.setNotification(Notification.newBuilder()
													.addPlatformNotification(
															AndroidNotification.newBuilder().setAlert(vo.getTitle())
															.addExtra("type", vo.getType())
															.addExtra("content", vo.getContent())
															.build()) 
													.build())
											.build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	
	/**
	 * @Title: sendNotificationAndroidAliasPush
	 * @Description:向android平台的指定目标推送通知
	 * @param alias
	 * @param content
	 * @param response
	 *            设定文件
	 * @throws
	 */
	public static void sendNotificationIosAliasPush(PushVo vo,String... alias) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY);

		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all())// ios平台
											.setAudience(Audience.alias(alias))
											.setOptions(Options.newBuilder().setApnsProduction(production).build())
											// 推送目标
											.setNotification(Notification.newBuilder()
													.addPlatformNotification(IosNotification.newBuilder().setAlert(vo.getTitle())
															.setBadge(1)
															.addExtra("type", vo.getType())
															.addExtra("content",  vo.getContent())
															.build()) 
													.build())
											.build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * @Title: sendNotificationIosAliasPush
	 * @Description: 向iso平台的指定目标推送通知
	 * @param alias
	 * @param content
	 * @param response
	 *            设定文件
	 * @throws
	 */
	public void sendNotificationIosAliasPush(String alias, String content) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY);

		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios())// ios平台
											.setAudience(Audience.alias(alias))
											// 推送目标
											.setNotification(Notification.alert(content))
											.build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * @Title: sendNotificationAllTagPush
	 * @Description: 向android_ios平台，目标是标签为“tag”的设备推送通知
	 * @param tag
	 * @param title
	 * @param content
	 * @param response
	 *            设定文件
	 * @throws
	 */
	public static void sendNotificationAllTagPush(String tag, String title, String content) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY);

		PushPayload payload = PushPayload.newBuilder()
											.setPlatform(Platform.android_ios())
											.setAudience(Audience.tag(tag))
											.setNotification(Notification.android(content, title, null))
											.build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * @Title: pushAllTagAndAlertWithExtrasAndMessage
	 * @Description: 平台是android_ios，推送目标是 "tag1", "tag_all" 的并集， 推送内容同时包括通知与消息 通知信息是 noticeContent， 通知声音为 sound， 并且附加字段 from = "JPush"； 消息内容是
	 *               msgContent
	 * @param noticeContent
	 * @param msgContent
	 * @param sound
	 * @param response
	 *            设定文件
	 * @throws
	 */
	public void pushAllTagAndAlertWithExtrasAndMessage(String tag1, String tag_all, String noticeContent, String msgContent, String sound) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY);

		PushPayload payload = PushPayload.newBuilder()
											.setPlatform(Platform.android_ios())
											.setAudience(Audience.tag_and("tag1", "tag_all"))
											.setNotification(Notification.newBuilder()
																			.addPlatformNotification(IosNotification.newBuilder()
																													.setAlert(noticeContent)
																													.setSound(sound)
																													.addExtra("from", "JPush")
																													.build())
																			.build())
											.setMessage(Message.content(msgContent))
											.setOptions(Options.newBuilder().setApnsProduction(production).build())
											.build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * @Title: pushAllAudienceMoreMessageWithExtras
	 * @Description: 平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的交集）并（"alias1" 与 "alias2" 的交集）， 推送内容为 msgContent 的消息，并且附加字段 from = JPush
	 * @param tag1
	 * @param tag2
	 * @param alias1
	 * @param alias2
	 * @param msgContent
	 * @param response
	 *            设定文件
	 * @throws
	 */
	public void pushAllAudienceMoreMessageWithExtras(String tag1, String tag2, String alias1, String alias2, String msgContent) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY);

		PushPayload payload = PushPayload.newBuilder()
											.setPlatform(Platform.android_ios())
											.setAudience(Audience.newBuilder()
																	.addAudienceTarget(AudienceTarget.tag(tag1, tag2))
																	.addAudienceTarget(AudienceTarget.alias(alias1, alias2))
																	.build())
											.setMessage(Message.newBuilder().setMsgContent(msgContent).addExtra("from", "JPush").build())
											.build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}
	
	public static  void sendNotificationAllAliasPushMsg(String alias, PushVo vo) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APPKEY,3);
		try {
			PushPayload payload = PushPayload.newBuilder()
	        .setPlatform(Platform.android_ios())
	        .setOptions(Options.newBuilder().setApnsProduction(production).build())
	        .setAudience(Audience.newBuilder()
	                .addAudienceTarget(AudienceTarget.alias(alias))
	                .build())
	        .setMessage(Message.newBuilder()
	                .setMsgContent(vo.getTitle())
	                .setTitle("showone")
	                .addExtra("type", vo.getType())
	                .addExtra("content", vo.getContent())
	                .build())
	        .build();
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}
	
	public static void simplePush(PushVo vo,String... alias){
		Map<String, String> map = new HashMap<>();
		map.put("type", vo.getType()+"");
		map.put("pushType", vo.getPushType()+"");
		map.put("content", vo.getContent());
		map.put("redirect_type", vo.getRedirectType()+"");
		map.put("redirect_id", vo.getRedirectId()+"");
		map.put("push_image", vo.getPushImage());
		map.put("topic_type", vo.getTopicType()+"");
		map.put("url", vo.getUrl());
		JPushClient jpushClient = new JPushClient(MASTER_SECRET,APPKEY);
		PushPayload payload = PushPayload  
                .newBuilder()  
                .setPlatform(Platform.all())  
                .setOptions(Options.newBuilder().setApnsProduction(production).build())
                .setAudience(Audience.alias(alias))  
                .setNotification(  
                        Notification  
                                .newBuilder()  
                                .addPlatformNotification(  
                                        IosNotification.newBuilder().setBadge(1) 
                                                .setAlert(vo.getTitle())  
                                                .setMutableContent(true)
                                                .setSound("default")
                                                .addExtras(map).build())  
                                .addPlatformNotification(  
                                        AndroidNotification.newBuilder()
                                                .setAlert(vo.getTitle())  
                                                .addExtras(map)  
                                                .build())  
                                .build()).build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			
			LOG.info("Got result - " + result+","+TypeUtil.typeToString("pushvo", vo));

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ");

		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ");
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}catch (Exception e) {
			LOG.error("push error:"+e.getMessage());
		}
	}
	
	
	public static void simplePush(PushVo vo){
		Map<String, String> map = new HashMap<>();
		map.put("type", vo.getType()+"");
		map.put("content", vo.getContent());
		map.put("redirect_type", vo.getRedirectType()+"");
		map.put("redirect_id", vo.getRedirectId()+"");
		map.put("push_image", vo.getPushImage());
		map.put("topic_type", vo.getTopicType()+"");
		map.put("url", vo.getUrl());
		JPushClient jpushClient = new JPushClient(MASTER_SECRET,APPKEY);
		PushPayload payload = PushPayload  
                .newBuilder()  
                .setPlatform(Platform.all())  
                .setOptions(Options.newBuilder().setApnsProduction(production).build())
                .setAudience(Audience.all())  
                .setNotification(  
                        Notification  
                                .newBuilder()  
                                .addPlatformNotification(  
                                        IosNotification.newBuilder().setBadge(1) 
                                                .setAlert(vo.getTitle())  
                                                .setMutableContent(true)
                                                .setSound("default")
                                                .addExtras(map).build())  
                                .addPlatformNotification(  
                                        AndroidNotification.newBuilder()
                                                .setAlert(vo.getTitle())  
                                                .addExtras(map)  
                                                .build())  
                                .build()).build();

		try {
			PushResult result = jpushClient.sendPush(payload);
			
			LOG.info("Got result - " + result+","+TypeUtil.typeToString("pushvo", vo));

		}
		catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		}
		catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}catch (Exception e) {
			LOG.error("push error:"+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
		
		
		
		PushVo vo = new PushVo();
		vo.setContent("testconetn");
		vo.setTitle("欢迎来到全球最火热的社交平台。快快发布你的作品，与中国最富有生活品味的人建立联系，共享生活志趣。");
		vo.setType(1);
		
		simplePush(vo);
//		PushVo vo = new PushVo();
//		vo.setContent("{\"stream_addr\":\"http://pull.souyu.tv/show/773071987236016128_1473149699.flv\",\"topic\":\"\",\"is_vip\":0,\"share_addr\":\"http://www.xiubi.com/share/live/773071987236016128\",\"master\":{\"uid\":753815857196564480,\"nick_name\":\"<E8><B4><9D><E5><A3><B3><E6><96><B0><E4><BA><BA>\",\"birth\":0,\"fan_level\":5,\"description\":null,\"master_level\":1,\"gender\":1,\"pid\":23220765,\"avatar\":\"http://wx.qlogo.cn/mmopen/MvJIBH38Q9UibUL5Yr6ABaspmia0I2nbaP7ibiasNvon5KbnN6zKzJbzHXIOyfSP6IFXu9vKYYp2tUG5cYGpHFiaowbhhOaD9m5lh/0\"},\"city\":\"<E5><8C><97><E4><BA><AC><E5><B8><82>\",\"id\":773071987236016128,\"room_admin\":0,\"cover\":\"http://wx.qlogo.cn/mmopen/MvJIBH38Q9UibUL5Yr6ABaspmia0I2nbaP7ibiasNvon5KbnN6zKzJbzHXIOyfSP6IFXu9vKYYp2tUG5cYGpHFiaowbhhOaD9m5lh/0\",\"title\":\"<E8><B4><9D><E5><A3><B3><E6><96><B0><E4><BA><BA><E7><9A><84><E7><9B><B4><E6><92><AD>\",\"room_id\":773071987236016128,\"is_live\":\"1\",\"vid\":773071987236016128,\"receive\":146,\"stream_status\":1,\"online_users\":7}");
//		vo.setType(1);
//		vo.setTitle("test title");
//		
//		sendNotificationAndroidAliasPush(vo,"755761105120399360","756464418618998784");
//		sendNotificationIosAliasPush(vo,"755761105120399360","756464418618998784");
//		sendNotificationAndroidAliasPush("755761105120399360","{\"stream_addr\":\"http://pull.souyu.tv/show/773071987236016128_1473149699.flv\",\"topic\":\"\",\"is_vip\":0,\"share_addr\":\"http://www.xiubi.com/share/live/773071987236016128\",\"master\":{\"uid\":753815857196564480,\"nick_name\":\"<E8><B4><9D><E5><A3><B3><E6><96><B0><E4><BA><BA>\",\"birth\":0,\"fan_level\":5,\"description\":null,\"master_level\":1,\"gender\":1,\"pid\":23220765,\"avatar\":\"http://wx.qlogo.cn/mmopen/MvJIBH38Q9UibUL5Yr6ABaspmia0I2nbaP7ibiasNvon5KbnN6zKzJbzHXIOyfSP6IFXu9vKYYp2tUG5cYGpHFiaowbhhOaD9m5lh/0\"},\"city\":\"<E5><8C><97><E4><BA><AC><E5><B8><82>\",\"id\":773071987236016128,\"room_admin\":0,\"cover\":\"http://wx.qlogo.cn/mmopen/MvJIBH38Q9UibUL5Yr6ABaspmia0I2nbaP7ibiasNvon5KbnN6zKzJbzHXIOyfSP6IFXu9vKYYp2tUG5cYGpHFiaowbhhOaD9m5lh/0\",\"title\":\"<E8><B4><9D><E5><A3><B3><E6><96><B0><E4><BA><BA><E7><9A><84><E7><9B><B4><E6><92><AD>\",\"room_id\":773071987236016128,\"is_live\":\"1\",\"vid\":773071987236016128,\"receive\":146,\"stream_status\":1,\"online_users\":7}",Platform.ios());
	}

}
