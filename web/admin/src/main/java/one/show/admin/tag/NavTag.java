package one.show.admin.tag;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import one.show.admin.view.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import one.show.manage.thrift.view.AdminFuncView;

/**
 * @author zhangwei
 *  
 */
public class NavTag extends TagSupport {

    private static final Logger logger = LoggerFactory.getLogger(NavTag.class);

   

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String str = make();
        try {
            out.write(str);
        } catch (IOException e) {
            logger.error(str);
        }
        return super.doEndTag();
    }

    private AdminFuncView getFatherFunc(List<AdminFuncView> list, AdminFuncView func){
        
        for (AdminFuncView adminFunc : list){
            if (adminFunc.getFuncId().equals(func.getFatherFuncId())){
                return adminFunc;
            }
        }
        return null;
    }
    
    /**
     * <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
     * @return
     */
    private String make() {
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        
        User user = (User) request.getSession().getAttribute("user");
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("<ul class=\"placeul\"><li><a href=\"/main.do\">首页</a></li>");
        AdminFuncView func = null;
        
        if ((func=user.getCurrentFunc())!=null){
            
            List<AdminFuncView> fatherFuncs = new ArrayList<AdminFuncView>();
            
            AdminFuncView fatherFunc = getFatherFunc(user.getFuncList(), func);
            
            while(fatherFunc != null){
                fatherFuncs.add(fatherFunc);
                fatherFunc = getFatherFunc(user.getFuncList(), fatherFunc);
            }
            
            for (int i=fatherFuncs.size()-1; i>=0 ; i--){
                AdminFuncView f = fatherFuncs.get(i);
                
                if (f.getUrl().equals("about:blank")){
                    sb.append("<li>"+f.getFuncName()+"</li>");    
                }else{
                    sb.append("<li><a href=\""+f.getUrl()+"\" >"+f.getFuncName()+"</a></li>");
                }
            }
            sb.append("<li>"+func.getFuncName()+"</li>");
            sb.append("</ul>");
        }
        

        return sb.toString();
    }

    
}
