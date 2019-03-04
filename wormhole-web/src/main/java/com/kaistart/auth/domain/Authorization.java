package com.kaistart.auth.domain;

import java.io.Serializable;
import java.util.Date;

import com.kaistart.gateway.support.proto.ProtoBean;

/**
 * @author fendyguo
 * @date 2019年2月21日 下午7:39:52
 */
public class Authorization extends ProtoBean implements Serializable {
  private static final long serialVersionUID = 1L;


  private Integer id;
  private Integer userId;
  private Integer roleId;
  private Date cdt;
  private Date udt;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getRoleId() {
    return this.roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public Date getCdt() {
    return this.cdt;
  }

  public void setCdt(Date cdt) {
    this.cdt = cdt;
  }

  public Date getUdt() {
    return this.udt;
  }

  public void setUdt(Date udt) {
    this.udt = udt;
  }

  @Override
  public String toString() {
    return "Authorization{id=" + this.id + ", userId=" + this.userId + ", roleId=" + this.roleId + ", cdt=" + this.cdt + ", udt=" + this.udt
        + '}';
  }
}
