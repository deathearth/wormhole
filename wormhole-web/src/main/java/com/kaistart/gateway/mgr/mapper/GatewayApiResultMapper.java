package com.kaistart.gateway.mgr.mapper;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayApiResultDO;
/**
 * 网关api响应信息 mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:31:20
 */
@Repository
public interface GatewayApiResultMapper extends CommonMapper<GatewayApiResultDO> {
    
    /**
     * 根据API删除信息
     * @param id
     * @return
     * @throws Exception
     */
    int deleteByApi(Long id) throws Exception;
}