/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.dubbo;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

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
import com.kaistart.gateway.domain.GatewayAppDO;
import com.kaistart.gateway.domain.pojo.UserToken;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责接口签名检查
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:43:53
 */
@Component("gatewayWebSign")
public class GatewayWebSign  {
  
  @Autowired
  private CacheService cacheService;
  
  @Autowired
  private RunConfig runConfig;
  
  @Autowired
  private GatewayMgrService gatewayMgrService;

  private static final Logger logger = LoggerFactory.getLogger(GatewayWebSign.class);

  public String run(Map<String, String> params, 
      Map<String, String> headers, GatewayApiDO apiInfo)  {


 // 检查appKey
    GatewayAppDO app = gatewayMgrService.getApp(headers.get(GatewayConstants.HTTP_HEADER_APP_KEY));
    if (app == null) {
        throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    String tokenString = null;
    
    //如果需要登录才获取
    if(apiInfo.getIsLogin().intValue() == 1){
      
    //获取用户id，如果前端传输了用户id，就需要校验token
      String uid = headers.get(GatewayConstants.HTTP_HEADER_USER_ID);
      if (StringUtils.isEmpty(uid)){
        throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
      }
      
      String tokenKey = GatewayConstants.CACHEKEY_USER_TOKEN + uid;
      String tokenJson = cacheService.get(tokenKey);
      if (StringUtils.isEmpty(tokenJson)) {
          logger.error("用户token信息不存在或者失效,token_key={}", tokenKey);
          throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
      }
      UserToken userToken = JSONObject.parseObject(tokenJson, UserToken.class);
      long now = System.currentTimeMillis();
      long expireTime = userToken.getExpireTime().getTime();
      if (now > expireTime) {
          logger.error("用户token信息失效,token_key={}", tokenKey);
          throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
      }
     //24小时内过期，则延长token过期时间
      long day = 86400000L;
      if ((now + day) > expireTime) { 
        Date today = new Date(System.currentTimeMillis());
        Date expire = new Date(today.getTime() + runConfig.getUserTokenExpiredTime());
        userToken.setCreateTime(today);
        userToken.setExpireTime(expire);
        cacheService.set(tokenKey, JSON.toJSONString(userToken));
      }
      
      if(StringUtils.isEmpty(userToken.getToken())) {
        throw new GatewayException(ResultCode.USER_TOKEN_EXPIRED);
      }
      tokenString = userToken.getToken();
    }
    
    TreeMap<String, String> map = new TreeMap<String, String>();
    map.put(GatewayConstants.HTTP_HEADER_APP_KEY, headers.get(GatewayConstants.HTTP_HEADER_APP_KEY));
    map.put(GatewayConstants.HTTP_HEADER_USER_ID, headers.get(GatewayConstants.HTTP_HEADER_USER_ID));
    map.put(GatewayConstants.HTTP_HEADER_DEVICE_ID, headers.get(GatewayConstants.HTTP_HEADER_DEVICE_ID));
    map.put(GatewayConstants.HTTP_HEADER_TIMESTAMP, headers.get(GatewayConstants.HTTP_HEADER_TIMESTAMP));
    
  map.putAll(params);
  StringBuilder buf = new StringBuilder();
  for (Map.Entry<String, String> entry : map.entrySet()) {
    if (!StringUtils.isEmpty(entry.getValue())) {
      buf.append(entry.getKey()).append("=").append(entry.getValue());
    }
  }
  buf.append(GatewayConstants.APP_SECRET).append("=").append(app.getAppSecret());
  buf.append(GatewayConstants.CONTENT_LENGTH).append("=").append(headers.get(GatewayConstants.CONTENT_LENGTH));

  try {
    String sign = UtilsMd5.md5(URLEncoder.encode(buf.toString(), "utf-8"));
    String auth = UtilsMd5.md5(sign+tokenString);
    //这里主要取值返回给前端展示
    return sign+"###"+auth;
  } catch (Exception e) {
    logger.error(e.getMessage(), e);
  }

  return null;
  }

}
