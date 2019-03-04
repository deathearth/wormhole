package com.kaistart.gateway.mgr.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayApiGroupDO;

/**
 * 网关api分组mapper
 * @author chenhailong
 * @date 2019年2月14日 下午2:29:32
 */
@Repository
public interface GatewayApiGroupMapper extends CommonMapper<GatewayApiGroupDO>{
  /**
   * 查询GROUP分页信息
   * @param map
   * @return
   */
  List<GatewayApiGroupDO> selectGroupPage(Map<String, Object> map);
}