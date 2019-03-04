package com.kaistart.gateway.domain.pojo;

/**
 * 客户端公用参数对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:58:30
 */
public class ClientInfo {

  private String appKey;
  private String ip;
  private String userId;
  /**
   * auth里面的sign
   */
  private String sign;
  /**
   * 从request里面获取
   */
  private String client;
  /**
   * 从request里面获取，表示app版本号
   */
  private String clientVersion;
  /**
   * header中的时间戳
   */
  private String t;
  /**
   * 从request里面获取
   */
  private Long deviceId;
  /**
   * api名称
   */
  private String apiName;

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

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }


}
