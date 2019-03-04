package com.kaistart.gateway.api.service;

import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayAppDO;

/**
 * 
 * 管理台相关方法
 * @author chenhailong
 * @date 2019年2月14日 上午10:38:15
 */
public interface GatewayMgrService  {
	
	/**
	 * 检查授权
	 * @param app
	 * @param api
	 * @return
	 */
	boolean checkAuth(GatewayAppDO app, GatewayApiDO api);
	
	/**
	 * 只读localcache
	 * @param appKey
	 * @return
	 */
	GatewayAppDO getApp(String appKey);
	
	/**
	 * 只读localcache
	 * @param apiName
	 * @return
	 */
	GatewayApiDO getApi(String apiName);
	
	/**
	 * zk全局更新localcache
	 * @param id
	 */
	void reloadAppCache(Long id);
	/**
     * zk全局更新localcache
     * @param id
     */
	void reloadApiCache(Long id);
	/**
     * zk全局更新localcache
     * @param id
     */
	void reloadAppAuthCache(Long id);
	
}
