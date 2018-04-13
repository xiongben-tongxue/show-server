package one.show.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import one.show.admin.view.User;
import one.show.common.Loader;
import one.show.common.MD5;
import one.show.common.Constant.ADMIN_STATUS;
import one.show.service.AdminFuncService;
import one.show.service.AdminRoleService;
import one.show.service.AdminUserService;
import one.show.service.VideoWallService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import one.show.manage.thrift.view.AdminFuncView;
import one.show.manage.thrift.view.AdminRoleFuncView;
import one.show.manage.thrift.view.AdminRoleView;
import one.show.manage.thrift.view.AdminUserRoleView;
import one.show.manage.thrift.view.AdminUserView;

@Controller
public class LoginController extends BaseController{

	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private AdminFuncService adminFuncService;
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private AdminRoleService adminRoleService;
	
	@Autowired
	private VideoWallService videoWallService;
	
	
	@RequestMapping("/flogin.do")
	public String flogin(){
		return "flogin";
	}
	
	
	@RequestMapping("/flogout.do")
	public String flogout(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            request.getSession().removeAttribute("user");
        }
        
	    return "redirect:/flogin.do";
	}
	

	
	@RequestMapping("/login.do")
	public String login(){
		/*
		// 下面代码可以获取DigestUtils对应的Class
        Class<DigestUtils> clazz = DigestUtils.class;
        // 获取该 Class 对象所对应类的全部构造器
        Constructor[] ctors = clazz.getDeclaredConstructors();
        System.out.println("DigestUtils 的全部构造器如下:");
        for (Constructor c : ctors) {
            System.out.println(c);
        }
        // 获取该 Class 对象所对应类的全部 public 方法
        Constructor[] publicCtors = clazz.getConstructors();
        System.out.println("DigestUtils 的全部 public 构造器如下 ：");
        for (Constructor c : publicCtors) {
            System.out.println(c);
        }
        // 获取该 Class 对象所对应类的指定方法
        Method[] mtds = clazz.getMethods();
        System.out.println("DigestUtils 的全部 public 方法如下：");
        for (Method md : mtds) {
            System.out.println(md);
        }
        //获取该 Class 对象所对应类的上的全部注释
        Annotation[] anns=clazz.getAnnotations();
        System.out.println("DigestUtils 的全部 Annotattion 如下：");
        for (Annotation an : anns) {
            System.out.println(an);
        }
        System.out.println("该 Class 元素上的 @SuppressWarnings 注释为："+
        clazz.getAnnotation(SuppressWarnings.class));
        //获取该 Class 对象所对应类的全部内部类
        Class<?>[] inners=clazz.getDeclaredClasses();
        System.out.println("DigestUtils 的全部内部类如下:");
        for (Class c : inners) {
            System.out.println(c);
        }
 
        System.out.println("DigestUtils 的包为："+clazz.getPackage());
        System.out.println("DigestUtils 的父类为："+clazz.getSuperclass());
        
        String realPath = Digest.class .getClassLoader().getResource("").getFile();
	       java.io.File file = new java.io.File(realPath);
	       realPath = file.getAbsolutePath();
	       try {
	           realPath = java.net.URLDecoder.decode (realPath, "utf-8");
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       System.out.println("classpath :"+realPath);
	       
	       String jarFilePath = DigestUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();  
			// URL Decoding  
			try {
				jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		       System.out.println("jarFilePath:"+jarFilePath);
		       */
	     return "login";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            request.getSession().removeAttribute("user");
            videoWallService.logoutEmployee(user.getUserView().getUserName());
            
        }
        
	    return "redirect:/login.do";
	}
	
	@RequestMapping("/doLogin.do")
	public ModelAndView doLogin(@RequestParam(value="username") String userName, 
			@RequestParam(value="password") String password, HttpServletRequest request){
		
			ModelAndView mv = new ModelAndView();  
    		
    
			if (userName == null || userName.equalsIgnoreCase("")) {
				mv.addObject("loginError", "用户名不能为空");  
	    		mv.setViewName("login"); 
	            return mv;
	        } else if (password == null || password.equalsIgnoreCase("")) {
	            mv.addObject("loginError", "密码不能为空");  
	    		mv.setViewName("login"); 
	            return mv;
	        }

	        boolean check = false;
	        
	        try {
	        	 AdminUserView adminUserView = adminUserService.getUser(userName);
	 	        if (adminUserView != null && MD5.md5(password).equals(adminUserView.getPassword())) {
	 	            check = true;
	 	        }

	 	        if (check) {
	 	        	User user = new User();
	 	        	if (adminUserView.getStatus() == ADMIN_STATUS.DISABLE.ordinal()) {
	 	        		mv.addObject("loginError", "此用户已被禁止登录");  
	 		    		mv.setViewName("login"); 
	 		            return mv;
	 	        	}

	 	        	user.setUserView(adminUserView);
	 	            // 超级用户拥有所有功能
	 	            String superPassport = Loader.getInstance().getProps("super.user");
	 	            if (superPassport != null && superPassport.indexOf(userName) >= 0) {
	 	                List<AdminFuncView> funcList = adminFuncService.getFuncList(ADMIN_STATUS.ENABLE);
	 	                user.setFuncList(funcList);
	 	            } else {

	 	                // 取用户拥有的角色
	 	                List<AdminUserRoleView> userRoleList = adminUserService.getUserRole(userName);
	 	                List<AdminRoleView> roleList = new ArrayList<AdminRoleView>();
	 	                for (AdminUserRoleView adminUserRole : userRoleList) {
	 	                	AdminRoleView ar = adminRoleService.getRoleById(adminUserRole.getRoleId());
	 	                    if (ar != null && ar.getStatus() == ADMIN_STATUS.ENABLE.ordinal())
	 	                        roleList.add(ar);
	 	                }
	 	                user.setRoleList(roleList);

	 	                // 取角色对应的功能
	 	                List<AdminFuncView> funcList = new ArrayList<AdminFuncView>();

	 	                for (AdminRoleView ar : user.getRoleList()) {
	 	                    List<AdminRoleFuncView> roleFunclist = adminRoleService.getFuncListByRoleId(ar.getRoleId());
	 	                    for (AdminRoleFuncView adminRoleFunc : roleFunclist) {
	 	                    	AdminFuncView af = adminFuncService.getFuncById(adminRoleFunc.getFuncId());
	 	                        if (af != null && af.getStatus() == ADMIN_STATUS.ENABLE.ordinal() && !funcList.contains(af)) {
	 	                            funcList.add(af);
	 	                        }
	 	                    }
	 	                }
	 	                user.setFuncList(funcList);

	 	            }

	 	            request.getSession().setAttribute("user", user);

	 	            mv.setViewName("redirect:/index.do"); 

	 	            return mv;

	 	        } else {
	 	        	mv.addObject("loginError", "用户名/密码错误");  
	 		    	mv.setViewName("login"); 
	 		        return mv;
	 	        }
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				mv.addObject("loginError", "系统异常:"+e.getMessage());  
 		    	mv.setViewName("login"); 
 		        return mv;
			}
	        
	       

	}
	
	public static void main(String[] args) {
		

	}
	
}