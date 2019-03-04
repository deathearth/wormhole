package com.kaistart.gateway.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaistart.gateway.api.service.GatewayWebService;
import com.kaistart.gateway.dto.GatewaySignRequestDto;

/**
 * 网关后台业务的服务层
 * @author chenhailong
 * @date 2019年1月31日 下午2:35:35
 */
@Service("gatewayWebService")
public class GatewayWebServiceImpl implements GatewayWebService{
	
    @Autowired
    private GatewayWebSign gatewayWebSign;
  
	@Override
	public String getGatewayAuthSign(GatewaySignRequestDto gatewaySignRequestDto) {
	  return gatewayWebSign.run(gatewaySignRequestDto.getParams(), gatewaySignRequestDto.getHeaders(), gatewaySignRequestDto.getApiInfo());
	}

}
