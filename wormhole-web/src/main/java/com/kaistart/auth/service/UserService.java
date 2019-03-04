package com.kaistart.auth.service;

import com.kaistart.auth.domain.User;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * 用户服务接口
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
public abstract interface UserService extends ProtoService<User> {
  /**
   * 根据名称查找用户信息
   * @param paramString
   * @return
   * @throws Exception
   */
  public abstract User selectByName(String paramString) throws Exception;
}
