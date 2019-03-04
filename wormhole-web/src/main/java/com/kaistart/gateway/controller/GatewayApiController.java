package com.kaistart.gateway.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kaistart.auth.domain.User;
import com.kaistart.gateway.api.service.CommonService;
import com.kaistart.gateway.api.service.GatewayApiGroupService;
import com.kaistart.gateway.api.service.GatewayApiMarkService;
import com.kaistart.gateway.api.service.GatewayApiResultService;
import com.kaistart.gateway.api.service.GatewayApiService;
import com.kaistart.gateway.api.service.GatewayServiceRequestService;
import com.kaistart.gateway.api.service.GatewayWebService;
import com.kaistart.gateway.common.GatewayConstants;
import com.kaistart.gateway.common.http.HttpClientUtils;
import com.kaistart.gateway.config.MgrConfig;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayApiGroupDO;
import com.kaistart.gateway.domain.GatewayApiRequestDO;
import com.kaistart.gateway.domain.GatewayApiResultDO;
import com.kaistart.gateway.domain.GatewayApiSearchDO;
import com.kaistart.gateway.domain.GatewayMarkDO;
import com.kaistart.gateway.domain.GatewayServiceRequestDO;
import com.kaistart.gateway.dto.GatewaySignRequestDto;
import com.kaistart.gateway.exception.GatewayMgrException;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * 网关api控制层
 * @author chenhailong
 * @date 2018年8月20日 下午6:44:22
 */
@Controller
@RequestMapping( "/gateway/api" )
public class GatewayApiController extends CommonController<GatewayApiDO> {

  @Resource
  private GatewayApiService gatewayApiService;
  
  @Resource
  private GatewayApiMarkService gatewayApiMarkService;
  
  @Resource
  private GatewayWebService gatewayWebService;
  
  @Resource
  private GatewayApiResultService gatewayApiResultService;
  
  @Resource
  private GatewayServiceRequestService gatewayServiceRequestService;
  
  @Resource
  private GatewayApiGroupService gatewayApiGroupService;
  
  @Resource
  private MgrConfig mgrConfig;
  
	
  @RequestMapping("/edit")
  public String edit(Long id, Model model) throws Exception {
    GatewayApiDO gatewayApi = gatewayApiService.selectById(id);
    
    StringBuffer sb = new StringBuffer();
    sb.append("<option value='未知'>未知</option >");
    String auths = mgrConfig.getAuths();
    if(auths.length() > 0) {
      String[] auth = auths.split(";");
      if(auth.length > 0) {
        for(int i = 0;i<auth.length;i++) {
          if(gatewayApi.getUpdateBy()!=null && gatewayApi.getUpdateBy().equals(auth[i])){
            sb.append("<option value='"+auth[i]+"' selected>"+auth[i]+"</option >");
          }else {
            sb.append("<option value='"+auth[i]+"'>"+auth[i]+"</option >");
          }
        }
      }
    }
    
    model.addAttribute("gatewayApi", gatewayApi);
    Map<String,Object> map  = new HashMap<String,Object>(8);
    map.put("apiId", id);
    List<GatewayApiResultDO> list = gatewayApiResultService.selectList(map);
    model.addAttribute("gatewayApiResult", list.size()>0?list.get(0):null);
    model.addAttribute("auths",sb.toString());
    return "pages/gateway/api/edit";
  }
  
