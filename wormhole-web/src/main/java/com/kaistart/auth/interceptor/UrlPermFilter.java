package com.kaistart.auth.interceptor;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 * @author frendy
 * @date 2019年2月21日 下午7:59:12
 */
public class UrlPermFilter extends PermissionsAuthorizationFilter {

    protected static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    public UrlPermFilter() {
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> isAccessAllowed");
        boolean isAccessAllowed = super.isAccessAllowed(request, response, this.getPerm(request));
        if(!isAccessAllowed){
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> request deny path:"+httpRequest.getRequestURI() +" "+httpRequest.getServletPath());
        }
        return isAccessAllowed;
    }

    private Object getPerm(ServletRequest request) {
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> getPerm");
        HttpServletRequest req = (HttpServletRequest) request;
        return new String[]{req.getRequestURI()};
    }
}
