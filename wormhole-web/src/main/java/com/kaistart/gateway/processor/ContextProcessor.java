/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.kaistart.gateway.common.GatewayConstants;

/**
 *
 * 负责获取固定参数信息加入到上下文中
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:32:32
 */
@Component("contextProcessor")
public class ContextProcessor implements Processor {

  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    context.setAppKey(request.getHeader(GatewayConstants.HTTP_HEADER_APP_KEY));
    context.setIp(getIPFromRequest(request));
    context.setT(request.getHeader(GatewayConstants.HTTP_HEADER_TIMESTAMP));

    context.setUserId(request.getHeader(GatewayConstants.HTTP_HEADER_USER_ID));
    context.setSign(request.getHeader(GatewayConstants.HTTP_HEADER_SIGN));
    context.setAuth(request.getHeader(GatewayConstants.HTTP_HEADER_AUTH));
    

    context.setClient(request.getParameter(GatewayConstants.HTTP_PARAM_CLIENT));
    context.setClientVersion(request.getParameter(GatewayConstants.HTTP_PARAM_CLIENT_VERSION));

    String deviceId = request.getHeader(GatewayConstants.HTTP_HEADER_DEVICE_ID);
    if (StringUtils.isNumeric(deviceId)) {
      context.setDeviceId(Long.valueOf(deviceId));
    }
    context.setApiName(request.getParameter(GatewayConstants.HTTP_PARAM_API_NAME));
    return null;
  }

  private String getIPFromRequest(HttpServletRequest request) {
    String unknown = "unknown";
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    // 如果是多个ip，只返回第一个，格式ip,ip,ip...
    if (ip != null) {
      int index = ip.indexOf(",");
      if (index != -1) {
        return ip.substring(0, index);
      }
    }
    return ip;
  }

  @Override
  public String getProcessorName() {
    return "context";
  }

}
