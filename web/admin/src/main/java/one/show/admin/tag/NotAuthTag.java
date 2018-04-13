
package one.show.admin.tag;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;

import one.show.admin.view.User;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
*
* @author zhangwei
*
*/
public class NotAuthTag extends BodyTagSupport implements BodyTag{

    private static Logger logger = LoggerFactory.getLogger(NotAuthTag.class);

    private String url;
    
    
	
	public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    @Override
	public int doStartTag() throws JspException {
		
		return EVAL_BODY_BUFFERED;
	}
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

    private String make() {
    	

        HttpSession session = pageContext.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user==null){
            return "";
        }
        
        if (StringUtils.isEmpty(url)){
        	logger.error("notAuth url is null");
        	return "";
        }

        if (!user.authAccess(url)){
            return bodyContent.getString();
        }else{
            return "";
        }

        
    }
    

    
}
