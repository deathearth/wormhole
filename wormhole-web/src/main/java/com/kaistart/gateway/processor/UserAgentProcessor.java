/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.config.RunConfig;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责检查UA，做防爬虫限制
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:40:26 
 */
@Component
public class UserAgentProcessor implements Processor{
  
  private static final Logger logger = LoggerFactory.getLogger(UserAgentProcessor.class);

  @Autowired
  private RunConfig runConfig;
  
  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    
    String userAgent = request.getHeader("User-Agent");
    if(runConfig.checkBlackUserAgent(userAgent)) {
      logger.info("访问的agent出现在禁止的列表名单中!");
      throw new GatewayException(ResultCode.SYSTEM_ERROR);
    }
    return null;
  }
  
  @Override
  public String getProcessorName() {
    return "userAgent";
  }

}
