/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.mgr.mapper;

import java.util.List;
import java.util.Map;

import com.kaistart.gateway.domain.CommonDO;

/**
 * 公共dao层方法
 * @author chenhailong
 * @date 2018年7月18日 下午1:48:45 
 */
public interface CommonMapper<T extends CommonDO> {
  
 /**
  * 根据主键删除数据
  * @param id 主键id
  * @return
  */
 int deleteByPrimaryKey(Long id);

 /**
  * 插入数据,全字段
  * @param record
  * @return
  */
 int insert(T record);

 /**
  * 插入数据,必要字段，其余默认值
  * @param record
  * @return
  */
 int insertSelective(T record);

 
 /**
  * 根据主键查询信息
  * @param id 主键ID
  * @return
  */
 T selectByPrimaryKey(Long id);

 /**
  * 修改设置信息
  * @param record 记录
  * @return 
  */
 int updateByPrimaryKeySelective(T record);

 /**
  * 根据主键修改信息
  * @param record
  * @return
  */
 int updateByPrimaryKey(T record);
 
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
 List<T> selectList( Map< String, Object > map ) throws Exception;
 
 /**
  * 查询分页信息
  * @param map 查询条件
  * @return
  * @throws Exception
  */
 List< T > selectPage( Map< String, Object > map ) throws Exception;

}
