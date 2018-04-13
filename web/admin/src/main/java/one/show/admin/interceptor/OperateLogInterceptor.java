package one.show.admin.interceptor;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import one.show.admin.view.User;
import one.show.common.exception.ServiceException;
import one.show.manage.thrift.view.OperateLogView;
import one.show.service.OperateLogService;
import one.show.utils.IPUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangwei on 15-7-22.
 * 权限
 */
public class OperateLogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(OperateLogInterceptor.class);
    
    
    @Autowired
    private OperateLogService operateLogService;

	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
    	return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	User user = (User) request.getSession().getAttribute("user");
    	
    	if (user != null){
	    		StringBuilder b = new StringBuilder();
	
	            Enumeration<String> em = request.getParameterNames();
	
	            int t = 0;
	
	            while (em.hasMoreElements()) {
	                if (t++ > 0) {
	                    b.append("&");
	                }
	                String name = em.nextElement();
	                if (name.equals("password")){
	                	continue;
	                }
	                b.append(name).append("=").append(request.getParameter(name));
	            }
            
    		 	String uri = request.getRequestURI();
    	    	
    	    	OperateLogView operateLogView = new OperateLogView();
    	        operateLogView.setOperator(user.getUserView().getUserName());
    	        operateLogView.setOperateTime((int)(System.currentTimeMillis()/1000));
    	        operateLogView.setVid(request.getParameter("vid"));
    	        operateLogView.setUid(request.getParameter("uid"));
    	        operateLogView.setCommentId(request.getParameter("commentId"));
    	        operateLogView.setIp(IPUtil.getIP(request));
    	        operateLogView.setDesc(uri+"?"+b.toString());
    	        try {
    				operateLogService.save(operateLogView);
    			} catch (ServiceException e) {
    				log.error(e.getMessage(), e);
    			}
    	}
	   
    

    }

}
