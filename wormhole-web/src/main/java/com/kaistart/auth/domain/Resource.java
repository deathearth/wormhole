package com.kaistart.auth.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.kaistart.gateway.support.proto.ProtoBean;

/**
 * @author fendyguo
 * @date 2019年2月21日 下午7:40:29
 */
public class Resource extends ProtoBean implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private Integer pid;
  private String name;
  private char category;
  private Integer seq;
  private String uri;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date cdt;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date udt;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPid() {
    return this.pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public char getCategory() {
    return this.category;
  }

  public void setCategory(char category) {
    this.category = category;
  }

  public Integer getSeq() {
    return this.seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
  }

  public String getUri() {
    return this.uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
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
    return "Resource{id=" + this.id + ", pid=" + this.pid + ", name='" + this.name + '\'' + ", category=" + this.category + ", seq="
        + this.seq + ", uri='" + this.uri + '\'' + ", cdt=" + this.cdt + ", udt=" + this.udt + '}';
  }
}
