package com.kaistart.gateway.support.result;

import java.io.Serializable;

/**
 * 统一结果响应对象
 * @author chenhailong
 * @date 2019年2月21日 下午6:53:55
 */
public class Result<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 响应状态位 */
  public final static String OK = "ok";
  public final static String ERROR = "error";
  /** 业务状态位。 */
  private String status;
  /** 业务处理消息。 */
  private String message;
  /** 业务处理代码。 */
  private String code;
  /** 业务数据 */
  private T data;

  public Result() {
    this.status = Result.OK;
  }

  public Result(String status, T data) {
    this.status = status;
    this.data = data;
  }

  public Result(String status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }
  
  public Result(String status,String code, String message, T data) {
    this.status = status;
    this.code = code;
    this.message = message;
    this.data = data;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static Result<?> error(String message) {
    return new Result(Result.ERROR, message);
  }
  
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static boolean isSuccess(Result<?> result) {
    if (Result.OK.equals(result.getStatus())) {
      return true;
    }
    return false;
  }
  
  public static boolean isNotSuccess(Result<?> result) {
    if (Result.ERROR.equals(result.getStatus())) {
      return true;
    }
    return false;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return "Result [status=" + status + ", message=" + message + ", code=" + code + ", data=" + data + "]";
  }


}
