package com.kaistart.gateway.api.service;
import java.util.List;

import com.kaistart.gateway.domain.GatewayApiMarkDO;
import com.kaistart.gateway.domain.GatewayMarkDO;

/**
 * 
 * 接口标签关系表 接口
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
public interface GatewayApiMarkService extends CommonService<GatewayApiMarkDO>{ 

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