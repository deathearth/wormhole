package com.kaistart.gateway.common.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * HTTPClient 工具类
 * @author chenhailong 2018-09-15
 * 
 */
public class HttpClientUtils {

  private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
  
  private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private static final String HEADER_NAME = "header_value1";
  private static final String NOTIFY_CALLBACK = "callback";

  /**
   * 网关测试功能的get请求方法
   * @param url
   * @param map
   * @return
   */
  @SuppressWarnings({"rawtypes"})
  public static ObjectNode get(String url,Map<String,Object> map) {
    ObjectNode resObjectNode = null;
    CloseableHttpClient httpCilent = HttpClients.createDefault();
    
    Object body = map.get("body");
    String urlparam = "";
    //application/x-www-form-urlencoded这种类型，需要把参数拼接到 url中
    if(map.get(HEADER_NAME).toString().equals(CONTENT_TYPE)) { 
      String[] urlencoded = body.toString().split("\r\n");
      for(int i = 0;i<urlencoded.length;i++) {
        String[] inner = urlencoded[i].split(":");
        urlparam += "&"+inner[0] +"=" + inner[1];  
      }
    }
    HttpGetWithEntity httpGet = new HttpGetWithEntity(url + urlparam);
    //处理头部参数
    String kfix = "header_key",vfix = "header_value";
    ArrayList al = (ArrayList)map.get("enables");
    
    for(int i = 0;i < al.size();i++) {
      Object key = map.get(kfix+al.get(i));
      if(key == null) { continue; }
      httpGet.addHeader(key.toString(), map.get(kfix+al.get(i))==null?"":map.get(vfix+al.get(i)).toString());
    }
    
    if(body != null  && !map.get(HEADER_NAME).toString().equals(CONTENT_TYPE)) {
      // 构建消息实体
      StringEntity entity = new StringEntity(body.toString(), Charset.forName("UTF-8"));
      entity.setContentEncoding("UTF-8");
      // 发送Json格式的数据请求
      entity.setContentType(httpGet.getHeaders("content-type")[0]);  
      httpGet.setEntity(entity);
    }
    
    String responseContent = "";
    HttpResponse httpResponse = null;
    try {
      httpResponse = httpCilent.execute(httpGet);
      HttpEntity entity = httpResponse.getEntity();
      if (null != entity) {
        responseContent = EntityUtils.toString(entity, "UTF-8");
        EntityUtils.consume(entity);
        ObjectMapper mapper = new ObjectMapper();
        resObjectNode = (ObjectNode)mapper.readTree(responseContent);
        resObjectNode.put("statusCode", httpResponse.getStatusLine().getStatusCode());
      }
    }catch(ClassCastException e) {
      logger.info("GET请求时发生异常{}{}",e.getMessage());
      ObjectNode obj = new ObjectMapper().createObjectNode();
      obj.put("warning", "该接口定义的返回类型不是标准的json格式,请尽量按照规范来做接口!");
      obj.put("statusCode", "200");
      obj.put("data", responseContent);
      return obj;
    }catch (Exception e) {
      logger.info("GET请求时发生异常 url={} msg={}",url,e.getMessage(), e);
      ObjectNode obj = new ObjectMapper().createObjectNode();
      obj.put("error", "GET请求时发生异常{}:"+e.getMessage());
      return obj;
    } finally {
      // 释放资源
      try {
        httpCilent.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return resObjectNode;
  }

  /**
   * 网关测试功能的post请求方法
   * @param url
   * @param map
   * @return
   */
  @SuppressWarnings({"rawtypes"})
  public static ObjectNode post(String url,Map<String,Object> map) {
    ObjectNode resObjectNode = null;
    CloseableHttpClient httpCilent = HttpClients.createDefault();
    
    Object body = map.get("body");
    String urlparam = "";
    //application/x-www-form-urlencoded这种类型，需要把参数拼接到 url中
    if(map.get(HEADER_NAME).toString().equals(CONTENT_TYPE)) { 
      String[] urlencoded = body.toString().split("\r\n");
      for(int i = 0;i<urlencoded.length;i++) {
        String[] inner = urlencoded[i].split(":");
        urlparam += "&"+inner[0] +"=" + inner[1];  
      }
    }
    
    HttpPost post = new HttpPost(url + urlparam);
    
    //处理头部参数
    String kfix = "header_key",vfix = "header_value";
    ArrayList al = (ArrayList)map.get("enables");
    
    for(int i = 0;i < al.size();i++) {
      Object key = map.get(kfix+al.get(i));
      if(key == null) { continue; }
      post.addHeader(key.toString(), map.get(kfix+al.get(i))==null?"":map.get(vfix+al.get(i)).toString());
    }
    
    if(body != null  && !map.get(HEADER_NAME).toString().equals(CONTENT_TYPE)) {
      // 构建消息实体
      StringEntity entity = new StringEntity(body.toString(), Charset.forName("UTF-8"));
      entity.setContentEncoding("UTF-8");
      // 发送Json格式的数据请求
      entity.setContentType(post.getHeaders("content-type")[0]);  
      post.setEntity(entity);
    }
    String responseContent = null;
    try {
      //设置post求情参数
      HttpResponse httpResponse = httpCilent.execute(post);
      HttpEntity entity = httpResponse.getEntity();
      if (null != entity) {
        
        responseContent = EntityUtils.toString(entity, "UTF-8");
        EntityUtils.consume(entity);
        ObjectMapper mapper = new ObjectMapper();
        resObjectNode = (ObjectNode)mapper.readTree(responseContent);
        if(resObjectNode != null) {
          resObjectNode.put("statusCode", httpResponse.getStatusLine()==null?null: httpResponse.getStatusLine().getStatusCode());
        }
      }
    } catch(ClassCastException e) {
      ObjectNode obj = new ObjectMapper().createObjectNode();
      if(url.toLowerCase().indexOf(NOTIFY_CALLBACK)<0) {
        obj.put("warning", "该接口定义的返回类型不是标准的json格式,请尽量按照规范来做接口!");
        obj.put("statusCode", "200");
      }
      obj.put("data", responseContent);
      return obj;
    }catch(JsonParseException e) {
      ObjectNode obj = new ObjectMapper().createObjectNode();
      if(url.toLowerCase().indexOf(NOTIFY_CALLBACK)<0) {
        obj.put("warning", "该接口定义的返回类型不是标准的json格式,请尽量按照规范来做接口!");
        obj.put("statusCode", "200");
      }
      obj.put("data", responseContent);
      return obj;
    } catch (Exception e) {
      logger.info("POST请求发生异常{}",e.getMessage());
      ObjectNode obj = new ObjectMapper().createObjectNode();
      obj.put("error", "POST请求时发生异常:"+e.getMessage());
      return obj;
    } finally {
      try {
        // 释放资源
        httpCilent.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return resObjectNode;
  }
  
  
  
  /**
   * 测试接口转线上接口
   * @param url
   * @param body
   * @return
   */
  public static ObjectNode post(String url , String body) {
    ObjectNode resObjectNode = null;
    CloseableHttpClient httpCilent = HttpClients.createDefault();
    HttpPost post = new HttpPost(url);
    try {
     
      // 构建消息实体
      StringEntity beforeentity = new StringEntity(body, Charset.forName("UTF-8"));
      beforeentity.setContentEncoding("UTF-8");
      // 发送Json格式的数据请求
      beforeentity.setContentType("application/json");  
      post.setEntity(beforeentity);
      
      //设置post求情参数
      HttpResponse httpResponse = httpCilent.execute(post);
      HttpEntity entity = httpResponse.getEntity();
      if (null != entity) {
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        EntityUtils.consume(entity);
        ObjectMapper mapper = new ObjectMapper();
        resObjectNode = (ObjectNode)mapper.readTree(responseContent);
        resObjectNode.put("statusCode", httpResponse.getStatusLine().getStatusCode());
      }
    } catch (IOException e) {
      logger.info("POST请求时发生异常{}",e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        httpCilent.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return resObjectNode;
  }
}
