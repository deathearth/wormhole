/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.processor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.common.exception.GatewayException;
import com.kaistart.gateway.config.RunConfig;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayServiceRequestDO;
import com.kaistart.gateway.domain.response.ResultCode;

/**
 * 负责路由到dubbo服务
 * 
 * @author wuyuan.lfk
 * @date 2019年1月3日 下午5:46:03
 */
@Component("dubboProcessor")
public class DubboProcessor implements Processor {
  private static final Logger logger = LoggerFactory.getLogger(DubboProcessor.class);

  @Autowired
  private RunConfig runConfig;

  @Autowired
  private GatewayMgrService gatewayMgrService;

  private ApplicationConfig application = new ApplicationConfig();

  private RegistryConfig registryConfig = new RegistryConfig();

  @Override
  public Object run(HttpServletRequest request, Context context) throws Exception {

    // 1获取API信息
    GatewayApiDO apiInfo = gatewayMgrService.getApi(context.getApiName());

    // 2.获取参数类型
    String[] types = getParamTypes(apiInfo);

    // 3.获取参数值
    Object[] values = getParamValues(request, apiInfo);

    // 4.返回结果
    return routeService(context, apiInfo, types, values);
  }

  @Override
  public String getProcessorName() {
    return "dubbo";
  }

  /**
   * 获取参数类型
   * 
   * @param apiInfo
   * @return
   */
  private String[] getParamTypes(GatewayApiDO apiInfo) {
    List<GatewayServiceRequestDO> listParams = apiInfo.getParamList();
    int size = listParams == null ? 0 : listParams.size();
    String[] types = new String[size];
    if (apiInfo.getSpecial() == GatewayApiDO.SPECIAL_TYPE_0) {
      for (GatewayServiceRequestDO request : listParams) {
        types[request.getIndex()] = request.getTypeName();
      }
    } else if (apiInfo.getSpecial() == GatewayApiDO.SPECIAL_TYPE_1) {
      types[0] = "java.lang.String";
    } else if (apiInfo.getSpecial() == GatewayApiDO.SPECIAL_TYPE_2) {
      types[0] = "java.util.Map";
    } else if (apiInfo.getSpecial() == GatewayApiDO.SPECIAL_TYPE_3) { 
      types[0] = "java.util.Map";
    }
    return types;
  }

  /**
   * special 1,2类型的保留，一是兼容历史数据,二是应对后期扩展或者其他数据 2类型说明 key-value接口
   * 例如：request中的参数t1=1&t1=2&t2=3形成的map结构： key=t1;value[0]=1,value[1]=2 key=t2;value[0]=3 3类型说明
   * 回调接口， url参数和body参数统一包装在map中
   * 
   * @param request
   * @param apiName
   * @return
   */
  private Object[] getParamValues(HttpServletRequest request, GatewayApiDO apiInfo) {

    List<GatewayServiceRequestDO> listParams = apiInfo.getParamList();
    int size = listParams == null ? 0 : listParams.size();
    Object[] params = new Object[size];
    // body内容
    String body = null;
    // 如果是post请求,或者 接口类型为回调接口需要获取body内的值
    boolean check = "POST".equalsIgnoreCase(request.getMethod())
        || (apiInfo.getSpecial() == GatewayApiDO.SPECIAL_TYPE_3 && request.getContentLength() > 0);
    if (check) {
      body = getBody(request, apiInfo.getName());
    }
    // 特殊api接口特殊处理，例如
    Integer special = apiInfo.getSpecial();
    if (special == GatewayApiDO.SPECIAL_TYPE_0) {
      String value = null;
      for (GatewayServiceRequestDO param : listParams) {
        switch (param.getType()) {
          case GatewayServiceRequestDO.STRING: {
            value = request.getParameter(param.getName());
            if (param.getIsRequired() == 1 && StringUtils.isEmpty(value)) {
              throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
            }
            params[param.getIndex()] = value;
            break;
          }
          case GatewayServiceRequestDO.INT: {
            value = request.getParameter(param.getName());
            if (param.getIsRequired() == 1 && value == null) {
              throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
            }
            if (value == null) {
              params[param.getIndex()] = null;
            } else {
              try {
                params[param.getIndex()] = Integer.parseInt(value);
              } catch (Exception e) {
                logger.error("param-error-int apiName=" + apiInfo.getName() + " " + param.getName()
                    + "=" + value);
                throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
              }
            }
            break;
          }
          case GatewayServiceRequestDO.LONG: {
            value = request.getParameter(param.getName());
            if (param.getIsRequired() == 1 && value == null) {
              throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
            }
            if (value == null) {
              params[param.getIndex()] = null;
            } else {
              try {
                params[param.getIndex()] = Long.parseLong(value);
              } catch (Exception e) {
                logger.error("param-error-long apiName=" + apiInfo.getName() + " " + param.getName()
                    + "=" + value);
                throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
              }
            }
            break;
          }
          case GatewayServiceRequestDO.FLOAT: {
            value = request.getParameter(param.getName());
            if (param.getIsRequired() == 1 && value == null) {
              throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
            }
            if (value == null) {
              params[param.getIndex()] = null;
            } else {
              try {
                params[param.getIndex()] = Float.parseFloat(value);
              } catch (Exception e) {
                logger.error("param-error-float apiName=" + apiInfo.getName() + " "
                    + param.getName() + "=" + value);
                throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
              }
            }
            break;
          }
          case GatewayServiceRequestDO.DOUBLE: {
            value = request.getParameter(param.getName());
            if (param.getIsRequired() == 1 && value == null) {
              throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
            }
            if (value == null) {
              params[param.getIndex()] = null;
            } else {
              try {
                params[param.getIndex()] = Double.parseDouble(value);
              } catch (Exception e) {
                logger.error("param-error-dubble apiName=" + apiInfo.getName() + " "
                    + param.getName() + "=" + value);
                throw new GatewayException(ResultCode.PARAM_ERROR, param.getName());
              }
            }
            break;
          }
          case GatewayServiceRequestDO.OTHERS: {
            try {
              params[param.getIndex()] = JSONObject.parseObject(body, Map.class);
            } catch (Exception e) {
              logger.error("param-error apiName=" + apiInfo.getName() + " body=" + body, e);
              throw new GatewayException(ResultCode.PARAM_ERROR, "body");
            }
            break;
          }
          default:{
            throw new GatewayException(ResultCode.PARAM_ERROR, "param-type-error="+param.getType());
          }
        }
      }
    } else if (special == GatewayApiDO.SPECIAL_TYPE_1) {
      params[0] = body;
    } else if (special == GatewayApiDO.SPECIAL_TYPE_2) {
      params[0] = request.getParameterMap();
    } else if (special == GatewayApiDO.SPECIAL_TYPE_3) {
      Map<String, String> map = new HashMap<String, String>(16);
      Enumeration<String> enums = request.getParameterNames();
      while (enums.hasMoreElements()) {
        String name = (String) enums.nextElement();
        String value = request.getParameter(name);
        map.put(name, value);
      }
      map.put("body", body);
      params[0] = map;
    }
    return params;
  }

