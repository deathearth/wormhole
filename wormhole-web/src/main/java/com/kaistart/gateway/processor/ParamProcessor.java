/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.GatewayConstants;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayAppDO;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责处理参数上下文
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:45:05 
 */
@Component
public class ParamProcessor implements Processor{
  /**
   * 普通接口的url标记判断,必须包含'api'
   */
  private static String urlApi = "api";
  
  @Autowired
  private GatewayMgrService gatewayMgrService;
  
  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    
    /**
     * 必填参数检查
     * 1、apiName
     * 2、appKey
     * 3、sign
     * 4、userId
     * 5、auth
     */
    
    String url = request.getRequestURL().toString();
    if( url.indexOf(urlApi)<0) {
      throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    
    String apiName = request.getParameter(GatewayConstants.HTTP_PARAM_API_NAME);
    if (StringUtils.isEmpty(apiName)) {
        throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    GatewayApiDO api = gatewayMgrService.getApi(apiName);
    if(api == null) {
      throw new GatewayException(ResultCode.PARAM_ERROR);
    }
  //判断请求类型是否和定义类型一致  //1=get  2=post 只允许get/post
    boolean check =api.getHttpMethod() != (request.getMethod().equalsIgnoreCase("GET")?1:request.getMethod().equalsIgnoreCase("POST")?2:3);
    if(check) {
      throw new GatewayException(ResultCode.HTTP_METHOD_INVALID);
    }
    context.setApiName(apiName);
    
    //appKey
    String appKey = request.getHeader(GatewayConstants.HTTP_HEADER_APP_KEY);        
    if (StringUtils.isEmpty(appKey)){
        throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    GatewayAppDO app = gatewayMgrService.getApp(appKey);
    if (app == null) {
        throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    
    /* 不需要鉴权，只要设置不需要鉴权，直接返回 */
    if (api.getIsAuth().intValue() == 0) {
        return null; 
    }
    
    //sign
    String sign = request.getHeader(GatewayConstants.HTTP_HEADER_SIGN);    
    if(StringUtils.isEmpty(sign)) {
      throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    
    //userId 和 auth
    if(api.getIsLogin().intValue() == 1) {
      String userId = request.getHeader(GatewayConstants.HTTP_HEADER_USER_ID);  
      String auth = request.getHeader(GatewayConstants.HTTP_HEADER_AUTH);  
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(auth)) {
          throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
        }
    }
    return null;
  }
  
  @Override
  public String getProcessorName() {
    return "param";
  }

}
