package com.kaistart.gateway.mgr.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kaistart.gateway.domain.GatewayAppAuthDO;
import com.kaistart.gateway.dto.RequestAuthorize;
/**
 * 网关app权限 mapper类
 * @author chenhailong
 * @date 2019年2月14日 下午2:32:17
 */
@Repository
public interface GatewayAppAuthMapper extends CommonMapper<GatewayAppAuthDO>{
    
    /**
     * 根据条件查询分组授权信息
     * @param map 查询条件
     * @return
     * @throws Exception
     */
    List<RequestAuthorize> selectPageAuthGroup(Map< String, Object > map) throws Exception;
    /**
     * 根据条件查询分组授权信息列表
     * @param map 查询条件
     * @return
     * @throws Exception
     */
    List<RequestAuthorize> selectListAuthGroup(Map< String, Object > map) throws Exception;
    
    /**
     * 根据条件查询API授权信息
     * @param map 查询条件
     * @return
     * @throws Exception
     */
    List<RequestAuthorize> selectPageAuthApi(Map< String, Object > map) throws Exception;
    
    /**
     * 根据条件查询API授权信息列表
     * @param map 查询条件
     * @return
     * @throws Exception
     */
    List<RequestAuthorize> selectListAuthApi(Map< String, Object > map) throws Exception;
    
    /**
     * 根据条件查询分组的数量
     * @param map 查询条件
     * @return
     * @throws Exception
     */
    Integer selectCountGroup( Map< String, Object > map ) throws Exception;
    
    /**
     * 根据条件查询api数量
     * @param map 查询条件
     * @return
     * @throws Exception
     */
    Integer selectCountApi( Map< String, Object > map ) throws Exception;
    
    /**
     * 根据条件查询授权列表信息
     * @param map 查询条件
     * @return
     */
    List<GatewayAppAuthDO> selectAuthList(Map< String, Object > map);
}