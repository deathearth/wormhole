package com.kaistart.gateway.mgr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaistart.gateway.api.service.GatewayMgrService;
import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayAppAuthDO;
import com.kaistart.gateway.domain.GatewayAppDO;
import com.kaistart.gateway.domain.GatewayServiceRequestDO;
import com.kaistart.gateway.mgr.mapper.GatewayApiMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppAuthMapper;
import com.kaistart.gateway.mgr.mapper.GatewayAppMapper;
import com.kaistart.gateway.mgr.mapper.GatewayServiceRequestMapper;

/**
 * 网关数据，缓存类处理
 * @author lfk
 * @date 2019年2月14日 下午3:27:35
 */
@Service("gatewayMgrService")
public class GatewayMgrServiceImpl implements GatewayMgrService, InitializingBean {
  
  private static final Logger logger = LoggerFactory.getLogger(GatewayMgrServiceImpl.class);
  public static final String KEY_PREFIX_APP = "gateway#app#";
  public static final String KEY_PREFIX_API = "gateway#api#";
  public static final String KEY_PREFIX_APP_AUTH = "gateway#appauth#";

  @Resource
  private GatewayApiMapper gatewayApiMapper;
  @Resource
  private GatewayAppAuthMapper gatewayAppAuthMapper;
  @Resource
  private GatewayAppMapper gatewayAppMapper;
  @Resource
  private GatewayServiceRequestMapper gatewayServiceRequestMapper;

  /**
   * appKey 信息 appInfo 信息 appAuth 信息
   */
  private static Cache<String, Object> cache = CacheBuilder.newBuilder()
      // 设置cache的初始大小为100，要合理设置该值
      .initialCapacity(100)
      // 最大缓存数量1万
      .maximumSize(10000)
      // 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
      .concurrencyLevel(5)
      // 设置cache中的数据在写入之后的存活时间为60分钟
      //.expireAfterWrite(3600, TimeUnit.SECONDS)
      // 构建cache实例
      .build();
  
  @Override
  public boolean checkAuth(GatewayAppDO app, GatewayApiDO api) {
    if(app == null || api == null) {
      return false;
    }
    //先检查分组，再检查api
    GatewayAppAuthDO auth =(GatewayAppAuthDO)cache.getIfPresent(app.getId()+"#"+api.getGroupId()+"#"+GatewayAppAuthDO.AUTH_TYPE_GROUP);
    if(auth == null) {
      auth =(GatewayAppAuthDO)cache.getIfPresent(app.getId()+"#"+api.getId()+"#"+GatewayAppAuthDO.AUTH_TYPE_API);
    }
    
    if(auth != null) {
      return true;
    }else {
      return false;
    }
  }

  @Override
  public GatewayAppDO getApp(String appKey) {
    return (GatewayAppDO)cache.getIfPresent(appKey);
  }

  @Override
  public GatewayApiDO getApi(String apiName) {
    return (GatewayApiDO)cache.getIfPresent(apiName);
  }

  @Override
  public void reloadAppCache(Long id) {
    GatewayAppDO app = gatewayAppMapper.selectByPrimaryKey(id);
    String key = KEY_PREFIX_APP+id;
    if(app == null || app.getStatus() == null || app.getStatus().intValue() != 1) {
      GatewayAppDO target = (GatewayAppDO)cache.getIfPresent(key);
      if(target != null) {
        cache.invalidate(key);
        cache.invalidate(target.getAppKey());
        logger.info("reloadAppCache invalidate key="+target.getAppKey());
      }
    }else {
      cache.put(key, app);
      cache.put(app.getAppKey(), app);
      logger.info("reloadAppCache key="+app.getAppKey());
    }
  }

