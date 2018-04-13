package one.show.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import one.show.common.exception.NotLoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;

public class BaseController implements HandlerExceptionResolver {
	
	
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
   
    private ModelAndView login(){
   	 	 ModelAndView mv = new ModelAndView();  
	     mv.setViewName("redirect:/login.do");
	     return mv;
   }

    private ModelAndView error(String msg){
    	 ModelAndView mv = new ModelAndView();  
	     mv.addObject("message", msg);  
	     mv.setViewName("common/error");  
	     return mv;
    }
   
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception exception) {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        log.error(exception.getMessage(), exception);
        
        if (exception instanceof NotLoginException) {
            return login();
        }else{
        	 return error(exception.getMessage());
        }
       
    }
  
}
