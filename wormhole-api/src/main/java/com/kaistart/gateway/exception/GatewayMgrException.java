package com.kaistart.gateway.exception;

/**
 * 网关自定义异常类
 * @author chenhailong
 * @date 2019年2月14日 上午10:44:49
 */
public class GatewayMgrException extends RuntimeException {

	private static final long serialVersionUID = 6713725541427807005L;

	private String code;
	private String message;

	public GatewayMgrException(){
	  
	}
	
	public GatewayMgrException(String code, String msg) {
		    this.code = code;
		    this.message = msg;
	}

	public GatewayMgrException(ResultCode resultCode){
		    this.code = resultCode.getCode();
		    this.message = resultCode.getMessage();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
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
	public String toString() {
		return "{code=" + code + ", message=" + message + "}";
	}
}
