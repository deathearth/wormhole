/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.kaistart.gateway.servlet.KaistartGatewayCallbackServlet;
import com.kaistart.gateway.servlet.KaistartGatewayServlet;

/**
 *
 * @author chenhailong
 * @date 2019年3月4日 下午4:01:56 
 * @ServletComponenthkScan 扫描servlet
 */
@SpringBootApplication
@ImportResource({"classpath:application-velocity.xml","classpath:/spring/spring-auth.xml",
  "classpath:/spring/spring-common.xml","classpath:/spring/springmvc.xml"})
@MapperScan({"com.kaistart.gateway.mgr.mapper","com.kaistart.auth.mapper"})
public class ApplicationRun extends SpringBootServletInitializer{

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(ApplicationRun.class, args);
  }
  
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(ApplicationRun.class);
  }

  
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean  //一定要加，不然这个方法不会运行
  public ServletRegistrationBean getServletRegistrationBean() {  //一定要返回ServletRegistrationBean
      ServletRegistrationBean bean = new ServletRegistrationBean(new KaistartGatewayServlet());     //放入自己的Servlet对象实例
      bean.addUrlMappings("/api");  //访问路径值
      return bean;
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean  //一定要加，不然这个方法不会运行
  public ServletRegistrationBean getServletRegistrationBeanCallback() {  //
      ServletRegistrationBean bean = new ServletRegistrationBean(new KaistartGatewayCallbackServlet());    
      bean.addUrlMappings("/callback/*");  //访问路径值
      return bean;
  }
}
