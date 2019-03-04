package com.kaistart.gateway.domain;

/**
 * 
 * 网关IP名单表实体
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
public class GatewayIpsetDO extends CommonDO {
    private static final long serialVersionUID = 1L;
    
    /**
     * 正常状态
     */
    public static final Integer STATUS_1 = 1;
    /**
     * 删除状态
     */
    public static final Integer STATUS_0 = 0;
    
    /**主键ID*/
    private Long id;
    /**IP地址*/
    private String ip;
    /**类型  默认是1*/
    private String type;
    /**描述信息*/
    private String desc;
    /**创建者*/
    private String createBy;
    /**修改者*/
    private String updateBy;
    /**状态 1正常 0 删除*/
    private Integer status;
    /**创建时间*/
    private String cdt;
    /**修改时间*/
    private String udt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
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

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
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


}