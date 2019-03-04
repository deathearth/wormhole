package com.kaistart.auth.controller;

import javax.annotation.Resource;

import org.apache.shiro.authc.credential.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.User;
import com.kaistart.auth.service.UserService;
import com.kaistart.gateway.support.json.JsonResp;
import com.kaistart.gateway.support.proto.ProtoController;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * @author administartor
 * @date 2019年2月21日 下午7:52:06
 */
@Controller
@RequestMapping("/auth/user")
public class UserController extends ProtoController<User> {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Resource
  private UserService userService;

  @Resource
  private PasswordService passwordService;

  @SuppressWarnings("rawtypes")
  @Override
  protected ProtoService getService() {
    return userService;
  }

  @Override
  @RequestMapping("/post")
  @ResponseBody
  public JsonResp post(@RequestBody User user) {

    try {

      User persistUser = userService.selectByName(user.getName());
      if (persistUser != null && persistUser.getName() != null) {
        throw new Exception("user exists.");
      }

      user.setPassword(passwordService.encryptPassword(user.getPassword()));
      userService.insert(user);

      return getSuccessJsonResp();

    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return getErrorJsonResp(e);
    }

  }
  
  @Override
  @ResponseBody
  @RequestMapping("/proto/put")
  public JsonResp put(@RequestBody User user) throws Exception {
    user.setPassword(passwordService.encryptPassword(user.getPassword()));
    userService.update(user);
    return getSuccessJsonResp();
  }
  
  @RequestMapping("/edit")
  public String edit(Integer id, Model model) throws Exception {
    User user = userService.selectById(id);
    user.setPassword("");
    model.addAttribute("user", user);
    return "pages/auth/user/edit";
  }

}