  @Override
  public void reloadApiCache(Long id) {
    GatewayApiDO api = gatewayApiMapper.selectByPrimaryKey(id);
    String key = KEY_PREFIX_API+id;
    if(api == null || api.getStatus() == null || api.getStatus().intValue() != 1) {
      GatewayApiDO target = (GatewayApiDO)cache.getIfPresent(key);
      if(target != null) {
        cache.invalidate(key);
        cache.invalidate(target.getName());
        logger.info("reloadApiCache invalidate key="+target.getName());
      }
    }else {
      //查询输入参数
      Map<String, Object> map = new HashMap<String, Object>(1);
      map.put("apiId", api.getId());
      List<GatewayServiceRequestDO> paramList = gatewayServiceRequestMapper.selectParamsList(map);
      api.setParamList(paramList);
      cache.put(key, api);
      cache.put(api.getName(), api);
      logger.info("reloadApiCache key="+api.getName());
    }
  }

  @Override
  public void reloadAppAuthCache(Long id) {
    GatewayAppAuthDO auth = gatewayAppAuthMapper.selectByPrimaryKey(id);
    String key = KEY_PREFIX_APP_AUTH+id;
    if(auth == null) {
      GatewayAppAuthDO target = (GatewayAppAuthDO)cache.getIfPresent(key);
      if(target != null) {
        cache.invalidate(key);
        cache.invalidate(target.getAppId()+"#"+target.getAuthId()+"#"+target.getAuthIdType());
        logger.info("reloadAppAuthCache invalidate key="+target.getAppId()+"#"+target.getAuthId()+"#"+target.getAuthIdType());
      }
    }else {
      cache.put(key, auth);
      cache.put(auth.getAppId()+"#"+auth.getAuthId()+"#"+auth.getAuthIdType(), auth);
      logger.info("reloadAppAuthCache key="+auth.getAppId()+"#"+auth.getAuthId()+"#"+auth.getAuthIdType());
    }
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    initAppCache();
    initApiCache();
    initAppAuthCache();
    
  }
  
  
  private void initAppCache() {
    List<GatewayAppDO> list = gatewayAppMapper.selectAppPage();
    if(list != null && list.size() > 0) {
      for(GatewayAppDO app : list) {
        cache.put(app.getAppKey(), app);
        cache.put(KEY_PREFIX_APP+app.getId(), app);
        logger.info("cahce appKey="+app.getAppKey());
      }
    }
    
  }
  
  private void initApiCache() {
    Map<String, Object> map = new HashMap<String, Object>(8);
    Map<String, Object> map2 = new HashMap<String, Object>(16);
    int size = 100;
    map.put("size", size);
    int skip = 0;
    while(true) {
      map.put("skip", skip);
      List<GatewayApiDO> apiList = gatewayApiMapper.selectApiPage(map);
      if(apiList == null || apiList.isEmpty()) {
        break;
      }
      for(GatewayApiDO api : apiList) {
        map2.put("apiId", api.getId());
        List<GatewayServiceRequestDO> paramList = gatewayServiceRequestMapper.selectParamsList(map2);
        api.setParamList(paramList);
        cache.put(api.getName(), api);
        cache.put(KEY_PREFIX_API+api.getId(), api);
        logger.info("cahce apiName="+api.getName());
      }
      skip += size ;
    }
    
  }
  
  private void initAppAuthCache() {
    Map<String, Object> map = new HashMap<String, Object>(16);
    int size = 100;
    map.put("size", size);
    int skip = 0;
    while(true) {
      map.put("skip", skip);
      List<GatewayAppAuthDO> list = gatewayAppAuthMapper.selectAuthList(map);
      if(list == null || list.isEmpty()) {
        break;
      }
      for(GatewayAppAuthDO auth : list) {
        cache.put(auth.getAppId()+"#"+auth.getAuthId()+"#"+auth.getAuthIdType(), auth);
        cache.put(KEY_PREFIX_APP_AUTH+auth.getId(), auth);
        logger.info("cahce appAuth="+auth.getAppId()+"#"+auth.getAuthId()+"#"+auth.getAuthIdType());
      }
      skip += size ;
    }
  }

}
