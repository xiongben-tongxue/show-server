package one.show.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import one.show.admin.view.Result;
import one.show.admin.view.User;
import one.show.common.Constant.ADMIN_FUNC_TYPE;
import one.show.common.exception.ServiceException;
import one.show.service.AdminFuncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import one.show.manage.thrift.view.AdminFuncView;

@Controller
@RequestMapping("/func")  
public class FuncController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(FuncController.class);
	
	@Autowired
	private AdminFuncService adminFuncService;
	
	@ModelAttribute("func")  
    public AdminFuncView getAdminFuncView(){  
        return new AdminFuncView();  
    }  
	
	@RequestMapping("/list.do")
	public ModelAndView list(){
		List<AdminFuncView> list = new ArrayList<AdminFuncView>();
        try {
            list = adminFuncService.getFuncList(null);
            for (AdminFuncView func1 : list) {
                for (AdminFuncView func2 : list) {
                    if (func1.getFuncId().equalsIgnoreCase(func2.getFatherFuncId())) {
                        func1.setHasChild(true);
                        break;
                    }
                }
            }
        } catch (ServiceException e) {
        	log.error(e.getMessage(), e);
        }
        
        ModelAndView mv = new ModelAndView();  
	    mv.addObject("funcList", list);  
	    mv.setViewName("system/funclist"); 
	     
	    return mv;


	}

	
	@RequestMapping("/add.do")
	public ModelAndView add(@RequestParam(value="fatherFuncId") String fatherFuncId){
		AdminFuncView af = null;
		try {
            if (!fatherFuncId.equalsIgnoreCase("-1")) {
            	af = adminFuncService.getFuncById(fatherFuncId);
            }else{
            	af = new AdminFuncView();
            	af.setFuncId("-1");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        
        ModelAndView mv = new ModelAndView();  
        mv.addObject("fatherFunc", af);  
	    mv.addObject("opt", "doAdd");  
	    mv.setViewName("system/funcinfo"); 
	     
        return mv;

	}
	
	
	
	
	@RequestMapping("/doAdd.do")
	public ModelAndView doAdd(@ModelAttribute(value="func") AdminFuncView func){
		
        func.setFuncType(ADMIN_FUNC_TYPE.FUNCTION.ordinal());
        
        Result result = new Result();
        result.setHref("/func/list.do");
        try {
            adminFuncService.save(func);
            result.setStatus(1);
            result.setMsg("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(2);
            result.setMsg("添加失败:"+e.getMessage());
        }

        ModelAndView mv = new ModelAndView();  
	    mv.addObject("result", result);  
	    mv.setViewName("common/result"); 
	     
        return mv;

	}
	
	
	@RequestMapping("/edit.do")
	public ModelAndView edit(@RequestParam(value="funcId") String funcId){
		AdminFuncView af = null;
		AdminFuncView faf = null;
		try {
			af = adminFuncService.getFuncById(funcId);
			faf = adminFuncService.getFuncById(af.getFatherFuncId());
			
			
        } catch (ServiceException e) {
            e.printStackTrace();
        }
		
		if (faf == null){
			faf = new AdminFuncView();
			faf.setFuncId("-1");
			faf.setFuncName("无");
		}

        
        ModelAndView mv = new ModelAndView();  
        mv.addObject("func", af);  
        mv.addObject("fatherFunc", faf);
	    mv.addObject("opt", "doEdit");  
	    mv.setViewName("system/funcinfo"); 
	     
        return mv;

	}
	
	
	
	
	@RequestMapping("/doEdit.do")
	public ModelAndView doEdit(@ModelAttribute(value="func") AdminFuncView func){
		
        func.setFuncType(ADMIN_FUNC_TYPE.FUNCTION.ordinal());
        
        Result result = new Result();
        result.setHref("/func/list.do");
        try {
            adminFuncService.update(func);
            result.setStatus(1);
            result.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(2);
            result.setMsg("修改失败："+e.getMessage());
        }

        ModelAndView mv = new ModelAndView();  
	    mv.addObject("result", result);  
	    mv.setViewName("common/result"); 
	     
        return mv;

	}
	
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public Result delete(@RequestParam(value="funcId") String funcId){
		 Result result = new Result();
		try {
        	adminFuncService.delete(funcId);
        	result.setStatus(1);
        	result.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(2);
            result.setMsg("删除失败:"+e.getMessage());
        }

        return result;

	}
	
	  /**
     * 返回功能树页面
     * 
     * @return
     */
	@RequestMapping("/tree.do")
    public ModelAndView tree(@RequestParam(value="funcIdInTree", required = false) String funcIdInTree) {

		if (funcIdInTree == null){
			funcIdInTree = "";
	    }
		
		ModelAndView mv = new ModelAndView();  
		mv.addObject("funcIdInTree", funcIdInTree);  
		mv.setViewName("system/functree"); 
        return mv;
    }

    /**
     * 返回功能树的json格式
     * 
     * @return
     */
	@RequestMapping("/treeData.do")
	@ResponseBody
    public List treeData(@RequestParam(value="funcIdInTree", required = false) String funcIdInTree, HttpServletRequest request) {

        if (funcIdInTree == null){
        	funcIdInTree = "";
        }
        User user = (User) request.getSession().getAttribute("user");

        List array = new ArrayList();
        if (user != null) {
            List<AdminFuncView> list = user.getFuncList();

            for (AdminFuncView func : list) {
                if (func.getFatherFuncId().equalsIgnoreCase("-1")) {
                    array.add(toJsonObj(func, list, funcIdInTree));
                }
            }
        } 
        return array;

    }

    private Map toJsonObj(AdminFuncView func, List<AdminFuncView> funcList, String funcIdInTree) {

        Map obj = new HashMap();
        obj.put("id", func.getFuncId());
        obj.put("text", func.getFuncName());
        obj.put("value", func.getFuncId());
        obj.put("showcheck", true);
        obj.put("isexpand", false);
        StringTokenizer st = new StringTokenizer(funcIdInTree, ",");
        int checkstate = 0;
        while (st.hasMoreTokens()) {
            if (st.nextToken().equalsIgnoreCase(func.getFuncId())) {
                checkstate = 1;
            }
        }
        obj.put("checkstate", checkstate);
        List childNodes = childFunc(func, funcList, funcIdInTree);
        obj.put("hasChildren", (childNodes == null || childNodes.size() == 0) ? false : true);
        obj.put("ChildNodes", childNodes);
        obj.put("complete", true);

        return obj;
    }
    
    private List<Map> childFunc(AdminFuncView func,List<AdminFuncView> funcList,  String funcIdInTree) {
        
        List<AdminFuncView> childrenList =  new ArrayList<AdminFuncView>();
        
        for(AdminFuncView o : funcList){
            if (func.getFuncId().equalsIgnoreCase(o.getFatherFuncId())){
                childrenList.add(o);
            }
        }
        
        List<Map> list = new ArrayList<Map>();
        
        for(AdminFuncView e : childrenList){
        	list.add(toJsonObj(e, funcList, funcIdInTree));
            childFunc(e, funcList, funcIdInTree);
        }
        
        return list;
        
    }

	
}