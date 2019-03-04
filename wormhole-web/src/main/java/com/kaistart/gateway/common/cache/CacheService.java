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
 * 
 * @author wuyuan.lfk
 * @date 2016年12月9日 下午2:05:16
 */
public interface CacheService {

	/**
	 * 设置分布式锁
	 * 
	 * @param key
	 * @param timeout
	 *            有效时间，单位秒
	 * @return
	 */
	boolean lock(String key, int timeout);

	/**
	 * 解除分布式锁
	 * 
	 * @param key
	 * @return
	 */
	boolean unlock(String key);

	/**
	 * 注意： 使用场景仅仅是判断值是否存在，不需要获取值，如果需要获取值，直接使用get方法
	 * 
	 * @param key
	 * @return
	 */
	boolean exists(String key);

	/**
	 * 根据键获取缓存值
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 有效时间60分钟
	 * 
	 * @param key
	 * @param value
	 */
	void set(String key, String value);

	/**
	 * 设置缓存的键、值、时间
	 * @param key
	 * @param value
	 * @param seconds
	 *            有效时间，单位秒
	 */
	void set(String key, String value, int seconds);

	/**
	 * 删除键
	 * @param key
	 */
	void del(String key);

	/**
	 * 更新cache数据，先delete再set，防止脏数据
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            有效时间，单位秒
	 */
	void delAndSet(String key, String value, int seconds);
	/**
	 * 批量删除键
	 * @param partern
	 * @return
	 */
	int batchdel(String partern);

	/**
	 * 计数器+1，key不存在自动创建初始化0再+1
	 * 
	 * @param key
	 * @return
	 */
	long incr(String key);

	/**
	 * 批量获取缓存，返回String列表，如果其中有的key不存在，列表中插入null返回
	 * 
	 * @param keys
	 *            元素不能是null
	 * @return
	 */
	String[] mget(String[] keys);

	/**
	 * 设置localcache
	 * 
	 * @param localCache
	 */
	public void setLocalCache(LocalCache localCache);

}
