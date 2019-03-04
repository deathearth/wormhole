package com.kaistart.gateway.domain;

/**
 * 
 * 网关接口标签表实体
 * 
 * @version 
 * @author chenhailong
 * <pre>
 * Author	Version		Date		Changes
 * chenhailong 	1.0  2019年01月14日 Created
 *
 * </pre>
 * @since 1.
 */
public class GatewayMarkDO extends CommonDO {
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 标签层级 1，2，3
     */
    public static Integer LEVEL_1 = 1;
    public static Integer LEVEL_2 = 2;
    public static Integer LEVEL_3 = 3;
    /**
     * 标签状态 1成功 0删除
     */
    public static Integer STATUS_1 = 1;
    public static Integer STATUS_0 = 0;
    
    /**主键ID*/
    private Long id;
    /**层级关系,有且只有1，2，3层*/
    private Integer level;
    /**根节点ID*/
    private Long rootId;
    /**枝节点ID*/
    private Long branchId;
    /**叶节点ID*/
    private Long leafId;
    /**标签名称*/
    private String name;
    /**标签描述*/
    private String desc;
    /**1正常0删除*/
    private Integer status;
    /**创建者*/
    private String createBy;
    /**修改者*/
    private String updateBy;
    /**创建时间*/
    private String cdt;
    /**修改时间*/
    private String udt;
    /**版本号*/
    private Integer version;
    
    /**是否选中,关联标签时使用*/
    private Integer isCheck;

    public Integer getIsCheck() {
      return isCheck;
    }
    public void setIsCheck(Integer isCheck) {
      this.isCheck = isCheck;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }

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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCdt() {
        return cdt;
    }
    public void setCdt(String cdt) {
        this.cdt = cdt;
    }

    public String getUdt() {
        return udt;
    }
    public void setUdt(String udt) {
        this.udt = udt;
    }

    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }


}