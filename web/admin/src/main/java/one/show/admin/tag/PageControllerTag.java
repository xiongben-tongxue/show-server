package one.show.admin.tag;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhangwei
 *  
 */
public class PageControllerTag extends TagSupport {

    private static Logger logger = LoggerFactory.getLogger(PageControllerTag.class);

    private String name = "pageController";
    
    private String path ;
    
    private String form;

    private String scope = "request";
    
    private boolean refresh = false;

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
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        PageController pc = (PageController) request.getAttribute(name);

        StringBuffer sb = new StringBuffer();
        
        int offfset = 5;
        
        int totalPages = pc.getTotalPages();
        int currentPage = pc.getCurrentPage();

        sb.append("<div class=\"message\">共<i class=\"blue\">"+pc.getTotalRows()+"</i>条记录，当前显示第&nbsp;<i class=\"blue\">"+currentPage+"&nbsp;</i>页</div>");
        sb.append("<ul class=\"paginList\">");

        if (pc.isHasPreviousPage()) {
            sb.append("<li class=\"paginItem\"><a href=\"javascript:document.getElementsByName('currentPage')[0].value="
                            + pc.getPreviousPage()
                            + ";document."+form+".submit();\"><span class=\"pagepre\"></span></a></li>");

        } 
        
        if (currentPage>offfset) {
        		sb.append("<li class=\"paginItem more\"><a href=\"javascript:document.getElementsByName('currentPage')[0].value="
                    + 1
                    + ";document."+form+".submit();\">1</a></li>");
        		sb.append("<li class=\"paginItem more\"><a href=\"javascript:document.getElementsByName('currentPage')[0].value="
                    + (currentPage-offfset)
                    + ";document."+form+".submit();\">...</a></li>");
		}
        
        
        
        int begin = 1;
        if (currentPage > offfset){
        	begin = currentPage;
        }
        
        
        
        int cp = 0;
        for(int i=begin;i<=totalPages;i++){
        	
        	if (currentPage==i){
        		sb.append("<li class=\"paginItem current\"><a href=\"javascript:;\">"+i+"</a></li>");
        	}else{
        		sb.append("<li class=\"paginItem\"><a href=\"javascript:document.getElementsByName('currentPage')[0].value="
                        + i
                        + ";document."+form+".submit();\">"+i+"</a></li>");
        	}
        	
        	
        	if ((++cp > offfset) && (i!=totalPages)){
        		sb.append("<li class=\"paginItem more\"><a href=\"javascript:document.getElementsByName('currentPage')[0].value="
                        + (i+1)
                        + ";document."+form+".submit();\">...</a></li>");
        		
        		 sb.append("<li class=\"paginItem "+(currentPage==totalPages?"current":"")+"\"><a href=\"javascript:document.getElementsByName('currentPage')[0].value="
        	                + totalPages
        	                + ";document."+form+".submit();\">"+totalPages+"</a></li>");
        		
        		break;
        	}
        }
        
       
        

        if (pc.isHasNextPage()) {
        	sb.append("<li class=\"paginItem\"><a href=\"javascript:document.getElementsByName('currentPage')[0].value="
                    		+ pc.getNextPage()
                    		+ ";document."+form+".submit();\"><span class=\"pagenxt\"></span></a></li>");

        }

        sb.append("</ul>");
        
        return sb.toString();
    }

    /**
     * @return Returns the form.
     */
    public String getForm() {
        return form;
    }

    /**
     * @param form
     *            The form to set.
     */
    public void setForm(String form) {
        this.form = form;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8049479179986983934L;

    /**
     * @return Returns the refresh.
     */
    public boolean isRefresh() {
        return refresh;
    }

    /**
     * @param refresh
     *            The refresh to set.
     */
    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    /**
     * @return Returns the scope.
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope
     *            The scope to set.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
