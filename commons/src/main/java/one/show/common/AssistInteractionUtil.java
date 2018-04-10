package one.show.common;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import one.show.common.client.redis.JedisUtil;
import one.show.common.mq.Publisher;
import one.show.common.mq.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 促进互动相关操作的类
 * 
 * @author wanglei
 * @date 2017年9月19日
 */
public class AssistInteractionUtil {
	
	private static final Logger log = LoggerFactory.getLogger(AssistInteractionUtil.class);
	
	public static Integer ACT_CHAT = 1;  //互动类型：聊天
	public static Integer ACT_SEND_GIFT= 2;  //互动类型：送礼物

	public static Integer DEFAULT_TTS = 7*24*60*60;//redis key 默认过期时间
	
	public static String FOLLOW = "assist_follow"; // 单向关注
	public static Integer FOLLOW_DELAY = 10 * 60; // 触发延迟时间，单位秒

	public static String FRIEND = "assist_friend"; // 双向关注
	public static Integer FRIEND_DELAY = 30 * 60; // 触发延迟时间，单位秒

	public static String CHAT = "assist_chat"; // 单向发起聊天
	public static Integer CHAT_DELAY = 15 * 60; // 触发延迟时间，单位秒
	public static Integer CHAT_TTS = 30 * 24 * 60 * 60; // 有效期

	public static String GIVE_GIFT = "assist_give_gift"; // 长时间无互动，赠送免费礼物
	public static Integer GIVE_GIFT_DELAY = 72 * 60 * 60; // 触发延迟时间，单位秒
	public static Integer GIVE_GIFT_TTS = 30 * 24 * 60 * 60; // 有效期

	public static String MALE_SEND_GIFT = "assist_male_send_gift"; // 单向男生送礼物
	public static Integer MALE_SEND_GIFT_DELAY = 10 * 60; // 触发延迟时间，单位秒
	public static Integer MALE_SEND_GIFT_TTS = 30 * 24 * 60 * 60; // 有效期

	public static String FEMALE_SEND_GIFT = "assist_female_send_gift"; // 单向女生送礼物
	public static Integer FEMALE_SEND_GIFT_DELAY = 10 * 60; // 触发延迟时间，单位秒
	public static Integer FEMALE_SEND_GIFT_TTS = 30 * 24 * 60 * 60; // 有效期
	
	public static String FREE_GIFT_NUMBER = "assist_free_gift_number"; //免费礼物个数
	public static Integer FREE_GIFT_TTS = 3 * 24 * 60 * 60; //免费礼物有效期
	
	
	public static void action(Long uid,Long tid,Integer type,Integer relation,Integer gender){
//		if (type.equals(ACT_CHAT)) {
//			deleteAssist(uid, tid, FOLLOW); //删除单向关注无互动提示
//			deleteAssist(uid, tid, FRIEND); //删除双向关注无互动提示
//			deleteAssist(tid, uid, FRIEND); //删除双向关注无互动提示
//			
//			//处理单向聊天互动提示
//			if (existAssist(tid, uid, CHAT)){
//				if (!isHandle(tid, uid, CHAT)) {
//					deleteAssist(tid, uid, CHAT);
//				}
//			} else {
//				if (relation == 0 ){
//					createAssist(uid, tid, CHAT);
//				}
//			}
//			//处理互相关注无互动提示
//			if (existAssist(tid, uid, GIVE_GIFT)){
//				if (!isHandle(tid, uid, GIVE_GIFT)) {
//					deleteAssist(tid, uid, GIVE_GIFT);
//				}
//			} else {
//				if (relation == 3 ){
//					createAssist(uid, tid, GIVE_GIFT);
//				}
//			}
//			//处理除单向男生送礼物互动提示
//			if (existAssist(tid, uid, MALE_SEND_GIFT)){
//				if (!isHandle(tid, uid, MALE_SEND_GIFT)) {
//					deleteAssist(tid, uid, MALE_SEND_GIFT);
//				}
//			}
//			//处理除单向女生送礼物互动提示
//			if (existAssist(tid, uid, FEMALE_SEND_GIFT)){
//				if (!isHandle(tid, uid, FEMALE_SEND_GIFT)) {
//					deleteAssist(tid, uid, FEMALE_SEND_GIFT);
//				}
//			}
//		} else if (type.equals(ACT_SEND_GIFT)) {
//			deleteAssist(uid, tid, FOLLOW); //删除单向关注无互动提示
//			deleteAssist(uid, tid, FRIEND); //删除双向关注无互动提示
//			deleteAssist(tid, uid, FRIEND); //删除双向关注无互动提示
//			
//			//处理除单向男生送礼物互动提示
//			if (existAssist(tid, uid, MALE_SEND_GIFT)){
//				if (!isHandle(tid, uid, MALE_SEND_GIFT)) {
//					deleteAssist(tid, uid, MALE_SEND_GIFT);
//				}
//			} else {
//				if (gender == 1){
//					createAssist(uid, tid, MALE_SEND_GIFT);
//				}
//			}
//			//处理除单向女生送礼物互动提示
//			if (existAssist(tid, uid, FEMALE_SEND_GIFT)){
//				if (!isHandle(tid, uid, FEMALE_SEND_GIFT)) {
//					deleteAssist(tid, uid, FEMALE_SEND_GIFT);
//				}
//			} else {
//				if (gender == 2) {
//					createAssist(uid, tid, FEMALE_SEND_GIFT);
//				}
//			}
//			//处理单向聊天互动提示
//			if (existAssist(tid, uid, CHAT)){
//				if (!isHandle(tid, uid, CHAT)) {
//					deleteAssist(tid, uid, CHAT);
//				}
//			}
//			//处理互相关注无互动提示
//			if (existAssist(tid, uid, GIVE_GIFT)){
//				if (!isHandle(tid, uid, GIVE_GIFT)) {
//					deleteAssist(tid, uid, GIVE_GIFT);
//				}
//			}
//		} 
	}

