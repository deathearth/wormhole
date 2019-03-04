package com.kaistart.gateway.mgr.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayAppDO;
/**
 * 网关app mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:32:39
 */
@Repository
public interface GatewayAppMapper extends CommonMapper<GatewayAppDO>{
    
    /**
     * 查询APP的数据
     * @return
     */
    List<GatewayAppDO> selectAppPage();
    
}