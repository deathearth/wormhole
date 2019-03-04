package com.kaistart.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaistart.auth.domain.Authorization;
import com.kaistart.auth.mapper.AuthorizationMapper;
import com.kaistart.auth.service.AuthorizationService;
import com.kaistart.gateway.support.proto.ProtoMapper;
import com.kaistart.gateway.support.proto.ProtoServiceImpl;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class AuthorizationServiceImpl extends ProtoServiceImpl<Authorization> implements AuthorizationService {
  @Resource
  private AuthorizationMapper authorizationMapper;

  @Override
  protected ProtoMapper<Authorization> getMapper() {
    return this.authorizationMapper;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void authz(Integer userId, String roleIds) throws Exception {
    this.authorizationMapper.deleteByUserId(userId);

    List<Authorization> authorizationList = new ArrayList<Authorization>();
    String[] roleIdArr = roleIds.split("-");
    for (int i = 0; i < roleIdArr.length; i++) {
      Authorization authorization = new Authorization();
      authorization.setUserId(userId);
      authorization.setRoleId(Integer.valueOf(roleIdArr[i]));

      authorizationList.add(authorization);
    }
    this.authorizationMapper.insertList(authorizationList);
  }

  @Override
  public List<Authorization> selectByUserId(Integer userId) throws Exception {
    return this.authorizationMapper.selectByUserId(userId);
  }
}