	/**
	 * 生成促进互动的内容
	 * 
	 * @param uid
	 * @param tid
	 */
	public static void createAssist(Long uid, Long tid, String type) {
//		boolean isDebug = false;
//		if (isDebug) {
//			FOLLOW_DELAY = 3 * 60;
//			FRIEND_DELAY = 3 * 60;
//			CHAT_DELAY = 3 * 60;
//			GIVE_GIFT_DELAY = 3 * 60;
//			MALE_SEND_GIFT_DELAY = 3 * 60;
//			FEMALE_SEND_GIFT_DELAY = 3 * 60;
//			
//			CHAT_TTS = 10 * 60;
//			GIVE_GIFT_TTS = 10 * 60;
//			MALE_SEND_GIFT_TTS = 10 * 60;
//			FEMALE_SEND_GIFT_TTS = 10 * 60;
//		}
//		
//		int currentTime = (int) (System.currentTimeMillis() / 1000);
//		String key = type + "_" + uid + "_" + tid;
//		
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("uid", String.valueOf(uid));
//		map.put("tid", String.valueOf(tid));
//		map.put("createTime", String.valueOf(currentTime));
//		map.put("isHandle", "0");
//		
//		if (type.equals(FOLLOW)) {
//			map.put("uidMessage", "你已经关注他啦，快问候下吧");
//			map.put("tidMessage", "这人正在关注你，快回应下吧");
//			map.put("delay", String.valueOf(FOLLOW_DELAY));
//			
//			JedisUtil.hMSet(key, map);
//			JedisUtil.expire(key, DEFAULT_TTS);
//		} else if (type.equals(FRIEND)) {
//			map.put("uidMessage", "你们是好友哦，聊聊会更熟悉");
//			map.put("tidMessage", "你们是好友哦，聊聊会更熟悉");
//			map.put("delay", String.valueOf(FRIEND_DELAY));
//			
//			JedisUtil.hMSet(key, map);
//			JedisUtil.expire(key, DEFAULT_TTS);
//			
//			//成为好友，删除单向关注assist
//			deleteAssist(tid, uid, FOLLOW);
//		} else if (type.equals(CHAT)) {
//			map.put("uidMessage", "互相关注下，可以成为好友哦");
//			map.put("tidMessage", "互相关注下，可以成为好友哦");
//			map.put("delay", String.valueOf(CHAT_DELAY));
//			
//			JedisUtil.hMSet(key, map);
//			JedisUtil.expire(key, CHAT_TTS);
//		} else if (type.equals(GIVE_GIFT)) {
//			map.put("uidMessage", "快送个小礼物，增进下熟悉度");
//			map.put("tidMessage", "快送个小礼物，增进下熟悉度");
//			map.put("delay", String.valueOf(GIVE_GIFT_DELAY));
//			
//			JedisUtil.hMSet(key, map);
//			JedisUtil.expire(key, GIVE_GIFT_TTS);
//		} else if (type.equals(MALE_SEND_GIFT)) {
//			map.put("uidMessage", "");
//			map.put("tidMessage", "他给你送礼物啦，快回应下哦");
//			map.put("delay", String.valueOf(MALE_SEND_GIFT_DELAY));
//			
//			JedisUtil.hMSet(key, map);
//			JedisUtil.expire(key, MALE_SEND_GIFT_TTS);
//		} else if (type.equals(FEMALE_SEND_GIFT)) {
//			map.put("uidMessage", "");
//			map.put("tidMessage", "回个小礼物能提升魅力值哦");
//			map.put("delay", String.valueOf(FEMALE_SEND_GIFT_DELAY));
//			
//			JedisUtil.hMSet(key, map);
//			JedisUtil.expire(key, FEMALE_SEND_GIFT_TTS);
//		}
//		try {
//			map.put("type", type);
//			String message = JacksonUtil.writeToJsonString(map);
//			Publisher.getInstance().sendDelayMessage(message, Queue.SUPE_ASSIST, Long.parseLong(map.get("delay"))*1000);
//			log.info("pulish assist work,message:"+message);
//		} catch (JMSException e) {
//			log.error("publish assist work error.",e);
//		}
	}
	
