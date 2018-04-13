
package one.show.admin.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import one.show.admin.view.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import one.show.manage.thrift.view.AdminFuncView;

/**
*
* @author Norman711
*
*/

public class FXTreeTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 745333551606034430L;
	
	private static Logger logger = LoggerFactory.getLogger(FXTreeTag.class);
	
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

    private String make() {
     
        HttpSession session = pageContext.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user==null){
        	return "";
        }
        StringBuffer sb = new StringBuffer();
        
        List<AdminFuncView> funcList = user.getFuncList();
   
        for(AdminFuncView func : funcList){
            if (func.getFatherFuncId().equalsIgnoreCase("-1")){
                sb.append("<dd>\n");
                sb.append("<div class=\"title\"><span><img src=\"../images/leftico01.png\" /></span>"+func.getFuncName()+"</div>\n");
                childFunc(sb, funcList, func);
                sb.append("</dd>\n");
            }
        }

        return sb.toString();
    }

	private void childFunc(StringBuffer sb, List<AdminFuncView> funcList,
			AdminFuncView func) {
		List<AdminFuncView> childFuncList = new ArrayList<AdminFuncView>();
		//获取func下的所有子功能节点
		logger.debug("father:"+func.getFuncId());
		
		for(AdminFuncView sf : funcList){
			if (sf.getFatherFuncId().equalsIgnoreCase(func.getFuncId())){
				childFuncList.add(sf);
				logger.debug("  >>> get child:"+sf.getFuncId());
			}
		}
		String actionUrl = "";
		
		sb.append("<ul class=\"menuson\">\n");
		if (childFuncList.size()>0){
			for(AdminFuncView sf : childFuncList){
				actionUrl = sf.getUrl();
				if (actionUrl.indexOf("?")>=0){
					actionUrl = actionUrl+"&funcId="+sf.getFuncId();
				}else{
					actionUrl = actionUrl+"?funcId="+sf.getFuncId();
				}
				if (actionUrl.indexOf("registEmployee.do") > 0){
					sb.append("<li><cite></cite><a href=\""+actionUrl+"\" target=\"_blank\">"+sf.getFuncName()+"</a><i></i></li>\n");
				}else{
					sb.append("<li><cite></cite><a href=\""+actionUrl+"\" target=\"rightFrame\">"+sf.getFuncName()+"</a><i></i></li>\n");
				}
				

			}
			sb.append("</ul>\n");
		}
		
	}



}
