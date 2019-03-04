package com.kaistart.gateway.common.tool;

import java.util.Random;
/**
 * 网关工具类
 * @author chenhailong
 * @date 2019年2月14日 下午1:50:59
 */
public class GatewayUtil {

    public static final String TABLES_GATEWAY_API = "gateway_api";
    
    public static final String TABLES_GATEWAY_API_GROUOP = "gateway_api_group";
    
    public static final String TABLES_GATEWAY_API_RESULT = "gateway_api_result";
    
    public static final String TABLES_GATEWAY_APP = "gateway_app";
    
    public static final String TABLES_GATEWAY_APP_AUTH = "gateway_app_auth";
    
    public static final String TABLES_GATEWAY_PARTNER = "gateway_partner";
    
    public static final String TABLES_GATEWAY_SERVICE_REQUEST = "gateway_service_request";
  
  
  
	/** 
     * 获取16进制随机数 
     * @param len 
     * @return 
     * @throws CoderException 
     */  
    public static String randomHexString(int len)  {  
        try {  
            StringBuffer result = new StringBuffer();  
            for(int i=0;i<len;i++) {  
                result.append(Integer.toHexString(new Random().nextInt(16)));  
            }  
            return result.toString().toUpperCase();  
              
        } catch (Exception e) {  
            e.printStackTrace();  
              
        }  
        return null;  
          
    }
    
}
