package com.kaistart.auth.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.Role;
import com.kaistart.auth.service.RoleService;
import com.kaistart.gateway.support.json.JsonResp;
import com.kaistart.gateway.support.proto.ProtoController;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * Created by Administrator on 2016/12/20.
 * @author
 */
@Controller
@RequestMapping("/auth/role")
public class RoleController extends ProtoController<Role> {
  private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
  @Resource
  private RoleService roleService;

  @SuppressWarnings("rawtypes")
  @Override
  protected ProtoService getService() {
    return roleService;
  }

  @RequestMapping("/edit")
  public String edit(Integer id, Model model) throws Exception {
    model.addAttribute("role", roleService.selectById(id));
    return "pages/auth/role/edit";
  }

  @RequestMapping("/delete/node/{id}")
  @ResponseBody
  public JsonResp deleteNode(@PathVariable Integer id) {
    try {

      roleService.delete(id);
      return getSuccessJsonResp();

    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return getErrorJsonResp(e);
    }
  }

}
