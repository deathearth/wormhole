package com.kaistart.gateway.mgr.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayPartnerDO;
/**
 * 网关用户 mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:35:58
 */
@Repository
public interface GatewayPartnerMapper extends CommonMapper<GatewayPartnerDO>{
    
    /**
     * 根据条件查询网关用户信息
     * @param map 查询条件
     * @return
     */
    List<GatewayPartnerDO> selectPartnerPage(Map<String, Object> map);

}