/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 
 * @author chenhailong
 * @date 2018年6月27日 下午5:21:49 
 */
public class RequestAuthorize  implements Serializable {

  private static final long serialVersionUID = 5631930214316121967L;
  
  /**app名称*/
  private String name;
  
  private int status;
  private long authPK;
  /**描述*/
  private String description;
  /**创建者*/
  private String createBy;
  /**主键*/
  private Long id;
  /**app id*/
  private Long appId;
  /**被授权id*/
  private Long authId;
  /**被授权id类型1=服务分组id2=服务id*/
  private Integer authIdType;
  /**被授权对象名称*/
  private String authName;
  /**创建时间*/
  private String cdt;
  /**修改时间*/
  private Date udt;
  /**版本号*/
  private Integer version;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public long getAuthPK() {
    return authPK;
  }
  public void setAuthPK(long authPK) {
    this.authPK = authPK;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getCreateBy() {
    return createBy;
  }
  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getAppId() {
    return appId;
  }
  public void setAppId(Long appId) {
    this.appId = appId;
  }
  public Long getAuthId() {
    return authId;
  }
  public void setAuthId(Long authId) {
    this.authId = authId;
  }
  public Integer getAuthIdType() {
    return authIdType;
  }
  public void setAuthIdType(Integer authIdType) {
    this.authIdType = authIdType;
  }
  public String getAuthName() {
    return authName;
  }
  public void setAuthName(String authName) {
    this.authName = authName;
  }
  
  public String getCdt() {
    return cdt;
  }
  public void setCdt(String cdt) {
    this.cdt = cdt;
  }
  public Date getUdt() {
    return udt;
  }
  public void setUdt(Date udt) {
    this.udt = udt;
  }
  public Integer getVersion() {
    return version;
  }
  public void setVersion(Integer version) {
    this.version = version;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }
  @Override
  public String toString() {
    return "RequestAuthorize [name=" + name + ", status=" + status + ", authPK=" + authPK
        + ", description=" + description + ", createBy=" + createBy + ", id=" + id + ", appId="
        + appId + ", authId=" + authId + ", authIdType=" + authIdType + ", authName=" + authName
        + ", cdt=" + cdt + ", udt=" + udt + ", version=" + version + "]";
  }
  
}