  @RequestMapping("/detail")
  public String detail(Long id, Model model) throws Exception {
    GatewayApiDO gatewayApi = gatewayApiService.selectById(id);
    
    if(gatewayApi.getUpdateBy()==null||gatewayApi.getUpdateBy()==""||gatewayApi.getUpdateBy().equals("")){
      User user = (User)SecurityUtils.getSubject().getPrincipal();
      gatewayApi.setUpdateBy(user.getName());
    }
    
    model.addAttribute("gatewayApi", gatewayApi);
    Map<String,Object> map  = new HashMap<String,Object>(8);
    map.put("apiId", id);
    List<GatewayApiResultDO> list = gatewayApiResultService.selectList(map);
    model.addAttribute("gatewayApiResult", list.size()>0?list.get(0):null);
    
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
    String version = request.getRequestURL().toString();
    if(version.indexOf(mgrConfig.getEnvLocal()) > 0 || version.indexOf(mgrConfig.getEnvLocal2()) > 0) {
      model.addAttribute("env", "1");
    //开发环境
    }else if(version.indexOf(mgrConfig.getEnvDevIp()) > 0 ) { 
      model.addAttribute("env", "2");
    }else if(version.indexOf(mgrConfig.getEnvTestIp()) > 0 || version.indexOf(mgrConfig.getEnvTestDomain()) > 0) {
      model.addAttribute("env", "3");
    }else if(version.indexOf(mgrConfig.getEnvOnlineIp()) > 0 || version.indexOf(mgrConfig.getEnvOnlineIp2()) > 0 || version.indexOf(mgrConfig.getEnvOnlineDomain()) > 0) {
      model.addAttribute("env", "4");
    }else {
      model.addAttribute("env", "5");
    }
    return "pages/gateway/api/detail";
  }
  
  @RequestMapping("/marks")
  public JsonResp getMarks(Long id, Model model) throws Exception {
    String html1 = "一级标签：";
    String html2 = "<br>二级标签：";
    String html3 = "<br>三级标签：";
    List<GatewayMarkDO> marks = gatewayApiMarkService.selectMarks(id);
    if(marks.size() > 0) {
      for(GatewayMarkDO mark:marks) {
        if(mark.getLevel() == 1) {
          html1 += mark.getName() + ",";
        }else if(mark.getLevel() == 2) {
          html2 += mark.getName() + ",";
        }else if(mark.getLevel() == 3) {
          html3 += mark.getName() + ",";
        }
      }
    }
    return new JsonResp(JsonResp.OK, html1+html2+html3);
  }
  
  @RequestMapping("/delete")
  public JsonResp delete(Long id, Model model) throws Exception {
    gatewayApiService.delete(id);
    return new JsonResp(JsonResp.OK, "");
  }


  @SuppressWarnings("rawtypes")
  @Override
  protected CommonService getService() {
    return gatewayApiService;
  }
	
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/put")
  public JsonResp put(@RequestBody GatewayApiDO bean) throws Exception {
    getService().update(bean);
    //修改数据之后更新缓存信息
    return getSuccessJsonResp();
  }
  
  @Override
  @SuppressWarnings("unchecked")
  @ResponseBody
  @RequestMapping("/proto/post")
  public JsonResp post(@RequestBody GatewayApiDO bean) throws Exception {
    User user = (User)SecurityUtils.getSubject().getPrincipal();
    bean.setCreateBy(user.getName());
    getService().insert(bean);
    //修改数据之后更新缓存信息
    return getSuccessJsonResp(bean);
  }
	
  @RequestMapping("/refresh")
  public JsonResp refresh() throws Exception {
    return new JsonResp(JsonResp.OK, "");
  }
  
