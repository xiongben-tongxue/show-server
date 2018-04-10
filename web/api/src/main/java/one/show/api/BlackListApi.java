package one.show.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.Adapter;
import one.show.common.im.IMUtils;
import one.show.common.local.XThreadLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import one.show.user.thrift.view.UserView;

@Controller
@RequestMapping("/black") 
public class BlackListApi extends BaseApi {
	
	private static final Logger log = LoggerFactory.getLogger(BlackListApi.class);
	

	/**
	 * 
	 * @param tid
	 * @param action 1添加  0删除
	 * @return
	 */
	@RequestMapping(value = "/black")
	@ResponseBody
	public Map<String, Object> black(
			@RequestParam(value = "tid", required = true) Long tid,
			@RequestParam(value = "action", required = true) int action){

		Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		if (currentUser == null) {
			return error("2019");
		}

		if (action == 1) {
			try {
				userService.add2BlackList(currentUser, tid);
				
				List<String> toBlackIds = new ArrayList<String>();
				toBlackIds.add(String.valueOf(tid));
				
				IMUtils.getInstants().blackUser(String.valueOf(currentUser), toBlackIds);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return error();
			}
			
		}else {
			try {
				userService.removeFromBlackList(currentUser, tid);
				
				List<String> toBlackIds = new ArrayList<String>();
				toBlackIds.add(String.valueOf(tid));
				
				IMUtils.getInstants().unblackUser(String.valueOf(currentUser), toBlackIds);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				return error();
			}
			
		}


	    return success();
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(
			@RequestParam(value = "cursor", required = false) Integer cursor,
			@RequestParam(value = "count", required = false) Integer count){
		
		final Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		if (currentUser == null) {
			return error("2019");
		}
		
		if (cursor==null||cursor < 0) {
			cursor = 0;
		}
		if (count==null || count < 0 || count>20) {
			count = 20;
		}


		List<Long> blackUsers = null;
		try {
			blackUsers=userService.findBlackList(currentUser, cursor, count);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		List<UserView> blackUserViews =null;
		try {
			if(blackUsers!=null){
				blackUserViews=userService.findUserListByIds(blackUsers);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		Map<String, Object> result = new HashMap<>();

		List<Map<String, Object>> userList = new ArrayList<>();
		if(blackUserViews!=null){
			for (UserView blackUserView : blackUserViews) {
				Map<String, Object> map = new HashMap<>();
				map.put("uid", blackUserView.getId());
				map.put("nickName", blackUserView.getNickname());
				map.put("gender", blackUserView.getGender());
				map.put("avatar", Adapter.getAvatar(blackUserView.getProfileImg()));
				
				userList.add(map);
			}
		}
		

		int next_cursor = cursor + count;
		if (userList == null || userList.size() == 0) {
			next_cursor = -1;
		}
		result.put("next_cursor", next_cursor);
		result.put("list", userList);
		
		return success(result);
		
	}
}