  /**
   * 获取请求body中的信息
   * 
   * @param request
   * @param apiName
   * @return
   */
  private String getBody(HttpServletRequest request, String apiName) {
    InputStream in = null;
    ByteArrayOutputStream outSteam = null;
    try {
      in = request.getInputStream();
      outSteam = new ByteArrayOutputStream();
      if (in == null) {
        logger.error("apiName=" + apiName + " body-is-null");
        throw new GatewayException(ResultCode.PARAM_ERROR);
      }
      byte[] buffer = new byte[4096];
      int len = 0;
      while ((len = in.read(buffer)) != -1) {
        outSteam.write(buffer, 0, len);
      }
      return new String(outSteam.toByteArray(), "utf-8");
    } catch (Exception e) {
      logger.error("apiName={},body-contentLength={}", apiName, request.getContentLength()+" msg="+e.getMessage());
      throw new GatewayException(ResultCode.PARAM_ERROR);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
        }
      }
      if (outSteam != null) {
        try {
          outSteam.close();
        } catch (IOException e) {
        }
      }
    }
  }


  /**
   * 路由具体的dubbo接口 2.ReferenceConfig封装了与注册中心的连接以及与提供者的连接，需要缓存，
   * 否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。 API方式编程时，容易忽略此问题。 这里使用dubbo内置的简单缓存工具类进行缓存
   * 
   * @param call
   * @return
   */
  private Object routeService(Context context, GatewayApiDO apiInfo, String types[],
      Object values[]) {

    ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
    Object result = null;
    GenericService genericService = null;
    try {
      // 1.初始化对象
      application.setName(runConfig.getApplicationName());
      registryConfig.setAddress(runConfig.getZookeeperAddress());
      registryConfig.setProtocol("zookeeper");

      reference.setApplication(this.application);
      reference.setRegistry(this.registryConfig);
      // 声明为泛化接口
      reference.setGeneric(true); 
      reference.setInterface(apiInfo.getServiceName());
      reference.setVersion(apiInfo.getServiceVersion());
      reference.setTimeout(apiInfo.getTimeOut());
      reference.setRetries(0);

      // 2.缓存处理
      genericService = ReferenceConfigCache.getCache().get(reference);
      if (genericService == null) {
        ReferenceConfigCache.getCache().destroy(reference);
        logger.error("dubbo server not founded ! apiInfo is {}", JSON.toJSONString(apiInfo));
        throw new GatewayException(ResultCode.SYSTEM_NOT_FOUNDED);
      }

      // 3.设置上下文对象
      setGWContext(context);

      // 4.调用服务
      result = genericService.$invoke(apiInfo.getServiceMethod(), types, values);
    } catch (Throwable e) {
      logger.error("eid={} context={} type={} param={}", e.hashCode(), JSON.toJSONString(context), JSON.toJSONString(types), JSON.toJSONString(values));
      throw e;
    }
    return result;
  }


  /**
   * 设置上下文信息
   * 
   * @param context
   */
  private void setGWContext(Context context) {
    RpcContext.getContext().setAttachment("appKey", context.getAppKey());
    RpcContext.getContext().setAttachment("ip", context.getIp());
    RpcContext.getContext().setAttachment("userId", context.getUserId());
    RpcContext.getContext().setAttachment("client", context.getClient());
    RpcContext.getContext().setAttachment("clientVersion", context.getClientVersion());
    if (context.getDeviceId() != null) {
      RpcContext.getContext().setAttachment("deviceId", context.getDeviceId() + "");
    }
    RpcContext.getContext().setAttachment("apiName", context.getApiName());
  }
}
