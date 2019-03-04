package com.kaistart.gateway.mgr.mapper;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayMarkDO;

/**
 * 网关标签 mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:33:23
 */
@Repository
public interface GatewayMarkMapper extends CommonMapper<GatewayMarkDO> { 
	
  /**
   * 根据条件查询标签分页信息
   * @param map 查询条件
   * @return
   */
  List<Map<String,Object>> selectCheckPage( Map< String, Object > map );
  
  /**
   * 根据条件查询标签分页数量
   * @param map
   * @return
   */
  int selectCheckCount( Map< String, Object > map );
}