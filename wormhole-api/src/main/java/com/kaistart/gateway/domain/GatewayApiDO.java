package com.kaistart.gateway.domain;

import java.util.Date;
import java.util.List;

import com.kaistart.gateway.tool.DateUtil;

/**
 *
 * 网关API-DO对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:39:25
 */
public class GatewayApiDO extends CommonDO {
  private static final long serialVersionUID = -3240563338269708158L;
  
  /**
   * 0=普通接口，body转换成json对象传递后端，url参数按照自定义传给后端
   */
  public static final Integer SPECIAL_TYPE_0 = 0;
  /**
   * 1=流式接口，body解析成String传给后端
   */
  public static final Integer SPECIAL_TYPE_1 = 1;
  /**
   * 2=键值接口，当前没有用到，废弃
   */
  public static final Integer SPECIAL_TYPE_2 = 2;
  /**
   * 3=三方回调接口，body解析成String 合并 url参数放入到map中，统一传给后端
   */
  public static final Integer SPECIAL_TYPE_3 = 3;

  public static final String SERVER_VERSION_ONLINE = "1.0.0.online";
  
  public static final String SERVER_VERSION_DEV = "1.0.0.dev";

  
  /**
   * @mbg.generated
   */
  private Long id;
  /**
   * @mbg.generated
   */
  private Long groupId;
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
  private Integer special;

  /**
   * @mbg.generated
   */
  private Integer status;
  /**
   * @mbg.generated
   */
  private Integer httpMethod;
  /**
   * @mbg.generated
   */
  private Integer isAuth;
  /**
   * @mbg.generated
   */
  private Integer isLogin;
  /**
   * @mbg.generated
   */
  private Integer authVersion;
  /**
   * @mbg.generated
   */
  private String serviceName;
  /**
   * @mbg.generated
   */
  private String serviceMethod;
  /**
   * @mbg.generated
   */
  private String serviceVersion;
  /**
   * @mbg.generated
   */
  private Integer timeOut;
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
   * 回调响应头信息
   */
  private String responseHeader;
  
  public String getResponseHeader() {
    return responseHeader;
  }

  public void setResponseHeader(String responseHeader) {
    this.responseHeader = responseHeader;
  }

  /**
   * 输入参数
   */
  private List<GatewayServiceRequestDO> paramList;
  
  /**
   * 分组名称
   */
  private String groupName;

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
  public Long getGroupId() {
    return groupId;
  }

  /**
   * @mbg.generated
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
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
  public Integer getSpecial() {
    return special;
  }

  /**
   * @mbg.generated
   */
  public void setSpecial(Integer special) {
    this.special = special;
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
  public Integer getHttpMethod() {
    return httpMethod;
  }

  /**
   * @mbg.generated
   */
  public void setHttpMethod(Integer httpMethod) {
    this.httpMethod = httpMethod;
  }

  /**
   * @mbg.generated
   */
  public Integer getIsAuth() {
    return isAuth;
  }

  /**
   * @mbg.generated
   */
  public void setIsAuth(Integer isAuth) {
    this.isAuth = isAuth;
  }

  /**
   * @mbg.generated
   */
  public Integer getIsLogin() {
    return isLogin;
  }

  /**
   * @mbg.generated
   */
  public void setIsLogin(Integer isLogin) {
    this.isLogin = isLogin;
  }

  /**
   * @mbg.generated
   */
  public Integer getAuthVersion() {
    return authVersion;
  }

  /**
   * @mbg.generated
   */
  public void setAuthVersion(Integer authVersion) {
    this.authVersion = authVersion;
  }

  /**
   * @mbg.generated
   */
  public String getServiceName() {
    return serviceName;
  }

  /**
   * @mbg.generated
   */
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  /**
   * @mbg.generated
   */
  public String getServiceMethod() {
    return serviceMethod;
  }

  /**
   * @mbg.generated
   */
  public void setServiceMethod(String serviceMethod) {
    this.serviceMethod = serviceMethod;
  }

  /**
   * @mbg.generated
   */
  public String getServiceVersion() {
    return serviceVersion;
  }

  /**
   * @mbg.generated
   */
  public void setServiceVersion(String serviceVersion) {
    this.serviceVersion = serviceVersion;
  }

  /**
   * @mbg.generated
   */
  public Integer getTimeOut() {
    return timeOut;
  }

  /**
   * @mbg.generated
   */
  public void setTimeOut(Integer timeOut) {
    this.timeOut = timeOut;
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
    return DateUtil.getFormatDate(cdt);
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
    return DateUtil.getFormatDate(udt);
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

  public List<GatewayServiceRequestDO> getParamList() {
    return paramList;
  }

  public void setParamList(List<GatewayServiceRequestDO> paramList) {
    this.paramList = paramList;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}
