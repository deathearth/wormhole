package com.kaistart.gateway.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.User;
import com.kaistart.gateway.api.service.CommonService;
import com.kaistart.gateway.api.service.GatewayApiGroupService;
import com.kaistart.gateway.domain.GatewayApiGroupDO;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * 网关api组控制层
 * @author chenhailong
 * @date 2018年8月20日 下午6:44:22
 */
@Controller
@RequestMapping( "/gateway/apigroup" )
public class GatewayApiGroupController extends CommonController<GatewayApiGroupDO>{

//  private static final Logger logger = LoggerFactory.getLogger( GatewayApiGroupController.class );
  
  @Resource
  private GatewayApiGroupService gatewayApiGroupService;
  
  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayApiGroupService;
  }

  @RequestMapping("/edit")
  public String edit(Long id, Model model) throws Exception {
    GatewayApiGroupDO gatewayApiGroup = gatewayApiGroupService.selectById(id);
    if(gatewayApiGroup.getUpdateBy()==null||gatewayApiGroup.getUpdateBy()==""){
      User user = (User)SecurityUtils.getSubject().getPrincipal();
      gatewayApiGroup.setUpdateBy(user.getName());
    }
    model.addAttribute("gatewayApiGroup", gatewayApiGroup);
    return "pages/gateway/apigroup/edit";
  }
  
  
  @RequestMapping("/delete")
  public JsonResp delete(Long id, Model model) throws Exception {
    gatewayApiGroupService.delete(id);
    return new JsonResp(JsonResp.OK, "");
  }
  
  @RequestMapping("/detail")
  public String showDetail(Long id, Model model) throws Exception {
    model.addAttribute("groupId", id);
    return "pages/gateway/api/listFromGroup";
  }
  
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/post")
  public JsonResp post(@RequestBody GatewayApiGroupDO bean) throws Exception {
    
    bean.setName(bean.getName().toLowerCase());
    User user = (User)SecurityUtils.getSubject().getPrincipal();
    bean.setCreateBy(user.getName());
    getService().insert(bean);
    return getSuccessJsonResp(bean);
  }
  
  
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping(value = "/proto/index")
  public JsonResp index(GatewayApiGroupDO bean, Integer pageIndex, Integer pageSize) throws Exception {

    Map<String, Object> map = getPagingQryMap(bean, pageIndex, pageSize);
    map.put("status", "1");
    Integer count = getService().selectCount(map);
    List<GatewayApiGroupDO> list;
    try {
      list = getService().selectPage(map);
    } catch (Exception e) {
      list = getService().selectList(map);
    }

    return getSuccessJsonResp(list, count);

  }
}
