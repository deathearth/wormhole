/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.common.tool;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.kaistart.gateway.api.service.GatewayMgrService;

/**
 *
 * 
 * @author wuyuan.lfk
 * @date 2018年12月25日 下午5:05:08 
 */
public class ZkService implements InitializingBean{
  private static final Logger logger = LoggerFactory.getLogger(ZkService.class);
  public static final String ZK_PATH_GATEWAY = "/gateway";
  public static final String ZK_PATH_APP = "/gateway/app";
  public static final String ZK_PATH_API = "/gateway/api";
  public static final String ZK_PATH_APP_AUTH = "/gateway/appAuth";
  
  private String zkUrl;
  private ZkClient zk;
  private GatewayMgrService gatewayMgrService;
  
  private IZkDataListener initDataListener() {
    IZkDataListener listener = new IZkDataListener() {
      @Override
      public void handleDataChange(String dataPath, Object data) throws Exception {
        String value = data.toString();
        logger.info(dataPath + "=" + value);
        if (StringUtils.isEmpty(value)) {
          return;
        }
        value = value.split("#")[0];
        if (StringUtils.isNumeric(value)) {
          if (ZK_PATH_API.equals(dataPath)) {
            gatewayMgrService.reloadApiCache(Long.valueOf(value));
          } else if (ZK_PATH_APP_AUTH.equals(dataPath)) {
            gatewayMgrService.reloadAppAuthCache(Long.valueOf(value));
          } else if (ZK_PATH_APP.equals(dataPath)) {
            gatewayMgrService.reloadAppCache(Long.valueOf(value));
          }
        }
      }

      @Override
      public void handleDataDeleted(String dataPath) throws Exception {

      }

    };
    return listener;

  }

  
  private void init() throws Exception{
    
    zk = new ZkClient(zkUrl,10000,10000,new SerializableSerializer());  
    
    if(!zk.exists(ZK_PATH_GATEWAY)){
        zk.createPersistent(ZK_PATH_GATEWAY);
    }
    
    if(!zk.exists(ZK_PATH_APP)){
      zk.createPersistent(ZK_PATH_APP);
    }
    
    if(!zk.exists(ZK_PATH_API)){
      zk.createPersistent(ZK_PATH_API);
    }
    
    if(!zk.exists(ZK_PATH_APP_AUTH)){
      zk.createPersistent(ZK_PATH_APP_AUTH);
    }
    
    IZkDataListener listener = initDataListener();
    zk.subscribeDataChanges(ZK_PATH_APP, listener);
    zk.subscribeDataChanges(ZK_PATH_API, listener);
    zk.subscribeDataChanges(ZK_PATH_APP_AUTH, listener);
    
  }
  
  public void updateApp(Long id) {
    String value = id+"#"+System.currentTimeMillis();
    try {
      zk.writeData(ZK_PATH_APP, value);
    } catch (Exception e) {
      logger.error("updateApp="+value, e);
    }
  }
  
  public void updateApi(Long id) {
    String value = id+"#"+System.currentTimeMillis();
    try {
      zk.writeData(ZK_PATH_API, value);
    } catch (Exception e) {
      logger.error("updateApi="+value, e);
    }
  }
  
  public void updateAppAuth(Long id) {
    String value = id+"#"+System.currentTimeMillis();
    try {
      zk.writeData(ZK_PATH_APP_AUTH, value);
    } catch (Exception e) {
      logger.error("updateAppAuth="+value, e);
    }
  }

  public String getZkUrl() {
    return zkUrl;
  }


  public void setZkUrl(String zkUrl) {
    this.zkUrl = zkUrl;
  }


  public GatewayMgrService getGatewayMgrService() {
    return gatewayMgrService;
  }

  public void setGatewayMgrService(GatewayMgrService gatewayMgrService) {
    this.gatewayMgrService = gatewayMgrService;
  }
  

  @Override
  public void afterPropertiesSet() throws Exception {
    init();
  }

}
