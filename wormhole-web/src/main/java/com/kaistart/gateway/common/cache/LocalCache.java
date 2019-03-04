/*
 * Copyright 2016 kaistart.com All right reserved. This software is the
 * confidential and proprietary information of kaistart.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with kaistart.com.
 */
package com.kaistart.gateway.common.cache;

/**
 *
 * 本地缓存，只提供接口，由使用方提供实现
 * @author wuyuan.lfk
 * @date 2017年8月8日 下午1:54:48 
 */
public interface LocalCache {
  /**
   * 查询指定key的缓存数据
   * <br>如果数据过期删除，并且返回null
   * @param key
   * @return 
   */
  Object get(String key);
  
  /**
   * 设置指定key的缓存数据
   * <br>key在白名单中
   * <br>value != null
   * <br>expiredTime > 0
   * <br>没有超过缓存数量限制
   * <br>缓存开关已打开
   * @param key
   * @param value
   * @param expiredTime 过期时间单位毫秒
   * @return 
   */
//  void set(String key, Object value, long expiredTime);
  
  /**
   * 设置指定key的缓存数据
   * <br>key在白名单中
   * <br>value != null
   * <br>没有超过缓存数量限制
   * <br>缓存开关已打开
   * <br>设置默认的过期时间 或则 根据配置 设置过期时间
   * @param key
   * @param value
   * @return 
   */
  void set(String key, Object value); 
  
  /**
   * 删除指定key的缓存数据
   * @param key
   * @return
   */
  Object delete(String key);
  
  /**
   * 输出统计信息
   */
  void stat();
  
}
