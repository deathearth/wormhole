/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责处理参数上下文
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:45:05 
 */
@Component
public class CallbackParamProcessor implements Processor{
  
  /**
   * 回调地址路径
   */
  private final static String NOTIFY_URL = "callback/";
  /**
   * 左斜杠
   */
  private final static String LEFT_SLASH = "/";
  
  @Autowired
  private GatewayMgrService gatewayMgrService;
  
  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    
    /**
     * 必填参数检查
     * 1、apiName
     */
    String apiName = "";
    String url = request.getRequestURI();
    //如果走的回调接口地址
    if(url.indexOf(NOTIFY_URL)>0) { 
      url = url.substring(url.indexOf(NOTIFY_URL)+9,url.length());
      if(url.indexOf(LEFT_SLASH) < 0 ) { 
        apiName = url;
      }else{
        throw new GatewayException(ResultCode.PARAM_ERROR);
      }
    }
    GatewayApiDO api = gatewayMgrService.getApi(apiName);
    if(api == null) {
      throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    //判断请求类型是否和定义类型一致  //1=get  2=post 只允许get/post
    boolean check = api.getHttpMethod() != (request.getMethod().equalsIgnoreCase("GET")?1:request.getMethod().equalsIgnoreCase("POST")?2:3);
    if(check) {
      throw new GatewayException(ResultCode.HTTP_METHOD_INVALID);
    }
    
    //放在用户在定义回调类型接口的时候没有定义map入参
    if(api.getParamList().size()<=0) {
      throw new GatewayException(ResultCode.PARAM_ERROR);
    }
    
    context.setApiName(apiName);
    
    return null;
  }
  
  @Override
  public String getProcessorName() {
    return "callbackparam";
  }

}
