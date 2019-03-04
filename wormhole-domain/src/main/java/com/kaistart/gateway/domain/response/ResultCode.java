package com.kaistart.gateway.domain.response;

/**
 *
 * 返回码类，所有的app的返回码常量都按照此格式定义
 * @author dxc
 * @date
 */
public class ResultCode {
  
  public static ResultCode SYSTEM_ERROR = new ResultCode(500, "系统异常，请稍后再试");
  
  public static ResultCode SYSTEM_NOT_FOUNDED = new ResultCode(51001, "系统异常，没有找到服务");
  
  public static ResultCode SYSTEM_FLOW_ERROR = new ResultCode(51002, "系统异常，请稍后再试");
  
  public static ResultCode USER_TOKEN_EXPIRED = new ResultCode(401, "登录已失效，请重新登录");
  
  public static ResultCode SIGN_FAILED = new ResultCode(50002, "签名不正确");
  
  public static ResultCode PARAM_ERROR = new ResultCode(50003, "参数格式错误");

  public static ResultCode API_AUTH_FAILED = new ResultCode(50004, "无权限访问该接口");
  
  public static ResultCode HTTP_METHOD_INVALID = new ResultCode(50005, "http请求方式不正确");

  
  private Integer code;
  private String message;
  
  public ResultCode(){}
  
  public ResultCode(Integer code, String message){
    this.code = code;
    this.message = message;
  }
  
  public Integer getCode(){
    return this.code;
  }
  public String getMessage(){
    return this.message;
  }
  
  public void setCode(Integer code) {
    this.code = code;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  @Override
  public String toString(){
    return "{code="+code+", message="+message+"}";
  }
}
