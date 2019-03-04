package com.kaistart.gateway.mgr.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayApiDO;

/**
 * 网关api mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:30:17
 */
@Repository
public interface GatewayApiMapper extends CommonMapper<GatewayApiDO>{
    /**
     * 查询分页信息
     * @param map
     * @return
     */
    List<GatewayApiDO> selectApiPage(Map<String, Object> map);
    
    /**
     * 模糊搜索数量处理
     * @param map
     * @return
     */
    Integer selectSearchCount(Map<String,Object> map);
    
    /**
     * 模糊搜索处理
     * @param map
     * @return
     */
    List<GatewayApiDO> selectSearchPage(Map<String, Object> map);
}