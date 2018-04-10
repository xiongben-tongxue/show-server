package one.show.common.im;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import one.show.common.JacksonUtil;
import one.show.common.exception.ServiceException;

public class MessageFactory {
	
	public static final String MESSAGE_TYPE="chatRoomMsg";
	
	public static final String SYSTEM_MESSAGE="systemMsg";
	
	/** {"giftId":1,"giftName":"","giftImg":"","num":10,"fromUid":"","fromNickName":"ces","fromUserImg":””,”count”:10}
	 * @throws ServiceException */
	public static ChatRoomMessage createGiftMessage(Map<String, Object> params) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			for(String key:params.keySet()){
				json.put(key, params.get(key));
			}
//			json.put("giftId", gift.getId());
//			json.put("giftName", gift.getName());
//			json.put("giftImg", gift.getImage());
//			json.put("giftIcon", gift.getIcon());
//			json.put("num", num);
//			json.put("count", count);
//			json.put("fromUid", user.getId()+"");
//			json.put("fromNickName",user.getNickname());
//			json.put("fromUserImg", user.getProfileImg());
			
			msg.put("type", ChatRoomMessage.TYPE_GIFT);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {"uid":"33","nickName":"ces","fanLevel":4}
	 * @throws ServiceException */
	public static ChatRoomMessage createUpgradeMessage(long uid,String nickName,int fanLevel) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("nickName", nickName);
			json.put("fanLevel", fanLevel);
			
			msg.put("type", ChatRoomMessage.TYPE_UPGRADE);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	

	/** {"uid":"","nickName":"ces","profileImg":"","content":""}
	 * @throws ServiceException */
	public static ChatRoomMessage createFlyMessage(long uid,String nickName,String profileMsg,int fanLevel,String content, Integer isVip) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("nickName", nickName);
			json.put("profileImg", profileMsg);
			json.put("isVip", isVip);
			json.put("fanLevel", fanLevel);
			json.put("content", content);
			
			msg.put("type", ChatRoomMessage.TYPE_FLY);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {"uid":"","type":""}
	 * @throws ServiceException */
	public static ChatRoomMessage createAdminMessage(long uid ,String nickName, Integer type) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("nickName", nickName);
			json.put("type", type);
			
			msg.put("type", ChatRoomMessage.TYPE_ADMIN);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	
	/** {"uid","nickName":"ces","fanLevel":1}
	 * @throws ServiceException */
	public static ChatRoomMessage createHeartMessage(long uid,String nickName,int fanLevel, int isAdmin) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("nickName", nickName);
			json.put("fanLevel", fanLevel);
			json.put("isAdmin", isAdmin);
			
			msg.put("type", ChatRoomMessage.TYPE_HEART);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {}
	 * @throws ServiceException */
	public static ChatRoomMessage createBeginMessage(long uid, String addr) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		
		try {
			json.put("uid", uid);
			json.put("addr", addr);
			msg.put("type", ChatRoomMessage.TYPE_BEGIN);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {}
	 * @throws ServiceException */
	public static ChatRoomMessage createFinishMessage(Map<String, Object> params) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		
		try {
			for(String key:params.keySet()){
				json.put(key, params.get(key));
			}
//			json.put("uid", uid);
//			json.put("reason", reason);
			msg.put("type", ChatRoomMessage.TYPE_FINISH);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {}
	 * @throws ServiceException */
	public static ChatRoomMessage createInterruptMessage(long uid) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		
		try {
			json.put("uid", uid);
			msg.put("type", ChatRoomMessage.TYPE_INTERRUPT);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** [{"uid":"","profileImg":""},...]
	 * @throws ServiceException */
	public static ChatRoomMessage createUpdateUseListMessage(int total,JSONArray  ranks) throws ServiceException{
		JSONObject msg = new JSONObject();
		try {
			msg.put("type", ChatRoomMessage.TYPE_UPDATE_USERLIST);
			msg.put("total", total);
			msg.put("data", ranks);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {"uid","nickName":"ces"}
	 * @throws ServiceException */
	public static ChatRoomMessage createWelcomeMessage(long uid,String nickname) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("nickName", nickname);
		
			msg.put("type", ChatRoomMessage.TYPE_WELCOME);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {"content":""
	 * @throws ServiceException */
	public static ChatRoomMessage createNoticeMessage(String content) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("content", content);
		
			msg.put("type", ChatRoomMessage.TYPE_NOTICE);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/**[{"uid":"","nickName":"","profileImg":"","spend":1000000},…… ]
	 * @throws ServiceException */
	public static ChatRoomMessage createRankMessage(List<Map<String, Object>> ranks) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			for(Map<String, Object> map:ranks){
				JSONObject json = new JSONObject();
				for(String key:map.keySet()){
					json.put(key, map.get(key));
				}
//					json.put("uid", r.getFromUid());
//					json.put("nickName", r.getFromNickName());
//					json.put("profileImg", r.getFromImg());
//					json.put("spend", r.getTotal());
				
				array.put(json);
			}
			msg.put("type", ChatRoomMessage.TYPE_RANK);
			msg.put("data", array);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	
	/** {"uid","nickName":"ces","forbid_type":1}
	 * @throws ServiceException */
	public static ChatRoomMessage createForbiddenMessage(long optUid, String optName,long uid,String nickname,int type) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("nickName", nickname);
			json.put("forbid_type", type);
			json.put("opt_uid", optUid);
			json.put("opt_nickName", optName);
		
			msg.put("type", ChatRoomMessage.TYPE_FORBIDDEN);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	/** {"uid":123,"nickName":"ces"}
	 * @throws ServiceException */
	public static ChatRoomMessage createFollowMasterMessage(long uid,String nickname) throws ServiceException{
		JSONObject msg = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("uid", uid);
			json.put("nickName", nickname);
		
			msg.put("type", ChatRoomMessage.TYPE_FOLLOW_MASTER);
			msg.put("data", json);
		} catch (JSONException e) {
			throw new ServiceException(e);
		}
		return new ChatRoomMessage(MESSAGE_TYPE,msg.toString());
	}
	
	
	public static SystemMessage createSystemMessage(int type, String value) throws ServiceException{
		Map date = new HashMap();
		date.put("type", type);
		date.put("value", value);
		return new SystemMessage(SYSTEM_MESSAGE, date);
	}

	public static void main(String[] args) {
		try {
			System.out.println(JacksonUtil.writeToJsonString(createBeginMessage(0l, "")));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
