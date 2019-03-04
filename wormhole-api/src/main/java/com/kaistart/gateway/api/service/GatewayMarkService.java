package com.kaistart.gateway.api.service;
import java.util.List;
import java.util.Map;

import com.kaistart.gateway.domain.GatewayMarkDO;

/**
 * 
 * 网关接口标签表 接口
 * 
 * @version 
 * @author chenhailong
 * <pre>
 * Author	Version		Date		Changes
 * chenhailong 	1.0  2019年01月14日 Created
 *
 * </pre>
 * @since 1.
 */
public interface GatewayMarkService extends CommonService<GatewayMarkDO> { 

  /**
   * 查询分页方法
   * @param map 分页参数
   * @return
   */
  List<Map<String,Object>> selectCheckPage( Map< String, Object > map ) ;
  
  /**
   * 查询总数方法
   * @param map 查询参数
   * @return
   */
  int selectCheckCount( Map< String, Object > map ) ;
}