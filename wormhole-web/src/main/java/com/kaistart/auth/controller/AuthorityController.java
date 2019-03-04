package com.kaistart.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kaistart.auth.domain.Authorization;
import com.kaistart.auth.domain.Permission;
import com.kaistart.auth.domain.User;
import com.kaistart.auth.service.AuthorizationService;
import com.kaistart.auth.service.PermissionService;
import com.kaistart.auth.service.ResourceService;
import com.kaistart.gateway.support.json.JsonResp;

/**
 * Created by Administrator on 2016/12/23.
 * @author 
 */
@Controller
@RequestMapping("/auth")
public class AuthorityController {

  private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);

  @Resource
  private ResourceService resourceService;

  @Resource
  private PermissionService permissionService;

  @Resource
  private AuthorizationService authorizationService;


  @RequestMapping({"/authz"})
  @ResponseBody
  public JsonResp authz() throws Exception {
    Map<String, Object> map = new HashMap<String, Object>(2);
    Set<Integer> set = new HashSet<Integer>();

    List<Integer> roleIds = new ArrayList<Integer>();
    List<Integer> resourceIds = new ArrayList<Integer>();

    User user = (User) SecurityUtils.getSubject().getPrincipal();
    List<Authorization> authorizationList = this.authorizationService.selectByUserId(user.getId());
    for (Authorization authorization : authorizationList) {
      roleIds.add(authorization.getRoleId());
    }
    map.put("roleIds", roleIds);

    List<Permission> permissionList = this.permissionService.selectList(map);
    for (Permission permission : permissionList) {
      resourceIds.add(permission.getResId());
    }
    set.addAll(resourceIds);
    resourceIds.clear();
    resourceIds.addAll(set);

    map.clear();
    map.put("resourceIds", resourceIds);

    List<com.kaistart.auth.domain.Resource> resourceList = this.resourceService.selectList(map);

    return new JsonResp("ok", resourceList);
  }



  @RequestMapping("/index")
  public ModelAndView index() {

    ModelAndView mav = new ModelAndView("pages/index/index");

    Map<String, Object> map = new HashMap<>(8);
    Set<Integer> set = new HashSet<>();

    List<Integer> roleIds = new ArrayList<>();
    List<Integer> resourceIds = new ArrayList<>();

    List<com.kaistart.auth.domain.Resource> resourceList;
    List<Permission> permissionList;
    List<Authorization> authorizationList;

    try {

      User user = (User) SecurityUtils.getSubject().getPrincipal();
      authorizationList = authorizationService.selectByUserId(user.getId());
      for (Authorization authorization : authorizationList) {
        roleIds.add(authorization.getRoleId());
      }

      map.put("roleIds", roleIds);

      permissionList = permissionService.selectList(map);
      for (Permission permission : permissionList) {
        resourceIds.add(permission.getResId());
      }
      set.addAll(resourceIds);
      resourceIds.clear();
      resourceIds.addAll(set);

      map.clear();
      map.put("resourceIds", resourceIds);

      resourceList = resourceService.selectList(map);
      mav.addObject("resourceList", resourceList);

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getLocalizedMessage(), e);
    }

    return mav;

  }

  @RequestMapping("/main")
  public String main() {
    return "authority/main";
  }

  @RequestMapping("/login")
  public String login() {
    return "authority/login";
  }

  @RequestMapping("/authc")
  public String authc(String username, String password) {

    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    token.setRememberMe(true);

    try {

      Subject subject = SecurityUtils.getSubject();
      subject.login(token);

      return "redirect:index";

    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return "login";
    }

  }

  @RequestMapping("/logout")
  public String logout() {

    Subject subject = SecurityUtils.getSubject();
    subject.logout();

    return "login";

  }

}
