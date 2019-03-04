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
 * 负责检查ip的黑白名单
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:38:26 
 */
@Component
public class IpProcessor implements Processor{
  
  private static final Logger logger = LoggerFactory.getLogger(IpProcessor.class);

  @Autowired
  private RunConfig runConfig;

  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    String ip = context.getIp();
    //黑名单ip直接返回系统繁忙，请稍后再试
    if(runConfig.checkBlackIp(ip)) {
      logger.info("访问的IP出现在禁止的列表名单中!");
      throw new GatewayException(ResultCode.SYSTEM_ERROR);
    }
    
    //针对appKey，检查ip白名单
    
    return null;
  }
  
  @Override
  public String getProcessorName() {
    return "ip";
  }

}
