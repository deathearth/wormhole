/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.GatewayConstants;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayAppDO;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责服务接口授权检查
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:42:35
 */
@Component("authProcessor")
public class AuthProcessor implements Processor {

  // private static final Logger logger = LoggerFactory.getLogger(AuthProcessor.class);

  @Autowired
  private GatewayMgrService gatewayMgrService;

  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    
    String apiName = request.getParameter(GatewayConstants.HTTP_PARAM_API_NAME);
    String appKey = request.getHeader(GatewayConstants.HTTP_HEADER_APP_KEY);        
    GatewayApiDO api = gatewayMgrService.getApi(apiName);
    GatewayAppDO app = gatewayMgrService.getApp(appKey);
    
    if (!gatewayMgrService.checkAuth(app, api)) {
      throw new GatewayException(ResultCode.API_AUTH_FAILED);
    }
    return null;
  }

  @Override
  public String getProcessorName() {
    return "auth";
  }

}
