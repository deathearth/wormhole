package com.kaistart.gateway.domain;

/**
 * 
 * 接口标签关系表实体
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
public class GatewayApiMarkDO extends CommonDO{
    private static final long serialVersionUID = 1L;
    
    /**标签id*/
    private Long markId;
    /**api接口ID*/
    private Long apiId;

    public Long getMarkId() {
        return markId;
    }
    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    public Long getApiId() {
        return apiId;
    }
    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }


}