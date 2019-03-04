package com.kaistart.gateway.support.proto;

import java.util.List;
import java.util.Map;

/**
 * 原型数据访问接口。提供基础的数据访问层增、删、改、查等数据访问操作。
 *
 * @author 翁耀中 2016/12/7.
 */
public interface ProtoMapper< T extends ProtoBean > {

    /**
     * 记录新增
     *
     * @param bean 需持久化的数据对象
     * @return
     * @throws Exception 数据库访问异常
     */
    int insert( T bean ) throws Exception;
    
    /**
     * 批量新增记录
     * @param list
     * @return int
     * @return
     * @throws Exception
     */
    int batchInsert(List<T> list) throws Exception;

    /**
     * 记录删除（逻辑删除）
     *
     * @param id 要删除记录的主键
     * @return
     * @throws Exception 数据库访问异常
     */
    int delete( Object id ) throws Exception;
    
    /**
     * 记录删除（真删除）
     *
     * @param id 要删除记录的主键
     * @return
     * @throws Exception 数据库访问异常
     */
    int deleteDB( Object id ) throws Exception;

    /**
     * 记录更新
     *
     * @param bean 需持久化的数据对象
     * @return
     * @throws Exception 数据库访问异常
     */
    int update( T bean ) throws Exception;
    
    /**
     * 根据版本号更新记录
     * @param bean
     * @return
     * @throws Exception
     */
    int updateByVersion(T bean) throws Exception;

    /**
     * 根据主键查询记录
     *
     * @param id 主键
     * @return
     * @throws Exception 数据库访问异常
     */
    T selectById( Object id ) throws Exception;

    /**
     * 根据查询条件返回查询结果记录数。一般用于分页查询。
     *
     * @param map 查询条件映射
     * @return
     * @throws Exception 数据库访问异常
     */
    Integer selectCount( Map< String, Object > map ) throws Exception;

    /**
     * 根据查询条件返回查询结果。
     *
     * @param map 查询条件映射
     * @return
     * @throws Exception 数据库访问异常
     */
    List< T > selectList( Map< String, Object > map ) throws Exception;
    
    /**
     * 根据查询条件返回查询结果。
     *
     * @param map 查询条件映射
     * @return
     * @throws Exception 数据库访问异常
     */
    List< T > selectPage( Map< String, Object > map ) throws Exception;
}
