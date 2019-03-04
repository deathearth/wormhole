package com.kaistart.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.Permission;
import com.kaistart.auth.service.PermissionService;
import com.kaistart.gateway.support.json.JsonResp;
import com.kaistart.gateway.support.proto.ProtoController;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * Created by Administrator on 2016/12/20.
 * @author
 */
@Controller
@RequestMapping("/auth/permission")
public class PermissionController extends ProtoController<Permission> {

  private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

  @Resource
  private PermissionService permissionService;

  @Override
  protected ProtoService<?> getService() {
    return permissionService;
  }

  @RequestMapping("/get")
  @ResponseBody
  public JsonResp selectByRoleId(Integer roleId) {
    try {
      List<Permission> permissionList = permissionService.selectByRoleId(roleId);
      return this.getSuccessJsonResp(permissionList);
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return this.getErrorJsonResp(e);
    }
  }

  @RequestMapping("/authz")
  @ResponseBody
  public JsonResp authz(Integer roleId, String resourceIds) {
    try {
      permissionService.authz(roleId, resourceIds);
      return this.getSuccessJsonResp();
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return this.getErrorJsonResp(e);
    }
  }

}
