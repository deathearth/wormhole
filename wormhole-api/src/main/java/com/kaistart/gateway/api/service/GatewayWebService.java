package com.kaistart.gateway.api.service;

import com.kaistart.gateway.dto.GatewaySignRequestDto;

/**
 * 
 * 管理台测试功能需要的签名验证逻辑
 * @author chenhailong
 * @date 2019年2月14日 上午10:38:52
 */
public interface GatewayWebService {

	/**
	 * 获取网关http请求的签名
	 * @param gatewaySignRequestDto
	 * @return
	 */
	String getGatewayAuthSign(GatewaySignRequestDto gatewaySignRequestDto);
	
}
