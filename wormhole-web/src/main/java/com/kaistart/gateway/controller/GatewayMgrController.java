package com.kaistart.gateway.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.gateway.api.service.CommonService;
import com.kaistart.gateway.api.service.GatewayAppAuthService;
import com.kaistart.gateway.domain.GatewayAppAuthDO;
import com.kaistart.gateway.dto.RequestAuthorize;
import com.kaistart.gateway.exception.DaoException;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * Created by dxc on 2018/6/11.
 * @author dxc
 *
 */
@Controller
@RequestMapping( "/gateway/mgr" )
public class GatewayMgrController extends CommonController<GatewayAppAuthDO>{

//	private static final Logger logger = LoggerFactory.getLogger( GatewayMgrController.class );

	@Resource
	private GatewayAppAuthService gatewayAppAuthService;
	
  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayAppAuthService;
  }
  
  
  @ResponseBody
  @RequestMapping(value = "/auth/grant/index")
  public JsonResp grant(RequestAuthorize bean, Integer pageIndex, Integer pageSize) throws Exception {

    Map<String, Object> map = getPagingQryMap(bean, pageIndex, pageSize);

    Integer count = gatewayAppAuthService.selectCountAuth(map);
    List<RequestAuthorize> list;
    try {
      list = gatewayAppAuthService.selectPageAuth(map);
    } catch (Exception e) {
      list = gatewayAppAuthService.selectListAuth(map);
    }

    return getSuccessJsonResp(list, count);

  }
	
  @ResponseBody
  @RequestMapping("/grant")
  public JsonResp grant(@RequestBody GatewayAppAuthDO bean)  {
    try {
      gatewayAppAuthService.grant(bean);
    } catch (Exception e) {
      return new JsonResp(JsonResp.ERROR, e.getMessage());
    }
    return new JsonResp(JsonResp.OK, "");
  }
  
  @ResponseBody
  @RequestMapping("/ungrant")
  public JsonResp ungrant(@RequestBody GatewayAppAuthDO bean)  {
    try{
      gatewayAppAuthService.ungrant(bean);
    }catch(DaoException e){
      return new JsonResp(JsonResp.ERROR, e.getMessage());
    }catch(Exception e){
      
    }
    return new JsonResp(JsonResp.OK, "");
  }
  
  
  
  
  protected Map<String, Object> getQryMap(RequestAuthorize bean) throws IllegalAccessException {
    Map<String, Object> map = new HashMap<String, Object>(8);

    if (bean != null) {
      for (Field field : bean.getClass().getDeclaredFields()) {
        field.setAccessible(Boolean.TRUE);
        map.put(field.getName(), field.get(bean));
      }
      
      for (Field field : bean.getClass().getSuperclass().getDeclaredFields()) {
        field.setAccessible(Boolean.TRUE);
        map.put(field.getName(), field.get(bean));
      }
      
    }

    return map;
  }

  protected Map<String, Object> getPagingQryMap(RequestAuthorize bean, Integer pageIndex, Integer pageSize) throws IllegalAccessException {
    Map<String, Object> map = getQryMap(bean);

    if (pageIndex == null || pageIndex < 1) {
      pageIndex = 1;
    }
    if (pageSize == null || pageSize < 0) {
      pageSize = 20000;
    }

    map.put("skip", (pageIndex - 1) * pageSize);
    map.put("size", pageSize);

    return map;
  }
}