	public static boolean existAssist(Long uid, Long tid, String type){
		String key = type + "_" + uid + "_" + tid;
		return JedisUtil.checkKeyExisted(key);
	}
	
	public static boolean isHandle(Long uid, Long tid, String type) {
		String key = type + "_" + uid + "_" + tid;
		if (JedisUtil.checkKeyExisted(key)) {
			String res = (String)JedisUtil.hGet(key, "isHandle");
			if ("1".equals(res)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static void handle(Long uid, Long tid, String type) {
		String key = type + "_" + uid + "_" + tid;
		JedisUtil.hSet(key, "isHandle", "1");
	}
	
	public static void incFreeGiftCount(Long uid, Long tid) {
		String key = FREE_GIFT_NUMBER + "_" + uid + "_" + tid;
		JedisUtil.setCount(key, 1l);
		JedisUtil.expire(key, FREE_GIFT_TTS);
	}
	
	public static void clearFreeGiftCount(Long uid, Long tid) {
		String key = FREE_GIFT_NUMBER + "_" + uid + "_" + tid;
		JedisUtil.delete(key);
	}
	
	public static Long getFreeGiftCount(Long uid, Long tid) {
		String key = FREE_GIFT_NUMBER + "_" + uid + "_" + tid;
		return JedisUtil.getCount(key);
	}

	/**
	 * 删除促进互动的内容
	 * 
	 * @param uid
	 * @param tid
	 */
	public static void deleteAssist(Long uid, Long tid, String type) {
		String key = type + "_" + uid + "_" + tid;
		JedisUtil.delete(key);
	}
	
	public static void main(String[] args) {
//		Map<String,String> map = new HashMap<String,String>();
//		int currentTime = (int) (System.currentTimeMillis() / 1000);
//		System.out.println(currentTime);
//		map.put("a", "1");
//		map.put("b", String.valueOf(currentTime));
//		JedisUtil.hMSet("testmap", map);
//		System.out.println(JedisUtil.hGet("assist_follow_903101303964696576_889831091605733376", "createTime"));
//		JedisUtil.expire("testmap",3);
//		System.out.println(JedisUtil.checkKeyExisted("testmap"));
//		JedisUtil.increase("xxx",1);
//		JedisUtil.expire("xxx",2);
//		JedisUtil.delete("xxx");
//		System.out.println(JedisUtil.getCount("xxx"));
//		JedisUtil.hSet("testmap", "a", "ccc");
//		JedisUtil.setCount("mytest", 1l);
		JedisUtil.delete("mytest");
		System.out.println(JedisUtil.getCount("mytest"));
	}

}
