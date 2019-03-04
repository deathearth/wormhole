/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.GatewayConstants;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.common.tool.UtilsMd5;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayAppDO;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责接口签名检查
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:43:53
 */
@Component
public class SignProcessor implements Processor {

  private static final Logger logger = LoggerFactory.getLogger(SignProcessor.class);

  @Autowired
  private GatewayMgrService gatewayMgrService;
  
  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    String apiName = request.getParameter(GatewayConstants.HTTP_PARAM_API_NAME);
    String appKey = request.getHeader(GatewayConstants.HTTP_HEADER_APP_KEY);        
    GatewayApiDO api = gatewayMgrService.getApi(apiName);
    GatewayAppDO app = gatewayMgrService.getApp(appKey);
    //如果不需要鉴权，则直接返回
    if(api.getIsAuth().intValue() == 0) { 
      return null;
    }

    /**
     * 第一步 拼接参数生成sign
     */
    TreeMap<String, String> map = new TreeMap<String, String>();
    map.put(GatewayConstants.HTTP_HEADER_APP_KEY,
        request.getHeader(GatewayConstants.HTTP_HEADER_APP_KEY));
    map.put(GatewayConstants.HTTP_HEADER_USER_ID,
        request.getHeader(GatewayConstants.HTTP_HEADER_USER_ID));
    map.put(GatewayConstants.HTTP_HEADER_DEVICE_ID,
        request.getHeader(GatewayConstants.HTTP_HEADER_DEVICE_ID));
    map.put(GatewayConstants.HTTP_HEADER_TIMESTAMP,
        request.getHeader(GatewayConstants.HTTP_HEADER_TIMESTAMP));

    Enumeration<String> paramsNames = request.getParameterNames();
    while (paramsNames.hasMoreElements()) {
      String name = (String) paramsNames.nextElement();
      map.put(name, request.getParameter(name));
    }
    StringBuilder buf = new StringBuilder();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (!StringUtils.isEmpty(entry.getValue())) {
        buf.append(entry.getKey()).append("=").append(entry.getValue());
      }
    }
    if(app == null) {
      throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    String appSecret = app.getAppSecret();
    

    buf.append(GatewayConstants.APP_SECRET).append("=").append(appSecret);
    buf.append(GatewayConstants.CONTENT_LENGTH).append("=").append(request.getContentLength()==0?"-1":request.getContentLength());

    String serverSign = null;
    String str = null;
    try {
      str = URLEncoder.encode(buf.toString(), "utf-8");
      serverSign = UtilsMd5.md5(str);
    } catch (Exception e) {
      logger.error(buf.toString(), e);
      throw new GatewayException(ResultCode.SIGN_FAILED);
    }

    /**
     * 第二步 校验签名
     */
    String clientSign = request.getHeader(GatewayConstants.HTTP_HEADER_SIGN);
    if (serverSign == null || !serverSign.equalsIgnoreCase(clientSign)) {
      logger.error("sign-error serverSign={},clientSign={} string={}", serverSign,clientSign, str);
      throw new GatewayException(ResultCode.SIGN_FAILED);
    }
    return null;
  }
  
  @Override
  public String getProcessorName() {
    return "sign";
  }

}
