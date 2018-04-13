package one.show.admin.controller;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import one.show.admin.view.User;
import one.show.common.RandomUtils;
import one.show.common.exception.ServiceException;
import one.show.service.RobotService;
import one.show.service.StatService;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import one.show.stat.thrift.view.SummaryStatView;

@Controller
public class IndexController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private StatService statService;
	

	@RequestMapping("/index")
	public ModelAndView index(){
		 ModelAndView mv = new ModelAndView();  
	     //添加模型数据 可以是任意的POJO对象  
	     mv.addObject("title", " 运营管理后台");  
	     //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面  
	     mv.setViewName("index");  
	     return mv;
	}
	
	@RequestMapping("/menu")
	public String menu(){
	     return "menu";
	}
	
	@RequestMapping("/drag")
	public String drag(){
	     return "drag";
	}
	
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mv = new ModelAndView();  
	    mv.addObject("user", user);  
	    mv.setViewName("main"); 
	    
	    try {
	    	List<SummaryStatView> list = statService.getSummaryStatList(20160715);
	    	
	    	List<String> time = new ArrayList<String>();
	    	List<Integer> play = new ArrayList<Integer>();
	    	List<Integer> pv = new ArrayList<Integer>();
	    	List<Integer> login = new ArrayList<Integer>();
	    	List<Integer> register = new ArrayList<Integer>();
	    	List<Integer> share = new ArrayList<Integer>();
	    	List<Integer> videos = new ArrayList<Integer>();
	    	List<Integer> liveMax = new ArrayList<Integer>();
	    	
	    	for (int i=0; i<list.size(); i++){
	    		SummaryStatView summaryStatView = list.get(i);
	    		time.add(String.valueOf(summaryStatView.getTime()).substring(2));
	    		play.add(summaryStatView.getPlay());
	    		pv.add(summaryStatView.getPv());
	    		register.add(summaryStatView.getReg());
	    		login.add(summaryStatView.getLogin());
	    		share.add(summaryStatView.getShare());
	    		videos.add(summaryStatView.getVideos());
	    		liveMax.add(summaryStatView.getLiveMax());
	    	}
	    	
	    	mv.addObject("time", time);  
	    	mv.addObject("play", play);  
	    	mv.addObject("pv", pv);  
	    	mv.addObject("login", login);  
	    	mv.addObject("register", register);  
	    	mv.addObject("share", share);  
	    	mv.addObject("videos", videos);  
	    	mv.addObject("liveMax", liveMax);  
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	    
	    
	    return mv;
	}
	
	@RequestMapping("/top")
	public String top(){
	     return "top";
	}
	
	public static void main(String[] args) {
		List time = new ArrayList();
		time.add(1);
		System.out.println(time);
	}
	
	
}