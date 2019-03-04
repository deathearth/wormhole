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
import com.kaistart.gateway.api.service.GatewayPartnerService;
import com.kaistart.gateway.domain.GatewayPartnerDO;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * 网关用户组控制层
 * @author chenhailong
 * @date 2018年8月20日 下午6:44:22
 */
@Controller
@RequestMapping( "/gateway/partner" )
public class GatewayPartnerController extends CommonController<GatewayPartnerDO>{

//	private static final Logger logger = LoggerFactory.getLogger( GatewayPartnerController.class );

	@Resource
	private GatewayPartnerService gatewayPartnerService;
	
  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayPartnerService;
  }
  
  
  @RequestMapping("/edit")
  public String edit(Long id, Model model) throws Exception {
    
    GatewayPartnerDO gatewayPartner = gatewayPartnerService.selectById(id);
    model.addAttribute("gatewayPartner", gatewayPartner);
    
    User user = (User)SecurityUtils.getSubject().getPrincipal();
    model.addAttribute("user", user.getName());
    return "pages/gateway/partner/edit";
  }
  
  @RequestMapping("/delete")
  public JsonResp delete(Long id, Model model) throws Exception {
    try{
      gatewayPartnerService.delete(id);
    }catch(Exception e){
      throw e;
    }
    return new JsonResp(JsonResp.OK, "");
  }
  
  @RequestMapping("/detail")
  public String showDetail(Long id, Model model) throws Exception {
    model.addAttribute("partnerId", id);
    return "pages/gateway/app/listFromPartner";
  }
  
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/post")
  public JsonResp post(@RequestBody GatewayPartnerDO bean) throws Exception {
    User user = (User)SecurityUtils.getSubject().getPrincipal();
    bean.setCreateBy(user.getName());
    getService().insert(bean);
    return getSuccessJsonResp(bean);
  }
}
	
