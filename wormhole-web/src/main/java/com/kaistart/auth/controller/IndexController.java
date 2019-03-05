package com.kaistart.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author administrator
 * @date 2019年2月21日 下午7:51:24
 */
@Controller
public class IndexController {

    @RequestMapping( "/home" )
    public String home() {

      return "pages/index/index";
    }
    
    
    /**
     * 默认首页指定
     * @param model
     * @param response
     * @return
     */
    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        return "/login";
    }

}
