package com.kaistart.gateway.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kaistart.gateway.api.service.CommonService;
import com.kaistart.gateway.api.service.GatewayApiResultService;
import com.kaistart.gateway.api.service.GatewayApiService;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayApiResultDO;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * 网关api响应结果控制层
 * @author chenhailong
 * @date 2018年8月20日 下午6:44:22
 */
@Controller
@RequestMapping( "/gateway/apiresult" )
public class GatewayApiResultController extends CommonController<GatewayApiResultDO>{

	private static final Logger logger = LoggerFactory.getLogger( GatewayApiResultController.class );

	@Resource
	private GatewayApiResultService gatewayApiResultService;
	
	@Resource
	private GatewayApiService gatewayApiService;
	
  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayApiResultService;
  }
  
  @RequestMapping("/delete")
  public JsonResp delete(Long id, Model model) throws Exception {
    gatewayApiResultService.delete(id);
    return new JsonResp(JsonResp.OK, "");
  }
  
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/newput")
  @Override
  public JsonResp put(GatewayApiResultDO bean) throws Exception {
    String example = bean.getExample();
    GatewayApiDO gatewayApi = gatewayApiService.selectById(bean.getApiId());
  //回调接口不验证json格式
    if(gatewayApi.getSpecial() < GatewayApiDO.SPECIAL_TYPE_3) { 
      try {
        JSON.parseObject(example);
      }catch(Exception e) {
        logger.info("explain api example failed!");
        return new JsonResp(JsonResp.ERROR, "保存到example数据不是标准的json格式,请修改后保存!");
      }
    }
    getService().update(bean);
    return getSuccessJsonResp();
  }
}
	
