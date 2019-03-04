package com.kaistart.gateway.support.json;

/**
 * 自定义json响应数据结构。
 *
 * @author 翁耀中 2016/12/7.
 */
public class JsonResp {

    /** 响应状态位 */
    public final static String OK    = "ok";
    public final static String ERROR = "error";

    /** 业务状态位。 */
    private String status;

    /** 返回业务记录数 */
    private Integer itemsCount;

    /** 业务数据 */
    private Object data;

    /**
     * 默认构造函数
     */
    public JsonResp() {
        this.status = JsonResp.OK;
    }

    /**
     * 自定义构造函数。
     *
     * @param status     业务状态位
     * @param data       业务数据
     * @param itemsCount 记录数
     */
    public JsonResp( String status, Integer itemsCount, Object data ) {
        this.status = status;
        this.itemsCount = itemsCount;
        this.data = data;
    }

    /**
     * 自定义构造函数。针对于不分页的情况
     *
     * @param status 业务状态位
     * @param data   业务数据
     */
    public JsonResp( String status, Object data ) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() { return status; }

    public void setStatus( String status ) { this.status = status; }

    public Integer getItemsCount() { return itemsCount; }

    public void setItemsCount( Integer itemsCount ) { this.itemsCount = itemsCount; }

    public Object getData() { return data; }

    public void setData( Object data ) { this.data = data; }

    @Override
    public String toString() {
        return "JsonResp{ status = " + status + ", count = " + itemsCount + ", data = " + data + '}';
    }
}
