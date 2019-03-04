package com.kaistart.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaistart.auth.domain.Permission;
import com.kaistart.auth.mapper.PermissionMapper;
import com.kaistart.auth.service.PermissionService;
import com.kaistart.gateway.support.proto.ProtoMapper;
import com.kaistart.gateway.support.proto.ProtoServiceImpl;

/**
 * 权限服务接口
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class PermissionServiceImpl extends ProtoServiceImpl<Permission> implements PermissionService {
  @Resource
  private PermissionMapper permissionMapper;

  @Override
  protected ProtoMapper<Permission> getMapper() {
    return this.permissionMapper;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void authz(Integer roleId, String resourceIds) throws Exception {
    this.permissionMapper.deleteByRoleId(roleId);

    List<Permission> permissionList = new ArrayList<Permission>();
    String[] resourceArr = resourceIds.split("-");
    for (int i = 0; i < resourceArr.length; i++) {
      Permission permission = new Permission();
      permission.setRoleId(roleId);
      permission.setResId(Integer.valueOf(resourceArr[i]));

      permissionList.add(permission);
    }
    this.permissionMapper.insertList(permissionList);
  }

  @Override
  public List<Permission> selectByRoleId(Integer roleId) throws Exception {
    return this.permissionMapper.selectByRoleId(roleId);
  }
}
