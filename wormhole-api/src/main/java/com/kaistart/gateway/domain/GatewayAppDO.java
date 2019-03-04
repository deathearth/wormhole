package com.kaistart.gateway.domain;

import java.util.Date;

import com.kaistart.gateway.tool.DateUtil;

/**
 *
 * 网关APP-DO对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:43:05
 */
public class GatewayAppDO extends CommonDO{
  
  /**正常*/
  public static final Integer STATUS_1 = 1;
  /**删除*/
  public static final Integer STATUS_0 = 1;
  
  /**
   * 
   */
  private static final long serialVersionUID = -4914766310070474040L;
  /**
   * @mbg.generated
   */
  private Long id;
  /**
   * @mbg.generated
   */
  private String name;
  /**
   * @mbg.generated
   */
  private String description;
  /**
   * @mbg.generated
   */
  private Long partnerId;
  
  private String partnerName;
  
  /**用户的key*/
  private String partnerKey;

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }
  
  public String getPartnerKey() {
      return partnerKey;
  }
  public void setPartnerKey(String partnerKey) {
      this.partnerKey = partnerKey;
  } 
  /**
   * @mbg.generated
   */
  private String appKey;
  /**
   * @mbg.generated
   */
  private String appSecret;
  /**
   * @mbg.generated
   */
  private Integer authType;
  /**
   * @mbg.generated
   */
  private Integer status;
  /**
   * @mbg.generated
   */
  private String createBy;
  /**
   * @mbg.generated
   */
  private String updateBy;
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
  public String getName() {
    return name;
  }

  /**
   * @mbg.generated
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @mbg.generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * @mbg.generated
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @mbg.generated
   */
  public Long getPartnerId() {
    return partnerId;
  }

  /**
   * @mbg.generated
   */
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  /**
   * @mbg.generated
   */
  public String getAppKey() {
    return appKey;
  }

  /**
   * @mbg.generated
   */
  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  /**
   * @mbg.generated
   */
  public String getAppSecret() {
    return appSecret;
  }

  /**
   * @mbg.generated
   */
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  /**
   * @mbg.generated
   */
  public Integer getAuthType() {
    return authType;
  }

  /**
   * @mbg.generated
   */
  public void setAuthType(Integer authType) {
    this.authType = authType;
  }

  /**
   * @mbg.generated
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * @mbg.generated
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * @mbg.generated
   */
  public String getCreateBy() {
    return createBy;
  }

  /**
   * @mbg.generated
   */
  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  /**
   * @mbg.generated
   */
  public String getUpdateBy() {
    return updateBy;
  }

  /**
   * @mbg.generated
   */
  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
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