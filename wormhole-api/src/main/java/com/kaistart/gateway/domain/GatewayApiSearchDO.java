package com.kaistart.gateway.domain;

/**
 * 后台API搜索功能用到的条件类
 * 方便扩充,及调整
 * @author chenhailong
 * @date 2019年1月15日 下午12:58:41
 */
public class GatewayApiSearchDO extends CommonDO {

  private static final long serialVersionUID = -3240563338269708158L;
  /**
   * api ID
   */
  private Long id;
  /**
   * api 分组
   */
  private Long groupId;
  /**
   * api 名称
   */
  private String name;
  /**
   * api 描述
   */
  private String description;
  /**
   * 服务路径
   */
  private String serviceName;
  /**
   * 服务方法
   */
  private String serviceMethod;
  /**
   * 根节点
   */
  private Long rootId;
  /**
   * 枝节点
   */
  private Long branchId;
  /**
   * 页节点
   */
  private Long leafId;
  /**
   * 标签名称
   */
  private String markName;
  public Long getRootId() {
    return rootId;
  }
  public void setRootId(Long rootId) {
    this.rootId = rootId;
  }
  public Long getBranchId() {
    return branchId;
  }
  public void setBranchId(Long branchId) {
    this.branchId = branchId;
  }
  public Long getLeafId() {
    return leafId;
  }
  public void setLeafId(Long leafId) {
    this.leafId = leafId;
  }
  public String getMarkName() {
    return markName;
  }
  public void setMarkName(String markName) {
    this.markName = markName;
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getGroupId() {
    return groupId;
  }
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getServiceName() {
    return serviceName;
  }
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }
  public String getServiceMethod() {
    return serviceMethod;
  }
  public void setServiceMethod(String serviceMethod) {
    this.serviceMethod = serviceMethod;
  }
}
