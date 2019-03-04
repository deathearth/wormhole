package com.kaistart.gateway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.User;
import com.kaistart.gateway.api.service.CommonService;
import com.kaistart.gateway.api.service.GatewayApiMarkService;
import com.kaistart.gateway.api.service.GatewayMarkService;
import com.kaistart.gateway.domain.GatewayApiGroupDO;
import com.kaistart.gateway.domain.GatewayApiMarkDO;
import com.kaistart.gateway.domain.GatewayMarkDO;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * 网关api标签设置
 * @author chenhailong
 * @date 2018年8月20日 下午6:44:22
 */
@Controller
@RequestMapping( "/gateway/mark" )
public class GatewayMarkController extends CommonController<GatewayMarkDO>{

//	private static final Logger logger = LoggerFactory.getLogger( GatewayAppController.class );

	@Resource
	private GatewayMarkService gatewayMarkService;
	
	@Resource
    private GatewayApiMarkService gatewayApiMarkService;
	
  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayMarkService;
  }
  
  
  @SuppressWarnings("unchecked")
  @RequestMapping("/edit")
  public String edit(Long id, Model model) throws Exception {
    GatewayMarkDO gatewayMark = gatewayMarkService.selectById(id);
    
    if(gatewayMark.getUpdateBy()==null||gatewayMark.getUpdateBy()==""||gatewayMark.getUpdateBy().equals("")){
      User user = (User)SecurityUtils.getSubject().getPrincipal();
      gatewayMark.setUpdateBy(user.getName());
    }
    
    model.addAttribute("gatewayMark", gatewayMark);
    //查询一级标签
    Map<String, Object> map = new HashMap<String,Object>(8);
    map.put("status", GatewayMarkDO.STATUS_1);
    map.put("level", GatewayMarkDO.LEVEL_1);
    List<GatewayMarkDO> root = getService().selectList(map);
    model.addAttribute("root", root);
    //查询二级标签
    map = new HashMap<String,Object>(8);
    map.put("status", GatewayMarkDO.STATUS_1);
    map.put("level", GatewayMarkDO.LEVEL_2);
    map.put("rootId", gatewayMark.getRootId());
    List<GatewayMarkDO> branch = getService().selectList(map);
    model.addAttribute("branch", branch);
    return "pages/gateway/mark/edit";
  }
  
  
  @RequestMapping("/delete")
  public JsonResp delete(Long id, Model model) throws Exception {
    gatewayMarkService.delete(id);
    return new JsonResp(JsonResp.OK, "");
  }
  
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/post")
  public JsonResp post(@RequestBody GatewayMarkDO bean) throws Exception {
    User user = (User)SecurityUtils.getSubject().getPrincipal();
    bean.setCreateBy(user.getName());
    
    if(null == bean.getRootId() || bean.getRootId() == 0) {
      bean.setLevel(GatewayMarkDO.LEVEL_1);
    }else {
      if(null == bean.getBranchId() || bean.getBranchId() == 0) {
        bean.setLevel(GatewayMarkDO.LEVEL_2);
      }else {
        if(null == bean.getLeafId() || bean.getLeafId() == 0) {
          bean.setLevel(GatewayMarkDO.LEVEL_3);
        }
      }
    }
    getService().insert(bean);
    return getSuccessJsonResp(bean);
  }
  
  
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping(value = "/proto/index")
  public JsonResp index(GatewayMarkDO bean, Integer pageIndex, Integer pageSize) throws Exception {
    Map<String, Object> map = getPagingQryMap(bean, pageIndex, pageSize);
    map.put("status", GatewayMarkDO.STATUS_1);
    Integer count = getService().selectCount(map);
    List<GatewayApiGroupDO> list;
    try {
      list = getService().selectPage(map);
    } catch (Exception e) {
      list = getService().selectList(map);
    }
    return getSuccessJsonResp(list, count);
  }
  
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping(value = "/proto/select/level/one")
  public JsonResp getSelectOne(GatewayMarkDO bean) throws Exception {
    Map<String, Object> map = new HashMap<String,Object>(8);
    map.put("status", GatewayMarkDO.STATUS_1);
    map.put("level", GatewayMarkDO.LEVEL_1);
    List<GatewayMarkDO> list = getService().selectList(map);
    return getSuccessJsonResp(list);
  }
  
  /**
   * 根据一级标签查询二级标签
   * @param bean
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping(value = "/proto/select/level/two")
  public JsonResp getSelectTwo(GatewayMarkDO bean) throws Exception {
    Map<String, Object> map = new HashMap<String,Object>(8);
    map.put("status", GatewayMarkDO.STATUS_1);
    map.put("level", GatewayMarkDO.LEVEL_2);
    map.put("rootId", bean.getRootId());
    List<GatewayMarkDO> list = getService().selectList(map);
    return getSuccessJsonResp(list);
  }
  
  /**
   * 根据一级、二级标签查询三级标签
   * @param bean
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping(value = "/proto/select/level/three")
  public JsonResp getSelectThree(GatewayMarkDO bean) throws Exception {
    Map<String, Object> map = new HashMap<String,Object>(8);
    map.put("status", GatewayMarkDO.STATUS_1);
    map.put("level", GatewayMarkDO.LEVEL_3);
    map.put("rootId", bean.getRootId());
    map.put("branchId", bean.getBranchId());
    List<GatewayMarkDO> list = getService().selectList(map);
    return getSuccessJsonResp(list);
  }
  
  @ResponseBody
  @RequestMapping(value = "/proto/check/list")
  public JsonResp checkList(
      @RequestParam(value = "apiId", required = true) Integer apiId,
      @RequestParam(value = "rootId", required = false) Integer rootId,
      @RequestParam(value = "branchId", required = false) Integer branchId,
      @RequestParam(value = "leafId", required = false) Integer leafId,
      @RequestParam(value = "skip", required = true) Integer skip,
      @RequestParam(value = "size", required = true) Integer size) throws Exception {
    
    Map<String, Object> map = new HashMap<String,Object>(8);
    map.put("apiId", apiId);
    map.put("rootId", rootId);
    map.put("branchId", branchId);
    map.put("leafId", leafId);
    map.put("skip",(skip-1)*size);
    map.put("size", size);
    Integer count = gatewayMarkService.selectCount(map);
    List<Map<String,Object>> list = gatewayMarkService.selectCheckPage(map);
    return getSuccessJsonResp(list, count);
  }
  
  
  @SuppressWarnings("unchecked")
  @RequestMapping("/check")
  public String check(Long apiId, Model model) throws Exception {
    model.addAttribute("apiId", apiId);
   //查询一级标签
    Map<String, Object> map = new HashMap<String,Object>(8);
    map.put("status", GatewayMarkDO.STATUS_1);
    List<GatewayMarkDO> root = getService().selectList(map);
    model.addAttribute("root", root);
    return "pages/gateway/mark/listForCheck";
  }
  
  
  @ResponseBody
  @RequestMapping(value = "/relationship")
  public JsonResp relationship(@RequestParam(value = "checks", required = true) String checks,
      @RequestParam(value = "ids", required = true) String ids, @RequestParam(value = "apiId", required = true) Long apiId
      ) throws Exception {
    
    if( ids.length() > 0 || checks.length() > 0 ) {
      List<GatewayMarkDO> marks = gatewayApiMarkService.selectMarks(apiId);
      
      String[] check = new String[0];
      if(checks.length() > 0) {
        check = checks.indexOf(",")<0?(new String[] {checks}):checks.split(",");
      }
      String[] id = new String[0];
      if(ids.length() > 0) {
        id = ids.indexOf(",")<0?(new String[] {ids}):ids.split(",");
      }
    //插入新数据
      for(int count = 0;count<check.length;count++) { 
        if(marks.size()> 0) {
          for(GatewayMarkDO mark :marks) {
          //如果存在就跳出循环
            if(mark.getId() == Long.parseLong(check[count])) { 
              break;
            }else {
              GatewayApiMarkDO apiMark = new GatewayApiMarkDO();
              apiMark.setMarkId(Long.parseLong(check[count]));
              apiMark.setApiId(apiId);
              gatewayApiMarkService.insert(apiMark);
            }
          }
        }else {
          GatewayApiMarkDO apiMark = new GatewayApiMarkDO();
          apiMark.setMarkId(Long.parseLong(check[count]));
          apiMark.setApiId(apiId);
          gatewayApiMarkService.insert(apiMark);
        }
      }
      
      //删除未选中的信息
      for(int count = 0;count<id.length;count++) {
         GatewayApiMarkDO apiMark = new GatewayApiMarkDO();
         apiMark.setMarkId(Long.parseLong(id[count]));
         apiMark.setApiId(apiId);          
         gatewayApiMarkService.deleteDB(apiMark);
      }
    }
    return new JsonResp(JsonResp.OK, "");
  }
  
}
	
