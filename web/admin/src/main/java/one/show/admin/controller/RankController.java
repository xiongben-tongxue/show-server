/**
 * 
 */
package one.show.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import one.show.admin.tag.PageController;
import one.show.admin.view.UserRank;
import one.show.common.Adapter;
import one.show.common.TimeUtil;
import one.show.common.exception.ServiceException;
import one.show.service.RankService;
import one.show.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import one.show.stat.thrift.view.RankUserDailyView;
import one.show.stat.thrift.view.RankUserListView;
import one.show.user.thrift.view.UserView;

/**
 * Created by Andy on 18/4/13.
 *
 */

@Controller
@RequestMapping("/rank")
public class RankController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(RankController.class);
	
	@Autowired
	private RankService rankService;
	
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("pageController")  
    public PageController getPageController(){ 
        return new PageController();  
    }
	
	
	@RequestMapping("/userList.do")
	public ModelAndView list(@ModelAttribute(value="pageController") PageController pageController, 
			@RequestParam(required = false, value = "beginTime") Integer beginTime,
			@RequestParam(required = false, value = "endTime") Integer endTime,
			@RequestParam(required = false, value = "type") Integer type){

		ModelAndView mv = new ModelAndView();  
	        
		if (beginTime == null){
			beginTime = TimeUtil.dateToIntegerDay(new Date());
		}
		mv.addObject("beginTime", beginTime);  
		if (endTime == null){
			endTime = TimeUtil.dateToIntegerDay(new Date());
		}
		mv.addObject("endTime", endTime);  
		if (type == null){
			type = 1;
		}
		mv.addObject("type", type);   
				   
        try {
        	RankUserListView rankUserListView = rankService.findUserRankList(beginTime, endTime, type, pageController.getPageStartRow(), pageController.getPageSize());

        	List<UserRank> rankList = new ArrayList<UserRank>();
        	if (rankUserListView != null){
        		pageController.setTotalRows(rankUserListView.count);
                pageController.update();
            	
            	
            	for(RankUserDailyView rankUserDailyView : rankUserListView.rankUserList){
            		UserRank userRank = new UserRank();
            		
            		UserView user = userService.findUserById(rankUserDailyView.getUid());
            		userRank.setUid(rankUserDailyView.getUid());
            		userRank.setNumber(rankUserDailyView.getNumber());
            		if (user != null){
            			userRank.setNickName(user.getNickname());
                		userRank.setPid(user.getPopularNo());
                		userRank.setProfieImg(Adapter.getAvatar(user.getProfileImg()));
                		userRank.setIsRobot(user.getIsrobot());
            		}
            		
            		
            		rankList.add(userRank);
            	}
            	
            	mv.addObject("userRankList", rankList);  
            	
        	}
 		    
        } catch (ServiceException e) {
        	log.error(e.getMessage(), e);
        }
        
        mv.setViewName("rank/userRankList"); 
        return mv;
	}
	
	


}


