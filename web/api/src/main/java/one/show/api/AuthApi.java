package one.show.api;

import java.util.Map;

import one.show.common.exception.ReturnException;
import one.show.common.exception.ServiceException;
import one.show.common.im.ChatRoomMessage;
import one.show.common.im.IMUtils;
import one.show.common.im.MessageFactory;
import one.show.service.UserService;
import one.show.service.VideoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import one.show.user.thrift.view.UserView;
import one.show.video.thrift.view.LiveView;

@Controller
@RequestMapping("/auth") 
public class AuthApi extends BaseApi {

	private static final Logger log = LoggerFactory.getLogger(AuthApi.class);
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private VideoService videoService;
	
	/**
	 * 踢人
	 * @return
	 */
	@RequestMapping(value = "/kick")
	@ResponseBody
	public Map<String, String> kick(@RequestParam(required = true, value = "uid") final Long uid,
				@RequestParam(required = true, value = "roomId") final Long roomId,
				@RequestParam(required = true, value = "minute") final Integer minute) throws ReturnException{
	
		UserView userView = getUserView();
		UserView tUser = roomAuth(uid, roomId);
		
		try {
			
			ChatRoomMessage msg = MessageFactory.createForbiddenMessage(userView.getId(),userView.getNickname(), tUser.getId(), tUser.getNickname(), 1);
			IMUtils.getInstants().publishChatroomMessage(roomId.toString(), msg);
		} catch (ServiceException e) {
			log.error("block chatroom user error.",e);
		}
		
		//融云异步踢出，避免用户在直播间内收不到被踢消息
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000 * 5);
					IMUtils.getInstants().kickChatRoomUser(roomId.toString(), uid.toString(),minute);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				
			}
		}).start();
		
		
		return success();
	}
	
	/**
	 * 禁言
	 * time 分钟
	 * @return
	 */
	@RequestMapping(value = "/block")
	@ResponseBody
	public Map<String, String> block(@RequestParam(required = true, value = "uid") Long uid,
			@RequestParam(required = true, value = "roomId") Long roomId,
			@RequestParam(required = true, value = "minute") Integer minute) throws ReturnException {
		UserView userView = getUserView();
		UserView tUser = roomAuth(uid, roomId);
         
		try {
			IMUtils.getInstants().gagChatRoomUser(roomId.toString(), uid.toString(), minute);
			
			ChatRoomMessage msg = MessageFactory.createForbiddenMessage(userView.getId(), userView.getNickname(), tUser.getId(), tUser.getNickname(), 2);
			IMUtils.getInstants().publishChatroomMessage(roomId.toString(), msg);
		} catch (ServiceException e) {
			log.error("block chatroom user error.",e);
		}
		return success();
	}

	
	/**
	 * 权限验证
	 * @param tid  目标用户
	 * @param roomId 房间id
	 * @return 
	 * @throws ReturnException
	 */
	private UserView roomAuth(Long tid, Long roomId) throws ReturnException{
		// 当前登陆用户
		UserView userView = getUserView();
		
		if (userView == null) {
			throw new ReturnException("2019");
		}
		
		//不能禁言自己
		if (userView.getId() == tid.longValue()){
			throw new ReturnException("5009");
		}
		

		//主播
		long masterId = 0l;
		LiveView liveView = null;
		try {
			liveView = videoService.getLiveById(roomId);
		} catch (ServiceException e) {
			log.error(e.getMessage() , e);
		}
		
		if (liveView != null){
			masterId  = liveView.getUid();
		}else{
			throw new ReturnException("7001");
		}
		
		//房管判断和巡管
		if (masterId != userView.getId()){
			
			Boolean admin = false;
			try {
				admin = userService.isRoomAdmin(masterId, userView.getId());
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
			log.info("isRoomAdmin uid="+roomId+",tid="+userView.getId()+" result="+admin);
			
			if (userView.getIsAdmin() == 0  && !admin){
				throw new ReturnException("5003");
			}
		}
		
		UserView tUser = null;
		try {
			tUser = userService.findUserById(tid);
		} catch (ServiceException e) {
			log.error("find user error.",e);
		}
		if(tUser==null){
			throw new ReturnException("2005");
		}

		//巡管不能被踢
		if (tUser.getIsAdmin() == 1){
			throw new ReturnException("5003");
		}
		//主播不能被踢
		if (tid == masterId){
			throw new ReturnException("5003");
		}
		Boolean admin = false;
		try {
			admin = userService.isRoomAdmin(masterId, tid);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
		}
		if(admin){
			throw new ReturnException("5003");
		}
		return tUser;
	}
	
}