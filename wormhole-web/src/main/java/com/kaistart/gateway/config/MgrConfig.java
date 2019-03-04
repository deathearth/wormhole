/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.config;

import org.springframework.context.annotation.Configuration;

/**
 *  levin中常用的 gw相关变量, 统一数据类型
 * 
 * @author chenhailong
 * @date 2019年1月11日 下午4:08:33 
 */

@Configuration
public class MgrConfig {
  
  /**本地环境地址1*/
  public String envLocal ;
  /**本地环境地址2*/
  public String envLocal2 ;
  /**开发环境地址*/
  public String envDevIp ;
  /**测试环境内网IP*/
  public String envTestIp ;
  /**测试环境域名*/
  public String envTestDomain ;
  /**线上环境公网IP*/
  public String envOnlineIp ;
  /**线上环境内网IP*/
  public String envOnlineIp2 ;
  /**线上环境域名*/
  public String envOnlineDomain ;
  /**测试环境同步接口的线上目标地址*/
  public String envOnlineReciveDate ;
  /**接口复制的统一后缀*/
  public String copySuffix ;
  /**作者信息,用于保存/更新接口的作者*/
  public String auths;

  public String getEnvLocal() {
    return "127.0.0.1";
  }
  public String getEnvLocal2() {
    return "localhost";
  }
  public String getEnvDevIp() {
    return "xxx.xx.xxx.xx";
  }
  public String getEnvTestIp() {
    return "xxx.xx.xxx.xx";
  }
  public String getEnvTestDomain() {
    return "xxx.xx.xxx.xx";
  }
  public String getEnvOnlineIp() {
    return "xxx.xx.xxx.xx";
  }
  public String getEnvOnlineIp2() {
    return "xxx.xx.xxx.xx";
  }
  public String getEnvOnlineDomain() {
    return "xxx.xx.xxx.xx";
  }
  public String getEnvOnlineReciveDate() {
    return "http://127.0.0.1/api/revice";
  }
  public String getCopySuffix() {
    return "#copy";
  }
  public String getAuths() {
    return "开发A;开发B;开发C;开发D";
  }
  
}
