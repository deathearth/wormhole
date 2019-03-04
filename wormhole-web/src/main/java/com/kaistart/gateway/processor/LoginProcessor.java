/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.GatewayConstants;
import com.kaistart.gateway.common.cache.CacheService;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.common.tool.UtilsMd5;
import com.kaistart.gateway.config.RunConfig;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.pojo.UserToken;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责处理参数上下文
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:45:05
 */
@Component
public class LoginProcessor implements Processor {

  private static final Logger logger = LoggerFactory.getLogger(LoginProcessor.class);

  @Autowired
  private GatewayMgrService gatewayMgrService;

  @Autowired
  private CacheService cacheService;

  @Autowired
  private RunConfig runConfig;

  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {


    String apiName = request.getParameter(GatewayConstants.HTTP_PARAM_API_NAME);
    GatewayApiDO api = gatewayMgrService.getApi(apiName);
    // 如果不需要登录，则直接返回
    if (api.getIsLogin().intValue() == 0) { 
      return null;
    }
    
    String uid = request.getHeader(GatewayConstants.HTTP_HEADER_USER_ID);
    if (StringUtils.isEmpty(uid)) {
      throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
    }
    String tokenKey = GatewayConstants.CACHEKEY_USER_TOKEN + uid;
    String tokenJson = cacheService.get(tokenKey);
    if (StringUtils.isEmpty(tokenJson)) {
      logger.error("auth-error-cache-is-null token_key={}", tokenKey);
      throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
    }
    UserToken userToken = JSONObject.parseObject(tokenJson, UserToken.class);
    long now = System.currentTimeMillis();
    long expireTime = userToken.getExpireTime().getTime();
    if (now > expireTime) {
      logger.error("auth-error-token-expired token_key={}", tokenKey);
      throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
    }
    // 24小时内过期，则延长token过期时间
    long day = 86400000L;
    if ((now + day) > expireTime) { 
      Date today = new Date(System.currentTimeMillis());
      Date expire = new Date(today.getTime() + runConfig.getUserTokenExpiredTime());
      userToken.setCreateTime(today);
      userToken.setExpireTime(expire);
      cacheService.set(tokenKey, JSON.toJSONString(userToken));
    }
    if (StringUtils.isEmpty(userToken.getToken())) {
      throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
    }
    
    //检查auth签名
    String str = context.getSign()+userToken.getToken();
    String serverAuth = UtilsMd5.md5(str);
    if (serverAuth == null || !serverAuth.equalsIgnoreCase(context.getAuth())) {
      logger.error("auth-error serverAuth={} serverToken={} context={}", 
          serverAuth, userToken.getToken(), JSON.toJSONString(context));
      throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
    }
    
    return null;
  }

  @Override
  public String getProcessorName() {
    return "login";
  }

}
