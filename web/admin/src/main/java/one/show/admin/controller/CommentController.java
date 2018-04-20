package one.show.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import one.show.admin.tag.PageController;
import one.show.admin.view.Result;
import one.show.admin.view.User;
import one.show.common.TimeUtil;
import one.show.common.exception.ServiceException;
import one.show.manage.thrift.view.CommentVerifyView;
import one.show.service.CommentService;
import one.show.service.ManageService;
import one.show.service.UserService;
import one.show.user.thrift.view.UserView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ManageService manageService;
	
	@Autowired
	private UserService userService;
	
	 @RequestMapping("/list.do")
	    public ModelAndView list(@ModelAttribute(value="pageController") PageController pageController){
	            if(pageController==null){
	                    pageController = new PageController();
	            }
	            List<CommentVerifyView> commentVerifyList = null;
	            Map<String,String> statements = new HashMap<String,String>();
            	statements.put("status", "0");
	            try {
	            	
	            	commentVerifyList = manageService.findCommentVerifyList(statements, pageController.getPageStartRow(), pageController.getPageSize());
	            } catch (Exception e) {
	                    log.error(e.getMessage(),e);
	            }
	            int count = 0;
	            try {
	                    count = manageService.findCommentVerifyListCount(statements);
	            } catch (Exception e) {
	                    log.error(e.getMessage(),e);
	            }
	            
	            if (commentVerifyList != null){
	            	 List<Long> ids = new ArrayList<Long>();
	 	            for(CommentVerifyView commentVerifyView : commentVerifyList){
	 	            	if(ids.contains(commentVerifyView.getUid())) continue;
	 	            	ids.add(commentVerifyView.getUid());
	 	            }
	 	            List<UserView> users = new ArrayList<UserView>();
	 				try {
	 					users = userService.findUserListByIds(ids);
	 				} catch (ServiceException e) {
	 					log.error("find users by ids error",e);
	 				}
	 	            Map<Long, UserView> userMap = new HashMap<Long, UserView>();

	 	            if (users != null){
	 	            	   for(UserView user:users){
	 	                   	userMap.put(user.getId(), user);
	 	                   }
	 	            	   for(CommentVerifyView commentVerifyView : commentVerifyList){
	 		                   	if (userMap.get(commentVerifyView.getUid()) != null){
	 		                   		commentVerifyView.setNickName(userMap.get(commentVerifyView.getUid()).getNickname()+" ("+userMap.get(commentVerifyView.getUid()).getPopularNo()+")");
	 		                   	}
	 	                   	
	 	                   }
	 	            }
	 	         
	            }
	           
	            ModelAndView mv = new ModelAndView();
	            if(commentVerifyList!=null){
	                    pageController.setTotalRows(count);
	                    pageController.update();
	                    mv.addObject("commentVerifyList",commentVerifyList);
	                    mv.addObject("pageController", pageController);
	            }
	            mv.setViewName("user/commentVerify");
	            return mv;
	    }

	    
	    @RequestMapping("/delete.do")
	    @ResponseBody
	    public Result delete(@RequestParam("id")Long id,
	    			HttpServletRequest request){
	    	
	            Result result = new Result();
	            User adminUser = (User) request.getSession().getAttribute("user");
	           
	            try {
	                    commentService.deleteComment(id, adminUser.getUserView().getUserName(), TimeUtil.getCurrentTimestamp());
	            } catch (Exception e) {
	                    result.setMsg("失败");
	                    result.setStatus(2);
	                    log.error(e.getMessage(), e);
	                    return result;
	            }
	          
	            result.setMsg("成功");
	            result.setStatus(1);
	            return result;
	    }
	    
	    @RequestMapping("/deleteByUser.do")
	    @ResponseBody
	    public Result deleteByUser(@RequestParam("uid")Long uid,
	    			HttpServletRequest request){
	    	
	            Result result = new Result();
	            User adminUser = (User) request.getSession().getAttribute("user");
	         
	            try {
	            	 	Map<String,String> statements = new HashMap<String,String>();
	            	 	statements.put("status", "0");
	            	 	statements.put("uid", String.valueOf(uid));
	            	 	List<CommentVerifyView> commentVerifyList = manageService.findCommentVerifyList(statements, 0, Integer.MAX_VALUE);
	            	

	                    for (CommentVerifyView CommentVerifyView : commentVerifyList){
	                    	commentService.deleteComment(CommentVerifyView.getId(), adminUser.getUserView().getUserName(), TimeUtil.getCurrentTimestamp());
	                    }
	            } catch (Exception e) {
	                    result.setMsg("失败");
	                    result.setStatus(2);
	                    log.error(e.getMessage(), e);
	                    return result;
	            }
	            
	          
	            result.setMsg("成功");
	            result.setStatus(1);
	            return result;
	    }
	    
	    /**
	     * 本页都看过了
	     * @param ids
	     * @return
	     */
	    @RequestMapping("/passPage.do")
	    @ResponseBody
	    public Result passPage(@RequestParam(required=true,value="ids") String ids, HttpServletRequest request){
	            Result result = new Result();
	            try {
	            		User adminUser = (User) request.getSession().getAttribute("user");
	            	 	Map<String,String> params = new HashMap<String,String>();
	            	 	params.put("status", "2");
	            	 	params.put("operator", adminUser.getUserView().getUserName());
	            	 	params.put("operate_time", String.valueOf(TimeUtil.getCurrentTimestamp()));
	                    String idsArr[] = ids.split(",");
	                    for(int i=0;i<idsArr.length;i++){
	                    	manageService.updateCommentVerifyById(Long.parseLong(idsArr[i]), params);
	                            
	                    }

	                    result.setMsg("成功");
	                    result.setStatus(1);
	            } catch (Exception e) {
	                    log.error(e.getMessage(), e);
	                    result.setMsg("失败");
	                    result.setStatus(2);
	            }
	            return result;

	    }


}
