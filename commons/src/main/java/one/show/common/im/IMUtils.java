package one.show.common.im;

import io.rong.models.ChatroomInfo;
import io.rong.models.FormatType;
import io.rong.models.Message;
import io.rong.models.MsgObj;
import io.rong.models.PlatformNotification;
import io.rong.models.PushMessage;
import io.rong.models.PushNotification;
import io.rong.models.SdkHttpResult;
import io.rong.models.TagObj;
import io.rong.models.TxtMessage;
import io.rong.models.UserTag;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.Constant;
import one.show.common.JacksonUtil;
import one.show.common.Loader;
import one.show.common.TypeUtil;
import one.show.common.exception.ServiceException;
import one.show.common.globalconf.GlobalConf;
import one.show.common.zk.BaseZookeeper;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.zookeeper.WatchedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public  class IMUtils extends BaseZookeeper {
	
	public static IMUtils imUtils = null;
	
	private static final Logger log = LoggerFactory.getLogger(IMUtils.class);
	
	private static String key = "testkey";
	
	private static String secret = "testsecret";
	
	public GlobalConf globalConf;
	
	private IMUtils(){
		String confData = null;
		try {
			connect();
			confData = getData(Constant.ZK_GLOBAL_CONFIG_PATH_HOME, false);
			globalConf = JacksonUtil.readJsonToObject(GlobalConf.class, confData);
			close();
		} catch (Exception e) {
			log.error("init imUtils error.",e);
		}
        if(globalConf.getEnv()==1){
        	key = "prdkey";
        	secret = "prdsecret";
        }
        log.info("imutils init.  GlobalConf : "+confData+",key:"+key+",secret:"+secret);
	}
	
	public synchronized static IMUtils getInstants(){
		if(imUtils==null){
			imUtils = new IMUtils();
		}
		return imUtils;
	}
	
	public  boolean wordFilterAdd(String word) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.wordFilterAdd(key, secret, word, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun wordFilterAdd error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	
	}
	
	public  boolean wordFilterDelete(String word) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.wordFilterDelete(key, secret, word, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun wordFilterDelete error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	
	}
	
	public  JSONArray wordFilterList() throws ServiceException{
		try {
			SdkHttpResult result = RongYunSDK.wordFilterList(key, secret, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			JSONArray array = obj.getJSONArray("words");
			return array;
		} catch (Exception e) {
			log.error("rongyun wordFilterList error.",e);
			throw new ServiceException(e);
		}
	
	}
	
	public  List<ChatroomInfo> getChatRoom(List<String> chatroomIds) throws ServiceException{
		List<ChatroomInfo> list = null;
		try {
			
			SdkHttpResult result = RongYunSDK.queryChatroom(key, secret, chatroomIds, FormatType.json);
			list = new ArrayList<ChatroomInfo>();
			JSONObject obj = new JSONObject(result.getResult());
			JSONArray array = obj.getJSONArray("chatRooms");
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				ChatroomInfo info = new ChatroomInfo(json.getString("chrmId"), json.getString("name"));
				list.add(info);
			}
		} catch (Exception e) {
			log.error("rongyun getChatRoom error.",e);
			throw new ServiceException(e);
		}
		
		return list;
	}
	
	// 获取token
	public  String getToken(String userId,String name,String portraitUri){
		String token = null;
		try {
			SdkHttpResult result = RongYunSDK.getToken(key, secret, userId, name,portraitUri, FormatType.json);
			log.info(TypeUtil.typeToString("getToken:", result));
			JSONObject obj = new JSONObject(result.getResult());
			token = obj.getString("token");
		} catch (Exception e) {
			log.error("rongyun gettoken error.",e);
		}
		return token;
	}
	
	public  boolean refreshUser(String userId,String name,String portraitUri) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.refreshUser(key, secret, userId, name,portraitUri, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun joinGroup error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}

	// 创建聊天室
	public  boolean createChatRoom(String id,String name) throws ServiceException{
		List<ChatroomInfo> chats = new ArrayList<ChatroomInfo>();
		chats.add(new ChatroomInfo(id,name));
		
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.createChatroom(key, secret, chats, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun createChatRoom error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	// 加入群
	public  boolean joinGroup(String userId,String chatRoomId,String chatRoomName) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.joinGroup(key, secret, userId, chatRoomId, chatRoomName, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun joinGroup error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	// 发送聊天室消息
	public  boolean publishChatroomMessage(String chatroomId,ChatRoomMessage msg) throws ServiceException{
		msg.getContent().put("roomId", chatroomId);
		return publishChatroomMessage("9",ImmutableList.of(chatroomId), msg);
	}
	public  boolean publishChatroomMessage(String fromUserId,List<String> toIds,Message msg) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.publishChatroomMessage(key, secret, fromUserId, toIds, msg , FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
			log.info("publish chatroom message :"+ msg.toString() +"  to:"+TypeUtil.typeToString("toIds", toIds)+"  result:"+TypeUtil.typeToString("result", result));
		} catch (Exception e) {
			log.error("rongyun publishChatroomMessage error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	//发送一对一自定义消息
	public  boolean publishSystemMessage(String toId , Message msg) throws ServiceException{
		int code = -1;
		try {
			String fromUserId = "742956173212401664";
			SdkHttpResult result = RongYunSDK.publishSystemMessage(key, secret, fromUserId, ImmutableList.of(toId), msg, null, null, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
			log.info("publish system message :"+ msg.toString() +"  result:"+TypeUtil.typeToString("result", result));
		} catch (Exception e) {
			log.error("rongyun system message error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
		
		
	//发送系统消息
	public  boolean publishSystemMessage(List<String> toIds,Message msg) throws ServiceException{
		return publishSystemMessage(toIds, msg, null, "{\"type\":\"3\"}");
	}
		
	//发送系统消息
	public  boolean publishSystemMessage(List<String> toIds,Message msg, String pushContent, String pushData) throws ServiceException{
		int code = -1;
		try {
			String fromUserId = Loader.getInstance().getProps("user.system");
			SdkHttpResult result = RongYunSDK.publishSystemMessage(key, secret, fromUserId, toIds, msg, pushContent, pushData, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
			log.info("publish system message :"+ msg.toString() +"  result:"+TypeUtil.typeToString("result", result));
		} catch (Exception e) {
			log.error("rongyun system message error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	public  boolean publishBroadcastMessage(Message msg, String pushContent, String pushData, String os) throws ServiceException{
		int code = -1;
		try {
			String fromUserId = Loader.getInstance().getProps("user.system");
			SdkHttpResult result = RongYunSDK.broadcastMessage(key, secret, fromUserId, msg, pushContent, pushData, os, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
			log.info("publish broadcast message :"+ msg.toString() +"  result:"+TypeUtil.typeToString("result", result));
		} catch (Exception e) {
			log.error("rongyun system message error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	
	
	public  boolean publishChatroomMessage(String fromUserId,List<String> toIds,String content) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.publishChatroomMessage(key, secret, fromUserId, toIds,new TxtMessage(content), FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun publishChatroomMessage error.",e);
			throw new ServiceException(e);
		}
		
		return code==200;
	}
	
	// 销毁聊天室
	public  boolean destroyChatroom(String... chatId) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.destroyChatroom(key, secret, Arrays.asList(chatId), FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun destroyChatroom error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	// 获取消息历史记录下载地址
	public  String getMessageHistoryUrl(String userId) throws ServiceException{
		String token = null;
		
		try {
			SdkHttpResult result = RongYunSDK.getMessageHistoryUrl(key, secret, userId,FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			token = obj.getString("token");
		} catch (Exception e) {
			log.error("rongyun getMessageHistoryUrl error.",e);
			throw new ServiceException(e);
		}
		
		return token;
	}
	
	// 删除历史记录(只是删除了历史记录，不会删除消息)
	public  boolean deleteMessageHistory(String userId) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.deleteMessageHistory(key, secret, userId, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun destroyChatroom error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}

	// 封禁用户相关操作**********begin**********封禁用户相关操作//
	
	/**
	 * 踢人[直播间操作]
	 * @param chatroomId
	 * @param userId
	 * @param minutes
	 * @return
	 * @throws ServiceException
	 */
	public  boolean kickChatRoomUser(String chatroomId,String userId,int minutes) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.blockChatRoomUser(key, secret, chatroomId,userId, minutes,FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun kickChatRoomUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	/**
	 * 移除踢出人[直播间操作]
	 * @param chatroomId
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public  boolean rollbackKickChatRoomUser(String chatroomId,String userId) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.rollbackBlockChatRoomUser(key, secret, chatroomId, userId, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun kickChatRoomUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	
	
	/**
	 * 禁言[直播间操作]
	 * @param chatroomId
	 * @param userId
	 * @param minute
	 * @return
	 * @throws ServiceException
	 */
	public  boolean gagChatRoomUser(String chatroomId,String userId,int minute) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.gagChatRoomUser(key, secret, chatroomId,userId,minute, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun blockChatRoomUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	/**
	 * 移除禁言[直播间操作]
	 * @param chatroomId
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public  boolean rollbackChatRoomUser(String chatroomId,String userId) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.rollbackChatRoomUser(key, secret, chatroomId,userId, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun blockChatRoomUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	//封用户
	public  boolean blockUser(String userId,int minute) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.blockUser(key, secret, userId,minute, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun blockUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	// 查询封禁用户
	public  Map<String, Date> queryBlockUsers() throws ServiceException{
		Map<String, Date> users = new HashMap<String, Date>();
		try {
			SdkHttpResult result = RongYunSDK.queryBlockUsers(key, secret,FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			JSONArray array = obj.getJSONArray("users");
			for (int i = 0; i < array.length(); i++) {
				JSONObject user = array.getJSONObject(i);
				users.put(user.getString("userId"), DateUtil.parseDate(user.getString("time")));
			}
		} catch (Exception e) {
			log.error("rongyun queryBlockUsers error.",e);
			throw new ServiceException(e);
		}
		return users;
	}
	
	// 解除封禁
	public  boolean unblockUser(String userId) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.unblockUser(key, secret, userId, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun blockUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	// 封禁用户相关操作**********end**********封禁用户相关操作//

	// 检查用户在线状态
	public  boolean checkOnline(String userId) throws ServiceException{
		String status = "";
		try {
			SdkHttpResult result = RongYunSDK.checkOnline(key, secret, userId, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			status = obj.getString("status");
		} catch (Exception e) {
			log.error("rongyun checkOnline error.",e);
			throw new ServiceException(e);
		}
		return status.equals("1");
	}
	
	/**
	 * 加入黑名单
	 * @param userId
	 * @param toBlackIds
	 * @return
	 * @throws ServiceException
	 */
	public  boolean blackUser(String userId,List<String> toBlackIds) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.blackUser(key, secret, userId,toBlackIds, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun blockUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
		
	/**
	 *  解除黑名单
	 * @param userId
	 * @param toBlackIds
	 * @return
	 * @throws ServiceException
	 */
	public  boolean unblackUser(String userId,List<String> toBlackIds) throws ServiceException{
		int code = -1;
		try {
			SdkHttpResult result = RongYunSDK.unblackUser(key, secret, userId,toBlackIds, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun unblackUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	/**
	 * 查询黑名单
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public  List<String> queryblackUser(String userId) throws ServiceException{
		List<String> blackUsers = new ArrayList<String>();
		try {
			SdkHttpResult result = RongYunSDK.QueryblackUser(key, secret,userId,FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			JSONArray array = obj.getJSONArray("users");
			for (int i = 0; i < array.length(); i++) {
				blackUsers.add(array.getString(i));
			}
		} catch (Exception e) {
			log.error("rongyun QueryblackUser error.",e);
			throw new ServiceException(e);
		}
		return blackUsers;
	}


	// 给用户打标签
	public  boolean setUserTag(String userId,String... tags) throws ServiceException{
		int code = -1;
		try {
			UserTag tag = new UserTag();
			tag.setTags(tags);
			tag.setUserId(userId);
			SdkHttpResult result = RongYunSDK.setUserTag(key, secret, tag, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun unblackUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}
	
	public  boolean push(String userId,String... tags) throws ServiceException{
		int code = -1;
		try {
			PushMessage message = createPushMessage();// 融云消息
			// PushMessage message = createPushMessage2();//不落地push
			SdkHttpResult result = RongYunSDK.push(key, secret, message, FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			code = obj.getInt("code");
		} catch (Exception e) {
			log.error("rongyun unblackUser error.",e);
			throw new ServiceException(e);
		}
		return code==200;
	}

	/**
	 * 创建发送落地消息的内容
	 */
	private static PushMessage createPushMessage()
			throws UnsupportedEncodingException {
		List<String> osList = new ArrayList<>();
		osList.add("ios");
		osList.add("android");

		TagObj tag = new TagObj();
		tag.setIs_to_all(false);// 给全量用户发 设置为true则下面标签和userids将无效

		// 给打标签的发则设置标签
		List<String> tagas = new ArrayList<String>();
		tagas.add("a");
		tagas.add("b");
		tagas.add("3");
		tag.setTag(tagas);

		// 给特定用户ID发,优先级高于上面的标签
		List<String> tagus = new ArrayList<String>();
		tagus.add("123");
		tagus.add("456");
		tag.setUserid(tagus);

		PushMessage pmsg = new PushMessage();
		pmsg.setPlatform(osList);
		PushNotification noti = new PushNotification();
		noti.setAlert("ddd");
		noti.setAndroid(createPush());
		noti.setIos(createPush());
		pmsg.setNotification(noti);

		// 下面两个参数的有无 控制发送的落地消息还是不落地的push
		// PushMessage实体中的 fromuserid 和 message为null即为不落地的push
		pmsg.setFromuserid("fromuseId1");
		MsgObj msg = new MsgObj();
		TxtMessage message = new TxtMessage("hello", "one extra");
		msg.setContent(message.toString());
		msg.setObjectName(message.getType());
		pmsg.setMessage(msg);
		// 上面两个参数的有无 控制发送的落地消息还是不落地的push

		pmsg.setAudience(tag);
		log.info(pmsg.toString());
		return pmsg;
	}

	/**
	 * 创建发送不落地的push内容
	 */
	private static PushMessage createPushMessage2()
			throws UnsupportedEncodingException {
		List<String> osList = new ArrayList<>();
		osList.add("ios");
		osList.add("android");

		TagObj tag = new TagObj();
		tag.setIs_to_all(false);// 给全量用户发 设置为true则下面标签和userids将无效

		// 给打标签的发则设置标签
		List<String> tagas = new ArrayList<String>();
		tagas.add("a");
		tagas.add("b");
		tagas.add("3");
		tag.setTag(tagas);

		// 给特定用户ID发,优先级高于上面的标签
		List<String> tagus = new ArrayList<String>();
		tagus.add("123");
		tagus.add("456");
		tag.setUserid(tagus);

		PushMessage pmsg = new PushMessage();
		pmsg.setPlatform(osList);
		PushNotification noti = new PushNotification();
		noti.setAlert("ddd");
		noti.setAndroid(createPush());
		noti.setIos(createPush());
		pmsg.setNotification(noti);

		pmsg.setAudience(tag);
		log.info(pmsg.toString());
		return pmsg;
	}

	private static PlatformNotification createPush() {
		PlatformNotification data = new PlatformNotification();
		data.setAlert("override alert");
		Map<String, String> map = new HashMap<>();
		map.put("id", "1");
		map.put("name", "2");
		data.setExtras(map);
		return data;
	}

	public List<Long> queryChatRoomUsers(String chatroomId) throws ServiceException {
		List<Long> users = new ArrayList<Long>();
		try {
			SdkHttpResult result = RongYunSDK.queryChatRoomUsers(key, secret, chatroomId,FormatType.json);
			JSONObject obj = new JSONObject(result.getResult());
			JSONArray array = obj.getJSONArray("users");
			for (int i = 0; i < array.length(); i++) {
				JSONObject user = array.getJSONObject(i);
				long uid = Long.parseLong(user.getString("id"));
				users.add(uid);
			}
		} catch (Exception e) {
			log.error("rongyun queryBlockUsers error.",e);
			throw new ServiceException(e);
		}
		return users;
	}

	@Override
	public void nodeChildrenChanged(WatchedEvent event) {
	}

	@Override
	public void connected(WatchedEvent event) {
	}

	public String getKey() {
		return key;
	}

	public String getSecret() {
		return secret;
	}
	
	
}
