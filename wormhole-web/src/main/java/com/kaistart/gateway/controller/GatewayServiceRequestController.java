package com.kaistart.gateway.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.gateway.api.service.CommonService;
import com.kaistart.gateway.api.service.GatewayApiService;
import com.kaistart.gateway.api.service.GatewayServiceRequestService;
import com.kaistart.gateway.api.service.GatewayWebService;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayServiceRequestDO;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * 网关api参数信息控制层
 * @author chenhailong
 * @date 2018年8月20日 下午6:44:22
 */
@Controller
@RequestMapping( "/gateway/servicerequest" )
public class GatewayServiceRequestController extends CommonController<GatewayServiceRequestDO> {

  @Resource
  private GatewayServiceRequestService gatewayServiceRequestService;
  
  @Resource
  private GatewayWebService gatewayWebService;
  
  @Resource
  private GatewayApiService gatewayApiService;
  
  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayServiceRequestService;
  }
	
  @RequestMapping("/edit")
  public String edit(Long id, Model model) throws Exception {
    GatewayServiceRequestDO gatewayServiceRequest = gatewayServiceRequestService.selectById(id);
    model.addAttribute("gatewayServiceRequest", gatewayServiceRequest);
    Long apiid = gatewayServiceRequest.getApiId() == null ? 0 : gatewayServiceRequest.getApiId();
    GatewayApiDO gatewayApi = gatewayApiService.selectById(apiid);
    if(gatewayApi != null) {
      model.addAttribute("special", gatewayApi.getSpecial());
    }
    return "pages/gateway/api/reqparam_edit";
  }
  
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/put")
  @Override
  public JsonResp put(@RequestBody GatewayServiceRequestDO bean) throws Exception {
    getService().update(bean);
    //修改数据之后更新缓存信息
    return getSuccessJsonResp();
  }
  
  @RequestMapping("/delete")
  public JsonResp delete(Long id, Model model) throws Exception {
    gatewayServiceRequestService.delete(id);
    return new JsonResp(JsonResp.OK, "");
  }
	
	
}
