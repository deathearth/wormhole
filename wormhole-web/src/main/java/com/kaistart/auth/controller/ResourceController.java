package com.kaistart.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaistart.auth.domain.Resource;
import com.kaistart.auth.service.ResourceService;
import com.kaistart.gateway.support.json.JsonResp;
import com.kaistart.gateway.support.proto.ProtoController;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * Created by Administrator on 2016/12/20.
 * @author
 */
@Controller
@RequestMapping("/auth/resource")
public class ResourceController extends ProtoController<Resource> {

  private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

  @javax.annotation.Resource
  private ResourceService resourceService;

  @SuppressWarnings("rawtypes")
  @Override
  protected ProtoService getService() {
    return resourceService;
  }

  @RequestMapping("/delete/node/{id}")
  @ResponseBody
  public JsonResp deleteNode(@PathVariable Integer id) {
    try {
      resourceService.deleteNode(id);
      return getSuccessJsonResp();
    } catch (Exception e) {
      logger.error(e.getLocalizedMessage(), e);
      return getErrorJsonResp(e);
    }
  }


  @RequestMapping("/edit")
  public String edit(Integer id, Model model) throws Exception {
    model.addAttribute("resource", resourceService.selectById(id));
    return "pages/auth/resource/edit";
  }

}
