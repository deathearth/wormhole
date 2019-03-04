/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.config.RunConfig;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayApiResultDO;
import com.kaistart.gateway.domain.response.ResultCode;
import com.kaistart.gateway.mgr.mapper.GatewayApiResultMapper;

/**
 * 负责mock
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:47:31 
 */
@Component
public class MockProcessor implements Processor{
  
  private static final Logger logger = LoggerFactory.getLogger(MockProcessor.class);
  
  /**
   * mock开启标志 1 开启 0 关闭
   */
  private static int mock = 1;

  @Autowired
  private RunConfig runConfig;
  
  @Autowired
  private GatewayMgrService gatewayMgrService;
  
  /**mock功能查询code*/
  @Autowired
  private GatewayApiResultMapper gatewayApiResultMapper;
  
  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    Object ret = null;
    /**测试开放了功能, 线上禁用*/
    if(runConfig.getIsMock() == mock) {
      ret = checkMock(request,context);
      if(ret != null) {
        return ret;
      }
    }
    return null;
  }
  
  /**
   * 检查是否存在mock参数
   * @param request
   * @param context
   * @return
   */
  private Object checkMock(HttpServletRequest request,Context context) {
    // 判断是否为mock处理
    String mock = request.getParameter("mock");
    if(null != mock && mock.equals(mock+"")) {
      
      String apiName = context.getApiName();
      GatewayApiDO apiInfo = gatewayMgrService.getApi(apiName);
      HashMap<String,Object> map = new HashMap<String,Object>(16);
      map.put("apiId", apiInfo.getId());
      List<GatewayApiResultDO> list;
      try {
        list = gatewayApiResultMapper.selectList(map);
        if(list.size()>0) {
          GatewayApiResultDO result = list.get(0);
          return JSON.parse(result.getExample());
        }
      } catch (Exception e) {
        logger.info("mock ",e);
      }
      throw new GatewayException(new ResultCode(500, "接口作者没有定义该接口的返回示例,请使用各种方法让他补充!"));
    }
    return null;
  }
  
  @Override
  public String getProcessorName() {
    return "mock";
  }

}
