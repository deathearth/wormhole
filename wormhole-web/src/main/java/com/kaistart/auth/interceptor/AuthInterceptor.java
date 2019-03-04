package com.kaistart.auth.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kaistart.auth.domain.Resource;
import com.kaistart.auth.service.AuthorityService;
import com.kaistart.gateway.common.exception.GatewayException;

/**
 * @author fendy
 * @date 2019年2月21日 下午7:57:15
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

  /**暂时写在此类中,如果多了可以写在配置文件或者DB中动态配置 */
  private String[] excludeUriList = {"/home", "/velocity", "/auth","/wx", "/gateway/api/recive"};
  /**不记录日志列表*/
  private String[] logNoRecordUrlList = {"/home","/velocity","/auth", "/auth/access/listHandler"};

  @Autowired
  private AuthorityService authorityService;


  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

    String requestURI = httpServletRequest.getRequestURI();

    //日志记录排除js等静态访问
    boolean recordFlag = true;
    for (String excludeUri : logNoRecordUrlList) {
      if("/".equals(requestURI)) {
        recordFlag = false;
        break;
      }
      
      if (StringUtils.startsWith(requestURI, excludeUri)) {
        recordFlag = false;
        break;
      }
    }
    
    if (recordFlag) { 
      //TODO  记录访问日志
    }

    //排除列表,可以在spring配置文件中进行配置
    for (String excludeUri : excludeUriList) {
      if (StringUtils.startsWith(requestURI, excludeUri)) {
        return true;
      }
    }

    //Shiro处理登录验证
//    if (StringUtils.isEmpty(httpServletRequest.getRemoteUser())) {
//      return true;
//    }
    //获取用户权限
    List<Resource> resourceList = authorityService.findResourceList();
    for (Resource resource : resourceList) {
      if (StringUtils.startsWith(requestURI, resource.getUri())) {
        return true;
      }
    }

    //用户无权限访问此资源
    throw new GatewayException(403, "无权限访问");
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    //解决跨域
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Cache-Control", "no-cache");
    httpServletResponse.setHeader("Pragma", "no-cache");
    httpServletResponse.setDateHeader("Expires", 0);
  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

  }


}
