/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.processor;

/**
 *
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午6:12:16 
 */
public class Context {

  /**
   * appkey,鉴权用
   */
  private String appKey;        
  /**
   * ip
   */
  private String ip;           
  /**
   * 验证登录
   */
  private String userId;        
  /**
   * 签名串
   */
  private String sign;          
  /**
   * 权限
   */
  private String auth;          
  /**
   * 客户端信息
   */
  private String client;        
  /**
   * 客户端笨笨信息
   */
  private String clientVersion; 
  /**
   * 时间戳
   */
  private String t;             
  /**
   * 设备信息
   */
  private Long deviceId;        
  /**
   * API接口名称
   */
  private String apiName;       
  
  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getClientVersion() {
    return clientVersion;
  }

  public void setClientVersion(String clientVersion) {
    this.clientVersion = clientVersion;
  }

  public String getT() {
    return t;
  }

  public void setT(String t) {
    this.t = t;
  }

  public Long getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(Long deviceId) {
    this.deviceId = deviceId;
  }
  
  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }
  
  
}
