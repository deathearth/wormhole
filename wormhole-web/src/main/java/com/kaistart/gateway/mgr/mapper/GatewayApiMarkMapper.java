package com.kaistart.gateway.mgr.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayApiMarkDO;
import com.kaistart.gateway.domain.GatewayMarkDO;

/**
 * 网关api标签 mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:30:44
 */
@Repository
public interface GatewayApiMarkMapper extends CommonMapper<GatewayApiMarkDO> { 

  /**
   * 根据双条件进行数据删除
   * @param t
   * @return
   */
  int deleteDB(GatewayApiMarkDO t);
  
  /**
   * 根据接口ID查询相关的标签信息
   * @param apiId 接口ID
   * @return
   */
  List<GatewayMarkDO> selectMarks(Long apiId);
}