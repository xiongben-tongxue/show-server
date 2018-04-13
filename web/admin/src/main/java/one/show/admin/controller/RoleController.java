package one.show.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import one.show.admin.tag.PageController;
import one.show.admin.view.Result;
import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.ServiceException;
import one.show.service.AdminFuncService;
import one.show.service.AdminRoleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import one.show.manage.thrift.view.AdminRoleFuncView;
import one.show.manage.thrift.view.AdminRoleListView;
import one.show.manage.thrift.view.AdminRoleView;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private AdminFuncService adminFuncService;
	
	
	@Autowired
	private AdminRoleService adminRoleService;
	
	
	@ModelAttribute("pageController")  
    public PageController getPageController(){  
        return new PageController();  
    }
	
	@ModelAttribute("role")  
    public AdminRoleView getAdminRoleView(){  
        return new AdminRoleView();  
    }
	
	
	
	@RequestMapping("/list.do")
	public ModelAndView list(@ModelAttribute(value="pageController") PageController pageController){

		 ModelAndView mv = new ModelAndView();  
		   
        try {
        	AdminRoleListView roleList = adminRoleService.getRoleList(null, pageController.getPageStartRow(), pageController.getPageSize());
        
        	if (pageController == null){
        		pageController = new PageController();
        	}
        	
        	if (roleList != null){
        		pageController.setTotalRows(roleList.count);
                pageController.update();
            	mv.addObject("roleList", roleList.roleList);  
        	}
 		    
        } catch (ServiceException e) {
        	log.error(e.getMessage(), e);
        }
        mv.setViewName("system/rolelist"); 
        return mv;
	}
	
	@RequestMapping("/add.do")
	public ModelAndView add(){
        
        ModelAndView mv = new ModelAndView();  
	    mv.addObject("opt", "doAdd");  
	    mv.setViewName("system/roleinfo");
	    return mv;
	}
	
	@RequestMapping("/doAdd.do")
	public ModelAndView doAdd(@ModelAttribute(value="role") AdminRoleView role,
			@RequestParam(value="funcStr") String funcStr){
        
		
	        
		Result result = new Result();
		result.setHref("/role/list.do");
        try {
        	StringTokenizer st = new StringTokenizer(funcStr, ",");
        	role.funcs = new ArrayList<String>();
        	while (st.hasMoreTokens()) {
                role.funcs.add(st.nextToken());
            }
        	
        	role.setStatus(ADMIN_STATUS.ENABLE.ordinal());
            role.setCreateTime((int)(System.currentTimeMillis()/1000));

        	adminRoleService.save(role);
            result.setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("添加失败:"+e.getMessage());
        }

        ModelAndView mv = new ModelAndView();  
	    mv.addObject("result", result);  
	    mv.setViewName("common/result"); 
	     
        return mv;
	        
	}
	
	@RequestMapping("/edit.do")
	public ModelAndView edit(@RequestParam(value="roleId") Integer roleId){
		
        
        AdminRoleView role = null;
        String funcStr = "";
		try {
			role = adminRoleService.getRoleById(roleId);
			
			List<AdminRoleFuncView> roleFuncList = adminRoleService.getFuncListByRoleId(roleId);
			if (roleFuncList != null){
                for(AdminRoleFuncView adminRoleFuncView : roleFuncList){
                	funcStr += adminRoleFuncView.getFuncId()+",";
                }
                
	        }
	            
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
		ModelAndView mv = new ModelAndView();  
		mv.addObject("opt", "doEdit");  
		mv.addObject("role", role);  
		mv.addObject("funcStr", funcStr); 
	    mv.setViewName("system/roleinfo"); 
	     
        return mv;
	}
	
	@RequestMapping("/doEdit.do")
	public ModelAndView doEdit(@ModelAttribute(value="role") AdminRoleView role,
			@RequestParam(value="funcStr") String funcStr){
		

		Result result = new Result();
		result.setHref("/role/list.do");
		
        try {
        	StringTokenizer st = new StringTokenizer(funcStr, ",");
        	role.funcs = new ArrayList<String>();
        	while (st.hasMoreTokens()) {
                role.funcs.add(st.nextToken());
            }
        	
            adminRoleService.update(role);

            result.setMsg("修改成功");
        } catch (ServiceException e) {

            e.printStackTrace();
            result.setMsg("修改失败:"+e.getMessage());
        }

        ModelAndView mv = new ModelAndView();  
	    mv.addObject("result", result);  
	    mv.setViewName("common/result"); 
	     
        return mv;
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Result delete(@RequestParam(value="roleId") Integer roleId){
		 Result result = new Result();
		try {
			adminRoleService.delete(roleId);
        	result.setStatus(1);
        	result.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(2);
            result.setMsg("删除失败:"+e.getMessage());
        }

        return result;

	}
	
}