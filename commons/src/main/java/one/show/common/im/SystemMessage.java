package one.show.common.im;

import io.rong.models.Message;
import io.rong.util.GsonUtil;

import java.util.Map;

public class SystemMessage extends Message{
	
	public static int UPDATE_USER_NICKNAME = 1;//更新用户昵称
	public static int UPDATE_USER_ARATAR = 2;//更新用户头像
	public static int UPDATE_USER_SHOWCOIN = 3;//更新用户ShowCoin
	public static int UPDATE_USER_VIP = 4;//更新用户VIP
	public static int UPDATE_USER_ADMIN = 5;//更新用户巡管身份
	public static int UPDATE_USER_PID = 6;//更新用户靓号
	public static int UPDATE_USER_DESC = 7;//更新用户签名
	
	private Map content;
	
	public SystemMessage(String type, Map data) {
		super();
		this.type = type;
		this.content = data;
	}

	public Map getContent() {
		return content;
	}

	public void setContent(Map data) {
		this.content = data;
	}

	@Override
	public String toString() {
		return GsonUtil.toJson(this, SystemMessage.class);
	}
}
