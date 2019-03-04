package com.kaistart.auth.mapper;

import org.springframework.stereotype.Repository;

import com.kaistart.auth.domain.User;
import com.kaistart.gateway.support.proto.ProtoMapper;

/**
 * 用户
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Repository
public abstract interface UserMapper extends ProtoMapper<User> {
  /**
   * 根据名称查询用户信息
   * @param paramString
   * @return
   * @throws Exception
   */
  public abstract User selectByName(String paramString) throws Exception;
}
