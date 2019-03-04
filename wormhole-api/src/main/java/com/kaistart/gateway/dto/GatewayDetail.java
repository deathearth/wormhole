package com.kaistart.gateway.dto;

import java.io.Serializable;
import java.util.List;

import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayApiResultDO;
import com.kaistart.gateway.domain.GatewayServiceRequestDO;
import com.kaistart.gateway.support.proto.ProtoBean;

/**
 * 网关接口详细信息(基本信息 + 响应信息)
 * @author chenhailong
 * @date 2019年2月14日 下午3:15:41
 */
public class GatewayDetail extends ProtoBean implements Serializable{

	private static final long serialVersionUID = -7793151089527261998L;

	private GatewayApiDO apiBase;
	
	private GatewayApiResultDO apiResult;
	
	public GatewayApiDO getApiBase() {
    return apiBase;
  }

  public void setApiBase(GatewayApiDO apiBase) {
    this.apiBase = apiBase;
  }

  public GatewayApiResultDO getApiResult() {
    return apiResult;
  }

  public void setApiResult(GatewayApiResultDO apiResult) {
    this.apiResult = apiResult;
  }

  public List<GatewayServiceRequestDO> getRequests() {
    return requests;
  }

  public void setRequests(List<GatewayServiceRequestDO> requests) {
    this.requests = requests;
  }

  private List<GatewayServiceRequestDO> requests;

	

	
}
