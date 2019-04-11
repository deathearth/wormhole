package com.kaistart.gateway.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.response.ResultCode;
import com.kaistart.gateway.processor.CallbackProcessorChain;
import com.kaistart.gateway.processor.Context;

/**
 * 
 * 网关回调类型接口的servlet访问层
 * @author chenhailong
 * @date 2019年1月31日 下午2:36:14
 */
@WebServlet(urlPatterns = "/callback",description = "回调接口servlet")
public class KaistartGatewayCallbackServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(KaistartGatewayCallbackServlet.class);
  private GatewayMgrService gatewayMgrService;  
  private CallbackProcessorChain processorChain;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    //第一种方式，实现Autowired功能可用
//    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    
    //第二种方式
    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    processorChain =(CallbackProcessorChain)context.getBean("callbackProcessorChain");
    gatewayMgrService = (GatewayMgrService)context.getBean("gatewayMgrService");
    
    
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) {

    Object result = null;
    Context context = new Context();
    try { 
      result = processorChain.run(request, context);
      response(result, response, context);     
    } catch (GatewayException e) {
      error(e, response);  
    } catch (Throwable e) {
      logger.error(e.getMessage(), e);
      error(ResultCode.SYSTEM_ERROR, response);
    } 
  }
  
  private void error(ResultCode code, HttpServletResponse response) {
    response.setContentType("text/xml;charset=UTF-8");    
    response(code.getMessage(), response, null);
  }
  
  private void error(GatewayException e, HttpServletResponse response) {
    response.setContentType("text/xml;charset=UTF-8");    
    response(e.getMessage(), response, null);
  }
  
  private void response(Object result, HttpServletResponse response,Context context) {
    //微信、支付宝在回调之后需要得到一个string类型的通知,所以这里不能去解析json,直接返回
    try {
      if(context != null) {
        String apiName = context.getApiName();
        GatewayApiDO api = gatewayMgrService.getApi(apiName);
        String responseHeader = api.getResponseHeader();
        if(StringUtils.isNotEmpty(responseHeader)) {
          JSONObject obj = JSON.parseObject(responseHeader);        
          for(Map.Entry<String,Object> entry : obj.entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue().toString());
          }
        }
      }else {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result.toString());
      }
    } catch (Exception e) {
      logger.info("response header error {}",e);
      try {
        response.getWriter().write(result.toString());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    
  }

}