  /**
   * 点击测试之后,从后台获取接口信息并返回到页面
   * @param id
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("/testinfo")
  public String testApi(Long id, Model model ) throws Exception {
    GatewayApiDO gatewayApi = gatewayApiService.selectById(id);
    Map<String,Object> map = new HashMap<String,Object>(8);
    map.put("apiId", gatewayApi.getId());
    List<GatewayServiceRequestDO> listParams =  gatewayServiceRequestService.selectList(map);
    
    TreeMap<Integer,GatewayServiceRequestDO> treeMap = new TreeMap<Integer,GatewayServiceRequestDO>();
    for(GatewayServiceRequestDO param : listParams) {
      treeMap.put(param.getIndex(), param);
    }
    
    //url
    StringBuilder sb = new StringBuilder();
    //参数拼接
    String temp = "";
    
    //url参数
    for(int i = 0;i < treeMap.size();i++) {
      GatewayServiceRequestDO param = treeMap.get(i);
    //如果是自定义类型，body内容默认为空
      if(param.getType() == 7) { 
        model.addAttribute("body", param.getExample()); 
        continue;
      }else {
        temp += "&"+param.getName()+"="+param.getExample();
      }
    }
    
    if(gatewayApi.getSpecial() == GatewayApiDO.SPECIAL_TYPE_3) {
      sb.append("callback/");
      sb.append(gatewayApi.getName());
      if(temp.indexOf("&")>0) {
        sb.append(temp.length()>0?temp.replaceFirst("&", "?"):temp);   
      }
    }else {
    //固定参数   项目名称+固定前缀+客户端类型+客户端版本+api名称
      sb.append("");               
      sb.append("api?");                   
      sb.append("&client=android");        
      sb.append("&clientversion=1.0.0.1"); 
      sb.append("&apiName="+gatewayApi.getName() );  
      sb.append(temp); 
    }
    
    
    //存储页面逻辑判断数据
    model.addAttribute("apiId", gatewayApi.getId());
    model.addAttribute("desc", gatewayApi.getDescription());
    model.addAttribute("interfaceDesc", (gatewayApi.getIsAuth()==1?"需要鉴权":"无需鉴权") + (gatewayApi.getIsLogin()==1?"需要登录":"无需登录"));
    model.addAttribute("islogin", gatewayApi.getIsLogin());
    model.addAttribute("isauth", gatewayApi.getIsAuth());
    model.addAttribute("type", gatewayApi.getHttpMethod());
    model.addAttribute("special", gatewayApi.getSpecial());
    model.addAttribute("url", sb.toString());
    model.addAttribute("t", System.currentTimeMillis());
    
    String userId = "6B95182C82AA3272E050A00ACC3C431F";
    if(gatewayApi.getIsLogin() == 0) {
      userId = ""; 
    }
    model.addAttribute("userId", userId);
    
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
    
    String version = request.getRequestURL().toString();
    if(version.indexOf(mgrConfig.getEnvLocal()) > 0 || version.indexOf(mgrConfig.getEnvLocal2()) > 0) {
      model.addAttribute("env", "1");
    }else if(version.indexOf(mgrConfig.getEnvDevIp()) > 0 ) { 
      model.addAttribute("env", "2");
    }else if(version.indexOf(mgrConfig.getEnvTestIp()) > 0 || version.indexOf(mgrConfig.getEnvTestDomain()) > 0) {
      model.addAttribute("env", "3");
    }else if(version.indexOf(mgrConfig.getEnvOnlineIp()) > 0 || version.indexOf(mgrConfig.getEnvOnlineIp2()) > 0 || version.indexOf(mgrConfig.getEnvOnlineDomain()) > 0) {
      model.addAttribute("env", "4");
    }else {
      model.addAttribute("env", "5");
    }
    
    map = new HashMap<String,Object>(8);
    map.put("apiId", id);
    List<GatewayApiResultDO> list = gatewayApiResultService.selectList(map);
    if(list.size()>0) {
      GatewayApiResultDO result = list.get(0);
      model.addAttribute("response","预期结果："+result.getResultDesc());
    }else {
      model.addAttribute("response","预期结果："+null);

    }
    return "pages/gateway/api/test";
  }
  
  @SuppressWarnings("rawtypes")
  @RequestMapping("/tryTest")
  public JsonResp tryTest(@RequestBody Map<String,Object> map,HttpServletRequest request ) {
    String type = map.get("type").toString();
    String env = map.get("env").equals("-1")?map.get("self")==null?"":map.get("self").toString():map.get("env").toString();
    String url = env + (map.get("url") == null ? "" : map.get("url").toString());
    String mock = map.get("mock")==null?"":map.get("mock").toString();
    boolean callback = false;
    
    try {
      GatewayApiDO gatewayApi = gatewayApiService.selectById(Long.parseLong(map.get("id").toString())); 
      if(gatewayApi.getSpecial() == GatewayApiDO.SPECIAL_TYPE_3) {
        callback = true;
      }
      
      if(!callback) {
        //判断url中是否包含固定字符
        if(url.indexOf(":")<0 || map.get("id") == null) {
          return new JsonResp(JsonResp.ERROR, "请输入正确的url地址!");
        }
        if(!StringUtils.isEmpty(mock)) {
          url = url+"&mock="+mock;
        }
      }else {
        if(url.indexOf("callback") < 0 ) {
          return new JsonResp(JsonResp.ERROR, "请输入正确的url地址!");
        }
        if(!StringUtils.isEmpty(mock)) {
          if(url.indexOf("?")>0) {
            url = url+"&mock="+mock;
          }else {
            url = url+"?mock="+mock;
          }
        }
      }
    
      if(gatewayApi.getIsAuth() == 1) {
        if( map.get("header_value2") == null || map.get("header_value2").toString()=="" || map.get("header_value2").toString().length() <30) {
          return new JsonResp(JsonResp.ERROR, "鉴权时,必要的header参数 appKey没有传或者appKey有误!!!");
        }
        boolean bool = gatewayApi.getIsLogin() == 1 && (map.get("header_value4") == null || map.get("header_value4").toString()=="" || map.get("header_value4").toString().length() <30);
        if(bool) {
          return new JsonResp(JsonResp.ERROR, "验证登录时,必要的header参数 uid没有传或者uid有误!!!");
        }
        
        GatewaySignRequestDto gatewaySignRequestDto = new GatewaySignRequestDto();
        Map<String,String> headers = new HashMap<String,String>(8);
        //处理头部参数
        String kfix = "header_key",vfix = "header_value";
        ArrayList al = (ArrayList)map.get("enables");
        for(int i = 0;i < al.size();i++) {
          Object key = map.get(kfix+al.get(i));
          if(key == null) { continue; }
          if(map.get(kfix+al.get(i)) == null) { continue; }
            headers.put(key+"", URLEncoder.encode(map.get(vfix+al.get(i)).toString(), "utf-8"));
        }
        
        Map<String,String> params = new HashMap<String,String>(8);
        String[] urlparams = url.split("&");
        int count = urlparams.length;
        if(count > 0) {
          for(int i = 0; i<count; i++) {
          //排除非url传递的参数
            if(urlparams[i].indexOf("=") > 0) {
              String[] inner = urlparams[i].split("=");
              if(inner.length > 1) {
                String temp = URLEncoder.encode(inner[1], "utf-8");
                params.put(inner[0], temp);
              }else {
                params.put(inner[0], "");
              }
            }
          }
        }
        
        String bodyContent = map.get("body")==null?"":map.get("body").toString();
        headers.put("contentLength", "".equals(bodyContent)?"-1":bodyContent.getBytes().length + "");
      //application/x-www-form-urlencoded这种类型，需要把参数拼接到 url中
        if(map.get("header_value1").toString().equals("application/x-www-form-urlencoded")) { 
          String[] urlencoded = bodyContent.split("\r\n");
          for(int i = 0;i<urlencoded.length;i++) {
            String[] inner = urlencoded[i].split(":");
            params.put(inner[0], inner[1]);
            map.put(inner[0], inner[1]);
          }
          headers.put("contentLength", "-1");
        }
        //设置 api信息 + header参数 + url参数
        gatewaySignRequestDto.setApiInfo(gatewayApi);
        gatewaySignRequestDto.setHeaders(headers);   
        gatewaySignRequestDto.setParams(params);     
        String sign = gatewayWebService.getGatewayAuthSign(gatewaySignRequestDto);
        String[] md5 = sign.split("###");
        //将 sign + auth 放到对应的header参数中
        map.put("header_value3",md5[0]); 
        map.put("header_value7",md5[1]); 
      }
      
    }catch(GatewayMgrException e) {
      return new JsonResp(JsonResp.ERROR, e.getMessage());
    }catch(Exception e) {
      return new JsonResp(JsonResp.ERROR, e.getMessage());
    }

    //url encode处理
    try {
      //如果是回调型接口,不走这个逻辑
      if(!callback) { 
      //地址的右边部分
        String partRight = url.substring(url.indexOf("?")); 
      //地址左边部分
        url = url.substring(0, url.indexOf("?")) + "?";      
        String[] urlparams = partRight.split("&");
        int count = urlparams.length;
        if(count > 0) {
          for(int i = 0; i<count; i++) {
          //排除非url传递的参数
            if(urlparams[i].indexOf("=") > 0) {
              String[] inner = urlparams[i].split("=");
              if(inner.length > 1) {
                  url += inner[0] + "=" + URLEncoder.encode(inner[1], "utf-8") + "&";
              }else {
                url += inner[0] + "=&";
              }
            }
          }
        }
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    ObjectNode json = null;
    if(type == GatewayConstants.HTTP_GET || GatewayConstants.HTTP_GET.equals(type)) {
      json = HttpClientUtils.get(url,map);
    }else {
      json = HttpClientUtils.post(url,map);
    }
    //将计算好的签名返回页面
    String sign = map.get("header_value3")==null ? "":map.get("header_value3").toString();
    String auth = map.get("header_value7")==null ? "":map.get("header_value7").toString();
    return new JsonResp(sign+"###"+auth, json==null?"":json.toString());
  }
  
  
  
  @ResponseBody
  @RequestMapping(value = "/proto/search")
  public JsonResp searchList(GatewayApiSearchDO bean, Integer pageIndex, Integer pageSize) throws Exception {

    Map<String, Object> map = getPagingQryMapForSearch(bean, pageIndex, pageSize);
    Integer count = gatewayApiService.selectSearchCount(map);
    List<GatewayApiDO> list = null;
    try {
      list = gatewayApiService.selectSearchPage(map);
    } catch (Exception e) {
    }
    return getSuccessJsonResp(list, count);

  }
  
  
  
  /**
   * 发送接口的数据到线上
   * @param bean
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/send")
  public JsonResp toRemote(Long id) throws Exception {
    
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
    String version = request.getRequestURL().toString();
    if(version.indexOf(mgrConfig.getEnvLocal()) > 0 || version.indexOf(mgrConfig.getEnvLocal2()) > 0) {
      return new JsonResp(JsonResp.ERROR, "检测到当前环境为local,不能操作同步功能!");
    }else if(version.indexOf(mgrConfig.getEnvDevIp()) > 0 ) { 
      return new JsonResp(JsonResp.ERROR, "检测到当前环境为dev,不能操作同步功能!");
    }else if(version.indexOf(mgrConfig.getEnvTestDomain()) > 0 || version.indexOf(mgrConfig.getEnvTestIp()) > 0) {
    }else if(version.indexOf(mgrConfig.getEnvOnlineIp()) > 0 || version.indexOf(mgrConfig.getEnvOnlineIp2()) > 0 || version.indexOf(mgrConfig.getEnvOnlineDomain()) > 0) {
     
    }else {
      return new JsonResp(JsonResp.ERROR, "检测到当前环境为未知,不能操作同步功能!");
    }
    
    //API基本信息
    GatewayApiDO gatewayApi = gatewayApiService.selectById(id);
    if(gatewayApi == null) {
      return new JsonResp(JsonResp.ERROR, "API信息不存在,请确定之后再进行迁移!");
    }
    
    if(gatewayApi.getServiceVersion().indexOf(GatewayConstants.ENV_TEST)<=0) {
      return new JsonResp(JsonResp.ERROR, "请确保该接口为daily版本后,在进行此操作!");
    }
    
    if(gatewayApi.getName().indexOf(mgrConfig.getCopySuffix()) > 0) {
      return new JsonResp(JsonResp.ERROR, "接口副本不允许同步,请将源接口的版本号更改为daily后再进行同步操作!");
    }
    
    GatewayApiRequestDO api = new GatewayApiRequestDO();
    api.setGroupId(gatewayApi.getGroupId());
    api.setName(gatewayApi.getName());
    api.setDescription(gatewayApi.getDescription());
    api.setSpecial(gatewayApi.getSpecial());
    api.setStatus(gatewayApi.getStatus());
    api.setHttpMethod(gatewayApi.getHttpMethod());
    api.setIsAuth(gatewayApi.getIsAuth());
    api.setIsLogin(gatewayApi.getIsLogin());
    api.setAuthVersion(gatewayApi.getAuthVersion());
    api.setServiceName(gatewayApi.getServiceName());
    api.setServiceMethod(gatewayApi.getServiceMethod());
    api.setServiceVersion(gatewayApi.getServiceVersion());
    api.setTimeOut(gatewayApi.getTimeOut());
    api.setCreateBy(gatewayApi.getCreateBy());
    api.setUpdateBy(gatewayApi.getUpdateBy());
    api.setCdt(new Date());
    api.setUdt(new Date());
    api.setVersion(gatewayApi.getVersion());
    
    //API组信息
    GatewayApiGroupDO group = gatewayApiGroupService.selectById(gatewayApi.getGroupId());
    api.setGroupName(group.getName());
    
    //API响应信息
    Map<String,Object> map  = new HashMap<String,Object>(8);
    map.put("apiId", id);
    List<GatewayApiResultDO> list = gatewayApiResultService.selectList(map);
    if(list.size()<1) {
      return new JsonResp(JsonResp.ERROR, "API信息不完整,请录入完成之后进行迁移!");
    }
    GatewayApiResultDO result = list.get(0);
    result.setCdt(null);
    result.setUdt(null);
    api.setGatewayApiResultDo(result);
    
    //API参数信息
    map = new HashMap<String,Object>(8);
    map.put("apiId", gatewayApi.getId());
    List<GatewayServiceRequestDO> listParams =  gatewayServiceRequestService.selectList(map);
    List<GatewayServiceRequestDO> params = new ArrayList<GatewayServiceRequestDO>();
    for(GatewayServiceRequestDO param : listParams) {
      param.setCdt(null);
      param.setUdt(null);
      params.add(param);
    }
    api.setParamList(params);
    ObjectNode json = HttpClientUtils.post(mgrConfig.getEnvOnlineReciveDate(),JSONObject.toJSON(api).toString());
    String status = json.get("status").toString();
    if(status.contains("error")) {
      return new JsonResp(JsonResp.ERROR, json.get("data"));
    }
    return new JsonResp(JsonResp.OK, json.get("data"));
  }
  
  
  /**
   * 接收post请求
   * @param bean
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/recive",method = RequestMethod.POST)
  public JsonResp fromRemote(@RequestBody GatewayApiRequestDO bean) throws Exception {
    boolean bool = gatewayApiService.transferData(bean);
    if(bool) {
      return new JsonResp(JsonResp.OK, "接口同步成功!");
    }else {
      return new JsonResp(JsonResp.ERROR, "接口同步失败!详情请查看日志!");
    }
  }
  
  /**
   * 复制接口功能
   * @param bean
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/copy")
  public JsonResp copyApi(Long id) throws Exception {
    
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
    String version = request.getRequestURL().toString();
    if(version.indexOf(mgrConfig.getEnvLocal()) > 0 || version.indexOf(mgrConfig.getEnvLocal2()) > 0) {
    }else if(version.indexOf(mgrConfig.getEnvDevIp()) > 0 ) { 
    }else if(version.indexOf(mgrConfig.getEnvTestIp()) > 0 || version.indexOf(mgrConfig.getEnvTestDomain()) > 0) {
    }else if(version.indexOf(mgrConfig.getEnvOnlineIp()) > 0 || version.indexOf(mgrConfig.getEnvOnlineIp2()) > 0 || version.indexOf(mgrConfig.getEnvOnlineDomain()) > 0) {
      return new JsonResp(JsonResp.ERROR, "检测到当前环境为online,不能操作复制功能!");
    }else {
      return new JsonResp(JsonResp.ERROR, "检测到当前环境为未知,不能操作复制功能!");
    }
    
    //API基本信息
    GatewayApiDO gatewayApi = gatewayApiService.selectById(id);
    if(gatewayApi == null) {
      return new JsonResp(JsonResp.ERROR, "API信息不存在,请确定之后再进行迁移!");
    }
    
    Map<String,Object> mapCopy  = new HashMap<String,Object>(8);
    mapCopy.put("name", gatewayApi.getName() + mgrConfig.getCopySuffix());
    List<GatewayApiDO> copyList = gatewayApiService.selectList(mapCopy);
    if(copyList.size()>0) {
      return new JsonResp(JsonResp.ERROR, "API已经存在副本,请确定后在进行操作!");
    }
    
    if(gatewayApi.getServiceVersion().indexOf(GatewayConstants.ENV_TEST)<=0) {
      return new JsonResp(JsonResp.ERROR, "请确保该接口为daily版本后,在进行此操作!");
    }
    
    GatewayApiRequestDO api = new GatewayApiRequestDO();
    api.setGroupId(gatewayApi.getGroupId());
    api.setName(gatewayApi.getName()+"#copy");
    api.setDescription(gatewayApi.getDescription());
    api.setSpecial(gatewayApi.getSpecial());
    api.setStatus(gatewayApi.getStatus());
    api.setHttpMethod(gatewayApi.getHttpMethod());
    api.setIsAuth(gatewayApi.getIsAuth());
    api.setIsLogin(gatewayApi.getIsLogin());
    api.setAuthVersion(gatewayApi.getAuthVersion());
    api.setServiceName(gatewayApi.getServiceName());
    api.setServiceMethod(gatewayApi.getServiceMethod());
    api.setServiceVersion(gatewayApi.getServiceVersion());
    api.setTimeOut(gatewayApi.getTimeOut());
    api.setCreateBy(gatewayApi.getCreateBy());
    api.setUpdateBy(gatewayApi.getUpdateBy());
    api.setCdt(new Date());
    api.setUdt(new Date());
    api.setVersion(gatewayApi.getVersion());
    
    //API组信息
    GatewayApiGroupDO group = gatewayApiGroupService.selectById(gatewayApi.getGroupId());
    api.setGroupName(group.getName());
    
    //API响应信息
    Map<String,Object> map  = new HashMap<String,Object>(8);
    map.put("apiId", id);
    List<GatewayApiResultDO> list = gatewayApiResultService.selectList(map);
    if(list.size()<1) {
      return new JsonResp(JsonResp.ERROR, "API信息不完整,请补充完整之后进行复制!");
    }
    GatewayApiResultDO result = list.get(0);
    result.setCdt(null);
    result.setUdt(null);
    api.setGatewayApiResultDo(result);
    
    //API参数信息
    map = new HashMap<String,Object>(8);
    map.put("apiId", gatewayApi.getId());
    List<GatewayServiceRequestDO> listParams =  gatewayServiceRequestService.selectList(map);
    List<GatewayServiceRequestDO> params = new ArrayList<GatewayServiceRequestDO>();
    for(GatewayServiceRequestDO param : listParams) {
      param.setCdt(null);
      param.setUdt(null);
      params.add(param);
    }
    
    api.setParamList(params);
    boolean bool = gatewayApiService.copyData(api);
    if(bool) {
      return new JsonResp(JsonResp.OK, "接口复制成功!");
    }else {
      return new JsonResp(JsonResp.ERROR, "接口复制失败!详情请查看日志!");
    }
  }
  
  
  
  @ResponseBody
  @RequestMapping("/get/auths")
  public JsonResp auths() throws Exception {
    StringBuffer sb = new StringBuffer();
    String auths = mgrConfig.getAuths();
    if(auths.length() > 0) {
      sb.append("<option value='未知'>未知</option >");
      String[] auth = auths.split(";");
      if(auth.length > 0) {
        for(int i = 0;i<auth.length;i++) {
          sb.append("<option value='"+auth[i]+"'>"+auth[i]+"</option >");
        }
      }
    }
    return getSuccessJsonResp(sb.toString());
  }
  
  
}
