package com.kaistart.gateway.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.User;
import com.kaistart.gateway.api.service.CommonService;
import com.kaistart.gateway.api.service.GatewayAppService;
import com.kaistart.gateway.domain.GatewayAppDO;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * 网关IP设置(未生效)
 * @author chenhailong
 * @date 2018年8月20日 下午6:44:22
 */
@Controller
@RequestMapping( "/gateway/ipset" )
public class GatewayIpsetController extends CommonController<GatewayAppDO>{

//	private static final Logger logger = LoggerFactory.getLogger( GatewayAppController.class );

	@Resource
	private GatewayAppService gatewayAppService;
	
  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayAppService;
  }
  
  
  @RequestMapping("/edit")
  public String edit(Long id, Model model) throws Exception {
    GatewayAppDO gatewayApp = gatewayAppService.selectById(id);
    
    if(gatewayApp.getUpdateBy()==null||gatewayApp.getUpdateBy()==""||gatewayApp.getUpdateBy().equals("")){
      User user = (User)SecurityUtils.getSubject().getPrincipal();
      gatewayApp.setUpdateBy(user.getName());
    }
    
    model.addAttribute("gatewayApp", gatewayApp);
    return "pages/gateway/app/edit";
  }
  
  @RequestMapping("/authorize")
  public String authorize(Long id, Model model) throws Exception {
    GatewayAppDO gatewayApp = gatewayAppService.selectById(id);
    model.addAttribute("gatewayApp", gatewayApp);
    return "pages/gateway/mgr/list";
  }
  
  
  @RequestMapping("/delete")
  public JsonResp delete(Long id, Model model) throws Exception {
    gatewayAppService.delete(id);
    return new JsonResp(JsonResp.OK, "");
  }
  
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/post")
  public JsonResp post(@RequestBody GatewayAppDO bean) throws Exception {
    User user = (User)SecurityUtils.getSubject().getPrincipal();
    bean.setCreateBy(user.getName());
    getService().insert(bean);
    return getSuccessJsonResp(bean);
  }
}
	
