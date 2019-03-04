package com.kaistart.gateway.api.service;
import java.util.List;
import java.util.Map;

import com.kaistart.gateway.domain.GatewayAppAuthDO;
import com.kaistart.gateway.dto.RequestAuthorize;

/**
 * 
 * APP鉴权表 接口
 * 
 * @version 
 * @author chenhailong
 * <pre>
 * Author	Version		Date		Changes
 * chenhailong 	1.0  2018年06月22日 Created
 *
 * </pre>
 * @since 1.
 */
public interface GatewayAppAuthService extends CommonService<GatewayAppAuthDO> { 
  
  /**
   * 删除方法
   * @param id 主键ID
   * @return
   * @throws Exception
   */
  void delete( Long id ) throws Exception;
  
  /**
   * 查询分页方法
   * @param map 分页参数
   * @return
   * @throws Exception
   */
  List<RequestAuthorize> selectPageAuth(Map< String, Object > map) throws Exception;
  
  /**
   * 查询分页方法
   * @param map 分页参数
   * @return
   * @throws Exception
   */
  List<RequestAuthorize> selectListAuth(Map< String, Object > map) throws Exception;
  
  /**
   * 查询总数方法
   * @param map 查询参数
   * @return
   * @throws Exception
   */
  Integer selectCountAuth(Map<String, Object> map) throws Exception;
  
  /**
   * 权限授权方法
   * @param gatewayAppAuth 授权信息
   * @throws Exception
   */
  void grant(GatewayAppAuthDO gatewayAppAuth)throws Exception;
  
  /**
   * 解除授权方法
   * @param gatewayAppAuth 解除授权信息
   * @throws Exception
   */
  void ungrant(GatewayAppAuthDO gatewayAppAuth)throws Exception;
}