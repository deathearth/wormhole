/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.chl.test;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

/**
 *
 * 
 * @author chenhailong
 * @date 2018年6月20日 下午2:50:42 
 */
public class DubboTest {

  /**
   * @param args
   */
  public static void main(String[] args) {

    ApplicationConfig application = new ApplicationConfig();
    application.setName("api-generic-consumer");

    RegistryConfig registry = new RegistryConfig();
    registry.setAddress("zookeeper://127.0.0.1:2181");

    application.setRegistry(registry);

    ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
    // 弱类型接口名
    reference.setInterface("com.kaishiba.uic.api.biz.app.AppBizService");
    // 声明为泛化接口
    reference.setGeneric(true);

    reference.setApplication(application);

    // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
    GenericService genericService = reference.get();

    String name = (String) genericService.$invoke("trackSDKInfoConfig", new String[]{}, new Object[]{});
    System.out.println(name);
  }

}
