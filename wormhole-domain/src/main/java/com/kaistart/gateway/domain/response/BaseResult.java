/*
 * Copyright 2017 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.domain.response;

/**
 * Controller基础返回类
 *
 * 
 * @author kenzzj
 * @date 2017年9月18日 下午12:43:58
 */

public class BaseResult<T> {
  
  private final static int SUCCESS = 200;

  private Integer code;

  private String message;
  
  private boolean success;

  private T data;

  public BaseResult() {}

  public BaseResult(Integer code, String message) {
    this.code = code;
    this.message = message;
    if(SUCCESS == code){
      this.success = true;
    }else{
      this.success = false;
    }
  }
  
  public BaseResult(T data) {
    this.data = data;
    this.code = 200;
    this.success = true;
  }

  public BaseResult(ResultCode resultCode) {
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
    if(SUCCESS == resultCode.getCode()){
      this.success = true;
    }else{
      this.success = false;
    }
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static BaseResult success(Object data){
    return new BaseResult(data);
  }
  

  public void setResultCode(ResultCode resultCode) {
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
    if( SUCCESS == resultCode.getCode()){
      this.success = true;
    }else{
      this.success = false;
    }
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
