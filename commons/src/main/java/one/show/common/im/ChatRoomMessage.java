package one.show.common.im;

import io.rong.models.Message;
import io.rong.util.GsonUtil;

import java.util.HashMap;
import java.util.Map;

import one.show.common.JacksonUtil;

public class ChatRoomMessage extends Message{
	
	public static int TYPE_GIFT = 1;//送礼
	public static int TYPE_UPGRADE = 2;//升级
	public static int TYPE_FLY = 3;//飘屏
	public static int TYPE_BEGIN = 4;//直播开始
	public static int TYPE_FINISH = 5;//直播结束
	public static int TYPE_INTERRUPT = 6;//直播中断
	public static int TYPE_HEART = 7;//飘心
	public static int TYPE_UPDATE_USERLIST = 8;//用户列表更新
	public static int TYPE_WELCOME = 9;//欢迎
	public static int TYPE_NOTICE = 10;//通知
	public static int TYPE_RANK = 11;//直播排行
	public static int TYPE_FORBIDDEN = 12;//禁言，踢人
	public static int TYPE_ADMIN = 13;//管理员
	public static int TYPE_FOLLOW_MASTER = 14;//关注主播
	
	private Map content;
	
	public ChatRoomMessage(String type,String data) {
		super();
		this.type = type;
		Map map = new HashMap();
		if (data != null && !data.equals("")){
			map = JacksonUtil.readJsonToObject(Map.class, data);;
		}
		this.content = map;
	}

	public Map getContent() {
		return content;
	}

	public void setContent(Map data) {
		this.content = data;
	}

	@Override
	public String toString() {
		return GsonUtil.toJson(this, ChatRoomMessage.class);
	}
}
