package com.kaistart.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.Authorization;
import com.kaistart.auth.service.AuthorizationService;
import com.kaistart.gateway.support.json.JsonResp;
import com.kaistart.gateway.support.proto.ProtoController;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * Created by Administrator on 2016/12/20.
 * @author
 */
@Controller
@RequestMapping("/auth/authorization")
public class AuthorizationController extends ProtoController<Authorization> {

  private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

  @Resource
  private AuthorizationService authorizationService;

  @SuppressWarnings("rawtypes")
  @Override
  protected ProtoService getService() {
    return authorizationService;
  }

  @RequestMapping("/authz")
  @ResponseBody
  public JsonResp authz(Integer userId, String roleIds) {
    try {
      authorizationService.authz(userId, roleIds);
      return this.getSuccessJsonResp();
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return this.getErrorJsonResp(e);
    }

  }

  @RequestMapping("/get")
  @ResponseBody
  public JsonResp selectByUserId(Integer userId) {
    try {
      List<Authorization> authorizationList = authorizationService.selectByUserId(userId);
      return this.getSuccessJsonResp(authorizationList);
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return this.getErrorJsonResp(e);
    }

  }
}
