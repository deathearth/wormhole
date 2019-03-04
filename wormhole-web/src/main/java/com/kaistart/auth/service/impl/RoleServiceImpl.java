package com.kaistart.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaistart.auth.domain.Role;
import com.kaistart.auth.mapper.RoleMapper;
import com.kaistart.auth.service.RoleService;
import com.kaistart.gateway.support.proto.ProtoMapper;
import com.kaistart.gateway.support.proto.ProtoServiceImpl;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class RoleServiceImpl extends ProtoServiceImpl<Role> implements RoleService {
  @Resource
  private RoleMapper roleMapper;

  @Override
  protected ProtoMapper<Role> getMapper() {
    return this.roleMapper;
  }
}
