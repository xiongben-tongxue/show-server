package one.show.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.show.common.Adapter;
import one.show.common.cache.LocalCache;
import one.show.common.local.XThreadLocal;
import one.show.service.SearchService;
import one.show.service.UserService;
import one.show.user.thrift.view.UserView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tencent.service.PayService;

@Controller
public class SearchApi extends BaseApi {
	private static final Logger log = LoggerFactory.getLogger(SearchApi.class);
	@Autowired
	private SearchService searchService;
	@Autowired
	private PayService payService;
	@Autowired
	private UserService userService;
	/**
	 *
	 * @param keyword
	 * @param cursor
	 * @param count
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search_user")
	@ResponseBody
	public Map<String, Object> search_user(@RequestParam(value = "keyword", required = true) String keyword,
			@RequestParam(value = "cursor", required = false) Integer cursor,
			@RequestParam(value = "count", required = false) Integer count){
		Map result = new HashMap<String, Object>();
		
		if (keyword == null || keyword.trim().equals("")) {
			return error("10001");
		}
		// keyword首字母去掉以下字符
//		String exclude = "/\\-:()\"![]{}#%^*+=?~";
//		char[] excludeArray = exclude.toCharArray();
//		
//		for(int i=0; i<excludeArray.length; i++){
//			
//			if(keyword.startsWith(String.valueOf(excludeArray[i]))){
//				keyword = keyword.substring(1);
//				i = -1;
//			}
//		}
		
		
		keyword = keyword.replaceAll("\\ |\\(|\\)|\\[|\\]|\\{|\\}|\\/|\\^|\\-|\\$|\\||\\?|\\*|\\+|\\.|\\\"|\\!|\\:|\\?|\\~|\\+|\\#|\\%|\\\\", "");
		if (keyword.equals("")) {
			return error("10001");
		}
		
		if (cursor == null || cursor < 0) {
			cursor = 0;
		}
		if (count == null || count < 0 || count > 20) {
			count = 20;
		}
		final String keywordnow = keyword;
		final int cursornow = cursor;
		final int countnow = count;

		final Long currentUser = XThreadLocal.getInstance().getCurrentUser();
		List<Map<String, Object>> userSearchList = null;
		try {
			userSearchList = new LocalCache<List<Map<String, Object>>>() {
				@Override
				public List<Map<String, Object>> getAliveObject() throws Exception {
					List<Map<String, Object>> userSearchViewList = new ArrayList<Map<String, Object>>();
					List<Long> ids = searchService.searchUser(keywordnow, cursornow, countnow);
					if(ids == null || ids.size() == 0){
						return null;
					}
					
					
					List<UserView> userList = userService.findUserListByIds(ids);
					
					for(UserView user : userList){
						Map<String, Object> map = new HashMap<>();
						map.put("uid", user.getId());
						
						map.put("gender", user.getGender());
						map.put("avatar", Adapter.getAvatar(user.getProfileImg()));
						map.put("intro", user.getDescription());
						map.put("nick_name", user.getNickname());
						map.put("is_live", user.getIslive());
						map.put("pid", user.getPopularNo());
						map.put("fan_level", user.getFanLevel());
						
						userSearchViewList.add(map);	
					}
					
					return userSearchViewList;
				}
			}.put(30 * 60, "query_userinfo_" + keyword + "_" + cursor + "_" + count);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return error();
			
		}
		
		if (userSearchList != null){
			List<Long> uids = new ArrayList<Long>();
			for (Map<String, Object> user : userSearchList) {
				uids.add((Long) user.get("uid"));
			}

		}

		int next_cursor = cursor + count;
		if (userSearchList == null || count > userSearchList.size()) {
			next_cursor = -1;
		}
		
		result.put("next_cursor", next_cursor);
		result.put("list", userSearchList == null?new ArrayList():userSearchList);
		return success(result);
	}
	
	public static void main(String[] args) {
		String keyword = "///哈\\哈 ()@#$%^&*()))_+";
		// keyword首字母去掉以下字符
		String exclude = "/\\-:()\"![]{}#%^*+=?~";
		char[] excludeArray = exclude.toCharArray();
		
		for(int i=0; i<excludeArray.length; i++){
			
			if(keyword.startsWith(String.valueOf(excludeArray[i]))){
				keyword = keyword.substring(1);
				i=-1;
			}
		}
		
		keyword = keyword.replaceAll("\\ |\\(|\\)|\\[|\\]|\\{|\\}|\\/|\\^|\\-|\\$|\\||\\?|\\*|\\+|\\.|\\\"|\\!|\\:|\\?|\\~|\\+|\\#|\\%|\\\\", "");
		System.out.println("keyword="+keyword);
	}

	
}
