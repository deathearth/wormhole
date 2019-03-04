package com.kaistart.gateway.domain;

import java.util.Date;

import com.kaistart.gateway.tool.DateUtil;
/**
 * 网关API请求参数-DO对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:44:04
 */
public class GatewayServiceRequestDO extends CommonDO{

  /**
   * 
   */
  private static final long serialVersionUID = 8503776553516360626L;
  
  public static final int STRING = 1;
  public static final int INT = 2;
  public static final int LONG = 3;
  /**
   * private static final int BOOLEAN = 4;
   */
  public static final int FLOAT = 5;
  public static final int DOUBLE = 6;
  /**
   * 自定义引用类型
   */
  public static final int OTHERS = 7;
  /**
   * @mbg.generated
   */
  private Long id;
  /**
   * @mbg.generated
   */
  private Long apiId;
  /**
   * @mbg.generated
   */
  private Integer index;
  /**
   * @mbg.generated
   */
  private String name;
  /**
   * @mbg.generated
   */
  private Integer type;
  /**
   * @mbg.generated
   */
  private String typeName;
  /**
   * @mbg.generated
   */
  private Integer isRequired;
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
  private String example;
  /**
   * @mbg.generated
   */
  private String description;

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
  public Long getApiId() {
    return apiId;
  }

  /**
   * @mbg.generated
   */
  public void setApiId(Long apiId) {
    this.apiId = apiId;
  }

  /**
   * @mbg.generated
   */
  public Integer getIndex() {
    return index;
  }

  /**
   * @mbg.generated
   */
  public void setIndex(Integer index) {
    this.index = index;
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
  public Integer getType() {
    return type;
  }

  /**
   * @mbg.generated
   */
  public void setType(Integer type) {
    this.type = type;
  }

  /**
   * @mbg.generated
   */
  public String getTypeName() {
    return typeName;
  }

  /**
   * @mbg.generated
   */
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  /**
   * @mbg.generated
   */
  public int getIsRequired() {
    return isRequired;
  }

  /**
   * @mbg.generated
   */
  public void setIsRequired(int isRequired) {
    this.isRequired = isRequired;
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

  /**
   * @mbg.generated
   */
  public String getExample() {
    return example;
  }

  /**
   * @mbg.generated
   */
  public void setExample(String example) {
    this.example = example;
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
}