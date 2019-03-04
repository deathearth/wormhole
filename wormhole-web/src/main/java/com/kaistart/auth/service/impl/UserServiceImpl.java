package com.kaistart.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaistart.auth.domain.User;
import com.kaistart.auth.mapper.UserMapper;
import com.kaistart.auth.service.UserService;
import com.kaistart.gateway.support.proto.ProtoMapper;
import com.kaistart.gateway.support.proto.ProtoServiceImpl;
/**
 * 用户服务接口实现
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class UserServiceImpl extends ProtoServiceImpl<User> implements UserService {
  @Resource
  private UserMapper userMapper;

  @Override
  protected ProtoMapper<User> getMapper() {
    return this.userMapper;
  }

  @Override
  public User selectByName(String name) throws Exception {
    return this.userMapper.selectByName(name);
  }
}
