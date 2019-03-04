package com.kaistart.gateway.common;

/**
 * 网关参数常量定义
 * @author chenhailong
 * @date 2019年1月31日 下午2:32:38
 */
public class GatewayConstants {
	  
	  /**
	   * 用户token前缀  + uid
	   */
	  public static final String CACHEKEY_USER_TOKEN = "usertoken_"; 
	  
	  
	  /**
	   * 请求固定参数
	   */
	  /**
	   * 客户端 取值范围ios/android/web/wap
	   */
	  public static final String HTTP_PARAM_CLIENT = "client";
	  /**
	   * app端版本
	   */
	  public static final String HTTP_PARAM_CLIENT_VERSION = "clientversion";
	  /**
	   * 服务接口名称
	   */
	  public static final String HTTP_PARAM_API_NAME = "apiName";
	  
	  public static final String HTTP_HEADER_APP_KEY = "appKey";
	  /**
	   * 时间戳，系统毫秒数
	   */
	  public static final String HTTP_HEADER_TIMESTAMP = "t";	  
	  /**
	   * 接口授权签名
	   */
	  public static final String HTTP_HEADER_SIGN = "sign";
	  /**
       * 接口登录授权签名
       */
      public static final String HTTP_HEADER_AUTH = "auth";
	  /**
	   * 登录用户id
	   */
	  public static final String HTTP_HEADER_USER_ID = "userId";
	  /**
	   * 服务端生成的设备id
	   */
	  public static final String HTTP_HEADER_DEVICE_ID = "deviceId";
	  /**
	   * 请求body长度
	   */
	  public static final String CONTENT_LENGTH = "contentLength";
	  
	  public static final String APP_SECRET = "appSecret";
	  
	  public static final String TOKEN = "token";
	  
	  /**
	   * 本地环境
	   */
	  public static final String ENV_LOCAL = "local";
	  /**
	   * 开发环境
	   */
	  public static final String ENV_DEV = "dev";
	  /**
	   * 测试环境
	   */
	  public static final String ENV_TEST = "daily";
	  /**
	   * 线上环境
	   */
	  public static final String ENV_ONLINE = "online";
	  /**
	   * http get请求
	   */
	  public static final String HTTP_GET = "get";
	  /**
	   * http post请求
	   */
	  public static final String HTTP_POST = "post";


}
