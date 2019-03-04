/*
 * Copyright 2016 kaistart.com All right reserved. This software is the confidential and proprietary
 * information of kaistart.com ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with kaistart.com.
 */
package com.kaistart.gateway.common.cache.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.kaistart.gateway.common.cache.CacheService;
import com.kaistart.gateway.common.cache.LocalCache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * 
 * @author wuyuan.lfk
 * @date 2016年12月9日 下午2:06:49
 */
public class CacheServiceImpl implements CacheService, InitializingBean {
  private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
  private static final Logger warnLogger = LoggerFactory.getLogger("warning");
  
  private static final String LOCK_VALUE = "v";
  private static final String REDIS_SET_IF_NOT_EXIST = "NX";
  private static final String REDIS_TIME_UNIT_SECONDS = "EX";
  private static final String REDIS_RESULT_CODE_OK = "OK";
  private String host;
  private int port;
  private String password;
  private int timeout;
  private JedisPool pool;
  private LocalCache localCache;

  @Override
  public boolean lock(String key, int timeout) {
    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      // 成功返回OK，失败返回null
      String code = jedis.set(key, LOCK_VALUE, REDIS_SET_IF_NOT_EXIST, REDIS_TIME_UNIT_SECONDS, timeout);
      return REDIS_RESULT_CODE_OK.equalsIgnoreCase(code);
    } catch (Exception e) {
      logger.error("redis lock error key=" + key+", eid="+e.hashCode(), e);
      warnLogger.error("redis lock error key=" + key+", eid="+e.hashCode());
      return false;
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
  }

  @Override
  public boolean unlock(String key) {
    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      jedis.del(key);
    } catch (Exception e) {
      logger.error("redis unlock-del error key=" + key, e);
      return false;
    } finally {
      if (jedis != null) {
        jedis.close();
        // pool.returnResourceObject(jedis);
      }
    }
    return true;
  }

  @Override
  public boolean exists(String key) {
    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      return jedis.exists(key);
    } catch (Exception e) {
      return false;
    } finally {
      if (jedis != null) {
        jedis.close();
        // pool.returnResourceObject(jedis);
      }
    }
  }

  @Override
  public String get(String key) {
    // 先查询localcache
    if (localCache != null) {
      Object value = localCache.get(key);
      if (value != null) {
        return (String) value;
      }
    }

    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      String value = jedis.get(key);
      // 先设置localcache
      if (value != null && localCache != null) {
        localCache.set(key, value);
      }
      return value;
    } catch (Exception e) {
      return null;
    } finally {
      if (jedis != null) {
        jedis.close();
        // pool.returnResourceObject(jedis);
      }
    }
  }

  @Override
  public void set(String key, String value) {
    set(key, value, 3600);
  }

  @Override
  public void set(String key, String value, int seconds) {
    // 先设置localcache
    if (localCache != null) {
      localCache.set(key, value);
    }

    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      jedis.setex(key, seconds, value);
    } catch (Exception e) {
      logger.error("redis set error key=" + key, e);
    } finally {
      if (jedis != null) {
        jedis.close();
        // pool.returnResourceObject(jedis);
      }
    }
  }

  @Override
  public void del(String key) {

    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      jedis.del(key);
    } catch (Exception e) {
      logger.error("redis del error key=" + key, e);
    } finally {
      if (jedis != null) {
        jedis.close();
        // pool.returnResourceObject(jedis);
      }
    }

    // 最后删除localcache
    if (localCache != null) {
      localCache.delete(key);
    }
  }

  @Override
  public void delAndSet(String key, String value, int seconds) {

    Jedis jedis = null;
    try {
      jedis = pool.getResource();
    } catch (Exception e) {
      logger.error("redis delAndSet error key=" + key, e);
      return;
    }
    try {
      jedis.del(key);
    } catch (Exception e) {
      logger.error("redis del error key=" + key, e);
    }
    try {
      jedis.setex(key, seconds, value);
    } catch (Exception e) {
      logger.error("redis set error key=" + key, e);
    }

    if (jedis != null) {
      jedis.close();
    }

    // 最后删除localcache
    if (localCache != null) {
      localCache.delete(key);
    }
  }

  @Override
  public int batchdel(String partern) {
    Jedis jedis = null;
    int result = 0;
    try {
      jedis = pool.getResource();
      Set<String> set = jedis.keys(partern);
      if (set != null && !set.isEmpty()) {
        jedis.del(set.toArray(new String[set.size()]));
        result = set.size();

        // 最后删除localcache
        if (localCache != null) {
          for (String key : set) {
            localCache.delete(key);
          }
        }
      }
    } catch (Exception e) {
      logger.error("redis batch delete error key=" + partern, e);
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
    return result;
  }

  @Override
  public long incr(String key) {
    Jedis jedis = null;
    try {
      jedis = pool.getResource();
      return jedis.incr(key);
    } catch (Exception e) {
      logger.error("redis incr error key=" + key, e);
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
    return 0;
  }

  @Override
  public String[] mget(String[] keys) {
    Jedis jedis = null;
    String[] result = new String[keys.length];
    try {
      int localCacheNotExistCount = 0;
      // 先查localcache
      if (localCache != null) {
        for (int i = 0; i < keys.length; i++) {
          Object value = localCache.get(keys[i]);
          if (value == null) {
            localCacheNotExistCount++;
            result[i] = null;
          } else {
            result[i] = (String) value;
          }
        }
      }

      // 计算需要查询缓存的key
      String[] targetKeys = new String[localCacheNotExistCount];
      // 返回结果的目标地址
      int[] map = new int[localCacheNotExistCount];
      int k = 0;
      for (int i = 0; i < result.length; i++) {
        if (result[i] == null) {
          targetKeys[k] = keys[i];
          map[k] = i;
          k++;
        }
      }

      // localcache查不到，再查redis
      if (localCacheNotExistCount > 0) {
        jedis = pool.getResource();
        List<String> list = jedis.mget(targetKeys);
        for (int i = 0; i < list.size(); i++) {
          if (list.get(i) != null) {
            result[map[i]] = list.get(i);
            // 设置localcache
            if (localCache != null) {
              localCache.set(targetKeys[i], list.get(i));
            }
          }
        }
      }
      return result;
    } catch (Exception e) {
      logger.error("redis mget error keys=" + JSON.toJSONString(keys));
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }

    return result;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(1024);
    config.setMaxIdle(100);
    config.setMaxWaitMillis(1000);
    // config.setTestOnBorrow(true);
    config.setTestOnReturn(true);

    pool = new JedisPool(config, host, port, timeout, password);
    logger.warn("finish init cacheService host=" + host + ", port=" + port + ", password=" + password);
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  public LocalCache getLocalCache() {
    return localCache;
  }

  @Override
  public void setLocalCache(LocalCache localCache) {
    this.localCache = localCache;
  }

}
