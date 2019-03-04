package com.kaistart.gateway.domain;

import java.util.Date;

import com.kaistart.gateway.tool.DateUtil;
/**
 *
 * 网关APP权限-DO对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:42:22
 */
public class GatewayAppAuthDO extends CommonDO{

  /**
   * 
   */
  private static final long serialVersionUID = 3182014124131650817L;
  
  public static final int AUTH_TYPE_GROUP = 1;
  
  public static final int AUTH_TYPE_API = 2;
  
  /**
   * @mbg.generated
   */
  private Long id;
  /**
   * @mbg.generated
   */
  private Long appId;
  /**
   * @mbg.generated
   */
  private Long authId;
  /**
   * @mbg.generated
   */
  private Integer authIdType;
  /**
   * @mbg.generated
   */
  private String authName;
  /**
   * @mbg.generated
   */
  private Date cdt;
  /**
   * @mbg.generated
   */
  private Date udt;
  /**
   * @mbg.generated
   */
  private Integer version;

  /**
   * @mbg.generated
   */
  public Long getId() {
    return id;
  }

  /**
   * @mbg.generated
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @mbg.generated
   */
  public Long getAppId() {
    return appId;
  }

  /**
   * @mbg.generated
   */
  public void setAppId(Long appId) {
    this.appId = appId;
  }

  /**
   * @mbg.generated
   */
  public Long getAuthId() {
    return authId;
  }

  /**
   * @mbg.generated
   */
  public void setAuthId(Long authId) {
    this.authId = authId;
  }

  /**
   * @mbg.generated
   */
  public Integer getAuthIdType() {
    return authIdType;
  }

  /**
   * @mbg.generated
   */
  public void setAuthIdType(Integer authIdType) {
    this.authIdType = authIdType;
  }

  /**
   * @mbg.generated
   */
  public String getAuthName() {
    return authName;
  }

  /**
   * @mbg.generated
   */
  public void setAuthName(String authName) {
    this.authName = authName;
  }

  /**
   * @mbg.generated
   */
  public String getCdt() {
    return DateUtil.getFormatTime(cdt);
  }

  /**
   * @mbg.generated
   */
  public void setCdt(Date cdt) {
    this.cdt = cdt;
  }

  /**
   * @mbg.generated
   */
  public String getUdt() {
    return DateUtil.getFormatTime(udt);
  }

  /**
   * @mbg.generated
   */
  public void setUdt(Date udt) {
    this.udt = udt;
  }

  /**
   * @mbg.generated
   */
  public Integer getVersion() {
    return version;
  }

  /**
   * @mbg.generated
   */
  public void setVersion(Integer version) {
    this.version = version;
  }
}