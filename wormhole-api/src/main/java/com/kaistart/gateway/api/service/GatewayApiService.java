package com.kaistart.gateway.api.service;
import java.util.List;
import java.util.Map;

import com.kaistart.gateway.domain.GatewayApiDO;
import com.kaistart.gateway.domain.GatewayApiRequestDO;

/**
 * 
 * API接口表 接口
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
public interface GatewayApiService extends CommonService<GatewayApiDO> { 

  /**
   * 获取搜索总数
   * @param map
   * @return
   */
  Integer selectSearchCount(Map<String,Object> map);
  
  /**
   * 获取搜索集合信息
   * @param map
   * @return
   */
  List<GatewayApiDO> selectSearchPage(Map<String, Object> map);
  
  /**
   * 同步数据
   * @param bean
   * @return
   */
  boolean transferData(GatewayApiRequestDO bean);
  
  /**
   * 拷贝数据
   * @param bean
   * @return
   */
  boolean copyData(GatewayApiRequestDO bean);
}