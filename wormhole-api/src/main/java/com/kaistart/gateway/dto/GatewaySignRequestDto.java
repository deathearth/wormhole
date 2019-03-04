package com.kaistart.gateway.dto;

import java.io.Serializable;
import java.util.Map;

import com.kaistart.gateway.domain.GatewayApiDO;

/**
 * 网关签名参数-DTO对象
 * @author chenhailong
 * @date 2019年2月14日 上午10:44:24
 */
public class GatewaySignRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6014852788629117057L;
	
	private Map<String, String> params;
	
	private Map<String, String> headers;
	
	private GatewayApiDO apiInfo;

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public GatewayApiDO getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(GatewayApiDO apiInfo) {
		this.apiInfo = apiInfo;
	}
	
	

}
