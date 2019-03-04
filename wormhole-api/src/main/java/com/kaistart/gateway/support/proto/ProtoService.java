package com.kaistart.gateway.support.proto;

import java.util.List;
import java.util.Map;

import com.kaistart.gateway.support.page.PageParam;
import com.kaistart.gateway.support.page.PageResult;

/**
 * 原型服务接口。提供基础的增、删、改、查等数据访问服务。
 *
 * @author 翁耀中 2016/12/13.
 */
public interface ProtoService< T extends ProtoBean > {

    /**
     * 记录新增
     *
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常
     */
    void insert( T bean ) throws Exception;
    
    /**
     * 批量新增记录
     * @param list
     * @throws Exception
     */
    void batchInsert(List<T> list) throws Exception;

    /**
     * 记录删除(逻辑删除)
     *
     * @param id 要删除记录的主键
     * @throws Exception 数据库访问异常
     */
    void delete( Object id ) throws Exception;
    
    
    /**
     * 真删除
     *
     * @param id 要删除记录的主键
     * @throws Exception 数据库访问异常
     */
    void deleteDB( Object id ) throws Exception;

    /**
     * 记录更新
     * @param bean 需持久化的数据对象
     * @throws Exception 数据库访问异常
     */
    void update( T bean ) throws Exception;
    
    /**
     * 根据版本号更新记录
     * @param bean
     * @throws Exception
     */
    void updateByVersion(T bean) throws Exception;

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
     * 根据查询条件分页查询。
     *
     * @param pageParam 查询条件映射
     * @return
     * @throws Exception 数据库访问异常
     */
    PageResult< T > selectPage( PageParam pageParam ) throws Exception;
    
    /**
     * 根据查询条件分页查询。
     *
     * @param map 查询条件映射
     * @return
     * @throws Exception 数据库访问异常
     */
    List<T> selectPage(Map<String, Object> map) throws Exception ;

}
