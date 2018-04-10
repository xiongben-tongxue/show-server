package one.show.common.local;

import one.show.common.Constant.USER_AGENT;

public class XThreadLocal {

    private final ThreadLocal<Long> userThreadLocal = new ThreadLocal<Long>();
    
    private final ThreadLocal<String> ipThreadLocal = new ThreadLocal<String>();
    
    private final ThreadLocal<HeaderParams> headerThreadLocal = new ThreadLocal<HeaderParams>();
    
    private final ThreadLocal<USER_AGENT> agentThreadLocal = new ThreadLocal<USER_AGENT>();

    private final ThreadLocal<String> exceptionThreadLocal = new ThreadLocal<String>();

    private static XThreadLocal xThreadLocal = new XThreadLocal();

    private XThreadLocal() {

    }

    public final static XThreadLocal getInstance() {
        if (xThreadLocal == null)
            xThreadLocal = new XThreadLocal();
        return xThreadLocal;
    }

    public void setCurrentUser(Long uid) {
    	userThreadLocal.set(uid);
    }

    public Long getCurrentUser() {
        return userThreadLocal.get();

    }
    
    public String getIp(){
    	return ipThreadLocal.get();
    }
    
    public void setIp(String ip){
    	ipThreadLocal.set(ip);
    }
    
    public void setHeaderParams(HeaderParams headerParams) {
    	headerThreadLocal.set(headerParams);
    }

    public HeaderParams getHeaderParams() {
        return headerThreadLocal.get();

    }
    
    
    public void setUserAgent(USER_AGENT userAgent) {
    	agentThreadLocal.set(userAgent);
    }

    public USER_AGENT getUserAgent() {
        return agentThreadLocal.get();

    }
    
    public void setException(String exception) {
    	exceptionThreadLocal.set(exception);
    }

    public String getException() {
        return exceptionThreadLocal.get();

    }
    
}
