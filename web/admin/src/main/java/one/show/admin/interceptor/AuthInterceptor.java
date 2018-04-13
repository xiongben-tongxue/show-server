package one.show.admin.interceptor;


import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import one.show.admin.view.User;
import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.AuthException;
import one.show.common.exception.NotLoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhangwei on 15-7-22.
 * 权限
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    
	private static Set<String> expandAction = new HashSet<String>();
	
	
	static{
        expandAction.add("/index.do");
        expandAction.add("/menu.do");
        expandAction.add("/top.do");
        expandAction.add("/main.do");
    }

	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
    	//从登陆session中获取用户信息 
	    User user = (User) request.getSession().getAttribute("user");
	    String uri = request.getRequestURI();
	    if (uri.equals("/login.do") || uri.equals("/doLogin.do") || uri.equals("/logout.do")){
	    	return true;
	    }
	    //验证是否登录
		if (user != null) {
		    //用户被禁用
		    if (user.getUserView().getStatus() == ADMIN_STATUS.DISABLE.ordinal()){
		    	throw new AuthException("用户被禁用");
		    }
		    
            String contextPath = request.getContextPath();
            String funcUri = uri.replaceFirst(contextPath, "");
           
            if (expandAction.contains(funcUri)){
                return true;
            }else{
            	 // 验证权限
                if (user.authAccess(funcUri)) {
                    return true;
                }else{

                    //没有权限
                	throw new AuthException("权限不足");
                }
                
            }

		}else{
			throw new NotLoginException("未登录");
		}

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    

    }

}
