package com.kaistart.gateway.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * 运行时需要的配置
 * @author chenhailong
 * @date 2019年2月27日 下午5:18:29
 */
public class RunConfig {

  private Set<String> userAgentBlackSet = new HashSet<String>();

  /**
   * 增加UserAgent请求黑名单
   */
  private String blackUserAgent;


  /**
   * 增加ip请求黑名单
   */
  private String blackIp;

  private Set<String> ipBlackSet = new HashSet<String>();

  /**
   * 用户登录token时间限制
   */
  private long userTokenExpiredTime;
  
  /**
   * 是否开放mock功能
   */
  private Integer isMock;
  
  /**
   * 组件组
   */
  private String processor;
  
  /**
   * 网关环境是否为线上的开关, 可以根据这个控制一些功能的开放与关闭
   */
  private boolean online = true;

  /**
   * dubbo泛化调用时用到
   */
  private String applicationName;
  
  /**
   * dubbo调用的zk地址
   */
  private String zookeeperAddress;

  public String getApplicationName() {
      return applicationName;
  }

  public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
  }

  public String getZookeeperAddress() {
      return zookeeperAddress;
  }

  public void setZookeeperAddress(String zookeeperAddress) {
      this.zookeeperAddress = zookeeperAddress;
  }
  
  
  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }
  
  public String getProcessor() {
    return processor;
  }

  public void setProcessor(String processor) {
    this.processor = processor;
  }

  public Integer getIsMock() {
    return isMock;
  }

  public void setIsMock(Integer isMock) {
    this.isMock = isMock;
  }

  public long getUserTokenExpiredTime() {
    return userTokenExpiredTime;
  }

  public void setUserTokenExpiredTime(long userTokenExpiredTime) {
    this.userTokenExpiredTime = userTokenExpiredTime;
  }
  

  public String getBlackUserAgent() {
    return blackUserAgent;
  }

  public void setBlackUserAgent(String value) {
    if (StringUtils.isBlank(value)) {
      return;
    }
    String[] array = value.split("\\|");
    Set<String> set = new HashSet<String>();
    for (String item : array) {
      if (!StringUtils.isBlank(item)) {
        set.add(item);
      }
    }
    userAgentBlackSet = set;
    blackUserAgent = value;
  }

  public String getBlackIp() {
    return blackIp;
  }

  public void setBlackIp(String value) {
    if (StringUtils.isBlank(value)) {
      return;
    }
    String[] array = value.split("\\|");
    Set<String> set = new HashSet<String>();
    for (String item : array) {
      if (!StringUtils.isBlank(item)) {
        set.add(item);
      }
    }
    ipBlackSet = set;
    blackIp = value;
  }

  public boolean checkBlackIp(String target) {
    return ipBlackSet.contains(target);
  }

  @Override
  public String toString() {
    return "AuthConfig [userAgentBlackSet=" + userAgentBlackSet + ", blackUserAgent="
        + blackUserAgent + ", blackIp=" + blackIp + ", ipBlackSet=" + ipBlackSet
        + ", userTokenExpiredTime=" + userTokenExpiredTime + ", isMock=" + isMock + "]";
  }

  public boolean checkBlackUserAgent(String target) {
    return userAgentBlackSet.contains(target);
  }

}
