/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaistart.gateway.config.RunConfig;

/**
 *
 * 
 * @author wuyuan.lfk
 * @date 2019年1月16日 下午1:57:33
 */
@Service("callbackProcessorChain")
public class CallbackProcessorChain implements Processor {

  @Autowired
  private CallbackParamProcessor callbackParamProcessor;
  @Autowired
  private MockProcessor mockProcessor;
  @Autowired
  private DubboProcessor dubboProcessor;
  @Autowired
  private RunConfig runConfig;


  private Map<String, Processor> processorMap = new HashMap<String, Processor>();
  private List<Processor> processorChain = new ArrayList<Processor>();
  private String processors = null;


  /**
   * 初始化processor
   */
  @PostConstruct
  public void init() {
    initProcessorMap();
    initProcessorChain();
  }
  
  private void initProcessorMap() {
    processorMap.put(callbackParamProcessor.getProcessorName(), callbackParamProcessor);
    processorMap.put(mockProcessor.getProcessorName(), mockProcessor);
    processorMap.put(dubboProcessor.getProcessorName(), dubboProcessor);
  }
  
  private void initProcessorChain() {
    String value = runConfig.getProcessor();
    if (!StringUtils.equals(processors, value)) {
      processors = value;
      if(StringUtils.isBlank(processors)){
        createDefaultProcessorChain();
      }else {
        String[] array = processors.split(";");
        for(String key : array) {
          Processor processor = processorMap.get(key);
          if(processor != null) {
            processorChain.add(processor);
          }
        }
        if(processorChain.isEmpty()) {
          createDefaultProcessorChain();
        }
      }
    }
  }
  
  private void createDefaultProcessorChain() {
    processorChain.add(callbackParamProcessor);
    processorChain.add(mockProcessor);
    processorChain.add(dubboProcessor);
  }

  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {
    initProcessorChain();
    Object result = null;
    for(Processor processor : processorChain) {
      result  = processor.run(request, context);
      if(result != null) {
        return result;
      }
    }
    return result;
  }

  @Override
  public String getProcessorName() {
    return "callbackChain";
  }

}
