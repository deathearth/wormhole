package com.kaistart.gateway.support.json;

/**
 * 自定义json响应数据结构。
 *
 * @author 翁耀中 2016/12/7.
 */
public class KaistartJsonResp {

    /** 响应状态位 */
    public final static String OK    = "ok";
    public final static String ERROR = "error";

    /** 业务状态位。 */
    private String status;
    
    /** 业务状态码。 */
    private String code;
    
    /** 返回说明 */
    private String message;

    /** 返回业务记录数 */
    private Integer total;

    /** 业务数据 */
    private Object data;

    /**
     * 默认构造函数
     */
    public KaistartJsonResp() {
        this.status = KaistartJsonResp.OK;
        this.code = "200";
        this.message = "操作成功";
    }

    /**
     * 自定义构造函数。
     *
     * @param status     业务状态位
     * @param data       业务数据
     * @param itemsCount 记录数
     */
    public KaistartJsonResp( String status, Integer total, Object data ) {
        this.status = status;
        this.total = total;
        this.data = data;
    }

    /**
     * 自定义构造函数。针对于不分页的情况
     *
     * @param status 业务状态位
     * @param data   业务数据
     */
    public KaistartJsonResp( String status, Object data ) {
        this.status = status;
        this.data = data;
    }

  
    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public Integer getTotal() {
      return total;
    }

    public void setTotal(Integer total) {
      this.total = total;
    }

    public Object getData() {
      return data;
    }

    public void setData(Object data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return "KaistartJsonResp [status=" + status + ", code=" + code + ", message=" + message + ", total=" + total + ", data=" + data + "]";
    }

   
}
