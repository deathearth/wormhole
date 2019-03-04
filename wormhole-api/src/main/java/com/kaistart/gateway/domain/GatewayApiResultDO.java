package com.kaistart.gateway.domain;

import java.util.Date;

import com.kaistart.gateway.tool.DateUtil;
/**
 *
 * 网关API响应信息-DO对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:41:35
 */
public class GatewayApiResultDO extends CommonDO {

  /**
   * 
   */
  private static final long serialVersionUID = 1643576231365392373L;
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
  private String resultDesc;
  /**
   * @mbg.generated
   */
  private String codeDesc;

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
  public String getResultDesc() {
    return resultDesc;
  }

  /**
   * @mbg.generated
   */
  public void setResultDesc(String resultDesc) {
    this.resultDesc = resultDesc;
  }

  /**
   * @mbg.generated
   */
  public String getCodeDesc() {
    return codeDesc;
  }

  /**
   * @mbg.generated
   */
  public void setCodeDesc(String codeDesc) {
    this.codeDesc = codeDesc;
  }
}