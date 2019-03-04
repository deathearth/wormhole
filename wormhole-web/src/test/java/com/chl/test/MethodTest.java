/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.chl.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.mgr.mapper.GatewayApiMapper;

/**
 *
 * 
 * @author chenhailong
 * @date 2018年8月23日 上午10:06:35 
 */
public class MethodTest {

  /**
   * @param args
   */
  @SuppressWarnings("resource")
  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring.xml");
    context.start();
    
    GatewayApiMapper apiMapper = (GatewayApiMapper)context.getBean("gatewayApiMapper");
    
    GatewayApiDO api = apiMapper.selectByPrimaryKey(48L);
    api.setId(null);
    apiMapper.insert(api);
    System.out.println(api.getId());
  }

}
