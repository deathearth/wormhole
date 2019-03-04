package com.kaistart.auth.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @author frendy
 * @date 2019年2月21日 下午7:57:47
 */
public class AuthenticationInterceptor implements WebRequestInterceptor {

    protected static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
    }
}
