package com.kaistart.auth.interceptor;

import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.annotation.WebFilter;
/**
 * shiro过滤器
 * @author chenhailong
 * @date 2019年2月21日 下午7:58:33
 */
@WebFilter(filterName = "shiroFilter", urlPatterns = {"/*"},
    initParams = {@javax.servlet.annotation.WebInitParam(name = "targetFilterLifecycle", value = "true")})
public class ShiroFilterWrapper extends DelegatingFilterProxy {
}
