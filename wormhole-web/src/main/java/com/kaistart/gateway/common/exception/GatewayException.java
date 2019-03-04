package com.kaistart.gateway.common.exception;

import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 网关异常类定义
 * @author chenhailong
 * @date 2019年1月31日 下午2:35:16
 */
public class GatewayException extends RuntimeException {
	  private static final long serialVersionUID = 2287364131007876539L;
	  
	  private Integer code;
	  private String message;

	  public GatewayException(Integer code, String msg) {
	    this.code = code;
	    this.message = msg;
	  }
	  
	  public GatewayException(ResultCode resultCode){
	    this.code = resultCode.getCode();
	    this.message = resultCode.getMessage();
	  }
	  
	  public GatewayException(ResultCode resultCode, String msg){
        this.code = resultCode.getCode();
        this.message = (msg == null ? resultCode.getMessage() : resultCode.getMessage()+":"+msg);
      }

	  public Integer getCode() {
	    return code;
	  }

	  public void setCode(Integer code) {
	    this.code = code;
	  }

	  @Override
	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }
	  
	  @Override
	  public String toString(){
	    return "{code="+code+", message="+message+"}";
	  }
}
