package com.kaistart.gateway.mgr.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayServiceRequestDO;
/**
 * 网关请求参数 mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:36:18
 */
@Repository 
public interface GatewayServiceRequestMapper extends CommonMapper<GatewayServiceRequestDO>{
    
    /**
     * 根据条件查询api的参数列表信息
     * @param params 查询条件
     * @return
     */
    List<GatewayServiceRequestDO> selectParamsList(Map<String, Object> params);
    
    /**
     * 根据apiid 删除接口的参数信息
     * @param id api - id
     * @return
     * @throws Exception
     */
    int deleteByApi(Long id) throws Exception;
}