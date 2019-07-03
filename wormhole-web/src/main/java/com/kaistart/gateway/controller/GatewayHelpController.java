package com.kaistart.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网关帮助层
 * @author chenhailong
 */
@Controller
@RequestMapping( "/gateway/help" )
public class GatewayHelpController {

	@RequestMapping("/base")
	  public String edit(Long id, Model model) throws Exception {
	    return "pages/gateway/help/base";
	  }
}
