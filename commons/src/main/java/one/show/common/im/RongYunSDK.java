package one.show.common.im;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.Message;
import io.rong.models.SdkHttpResult;
import io.rong.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class RongYunSDK extends ApiHttpClient {

	private static final String RONGCLOUDURI = "http://api.cn.ronghub.com";
	
	private static final String UTF8 = "UTF-8";

	//添加禁言聊天室成员 方法
	public static SdkHttpResult gagChatRoomUser(String appKey, String appSecret,String chatroomId,
			String userId, int minute, FormatType format) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(appKey,
				appSecret, RONGCLOUDURI + "/chatroom/user/gag/add." + format.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&chatroomId=").append(URLEncoder.encode(chatroomId, UTF8));
		sb.append("&minute=").append(
				URLEncoder.encode(String.valueOf(minute), UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}
	
	public static SdkHttpResult rollbackChatRoomUser(String appKey, String appSecret,String chatroomId,
			String userId, FormatType format) throws Exception {
		
		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(appKey,
				appSecret, RONGCLOUDURI + "/chatroom/user/gag/rollback." + format.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&chatroomId=").append(URLEncoder.encode(chatroomId, UTF8));
		
		HttpUtil.setBodyParameter(sb, conn);
		
		return HttpUtil.returnResult(conn);
	}
	
	public static SdkHttpResult blockChatRoomUser(String appKey, String appSecret,String chatroomId,
			String userId, int minute, FormatType format) throws Exception {
		
		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(appKey,
				appSecret, RONGCLOUDURI + "/chatroom/user/block/add." + format.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&chatroomId=").append(URLEncoder.encode(chatroomId, UTF8));
		sb.append("&minute=").append(
				URLEncoder.encode(String.valueOf(minute), UTF8));
		
		HttpUtil.setBodyParameter(sb, conn);
		
		return HttpUtil.returnResult(conn);
	}
	
	public static SdkHttpResult rollbackBlockChatRoomUser(String appKey, String appSecret,String chatroomId,
			String userId, FormatType format) throws Exception {
		
		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(appKey,
				appSecret, RONGCLOUDURI + "/chatroom/user/block/rollback." + format.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("userId=").append(URLEncoder.encode(userId, UTF8));
		sb.append("&chatroomId=").append(URLEncoder.encode(chatroomId, UTF8));
		
		HttpUtil.setBodyParameter(sb, conn);
		
		return HttpUtil.returnResult(conn);
	}
	
	///chatroom/user/query
	public static SdkHttpResult queryChatRoomUsers(String appKey, String appSecret,String chatroomId,FormatType format) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(appKey,
				appSecret, RONGCLOUDURI + "/chatroom/user/query." + format.toString());

		StringBuilder sb = new StringBuilder();
		sb.append("chatroomId=").append(URLEncoder.encode(chatroomId, UTF8));

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}
	
	public static SdkHttpResult broadcastMessage(String appKey, String appSecret, String fromUserId, Message msg, String pushContent, String pushData, String os, FormatType format) throws Exception {

		HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(appKey,
				appSecret, RONGCLOUDURI + "/message/broadcast." + format.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("fromUserId=").append(URLEncoder.encode(fromUserId, UTF8));
		sb.append("&objectName=").append(URLEncoder.encode(msg.getType(), UTF8));
		sb.append("&content=").append(URLEncoder.encode(msg.toString(), UTF8));
		if (pushContent != null){
			sb.append("&pushContent=").append(URLEncoder.encode(pushContent, UTF8));
		}
		
		if (pushData != null){
			sb.append("&pushData=").append(URLEncoder.encode(pushData, UTF8));
		}
		
		if (os != null){
			sb.append("&os=").append(URLEncoder.encode(os, UTF8));
		}
		

		HttpUtil.setBodyParameter(sb, conn);

		return HttpUtil.returnResult(conn);
	}
}
