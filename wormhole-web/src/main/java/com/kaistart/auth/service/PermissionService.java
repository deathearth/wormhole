package com.kaistart.auth.service;

import java.util.List;

import com.kaistart.auth.domain.Permission;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * 权限服务接口
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
public abstract interface PermissionService extends ProtoService<Permission> {
  /**
   * 查询权限
   * @param paramInteger
   * @param paramString
   * @throws Exception
   */
  public abstract void authz(Integer paramInteger, String paramString) throws Exception;

  /**
   * 查询权限列表
   * @param paramInteger
   * @return
   * @throws Exception
   */
  public abstract List<Permission> selectByRoleId(Integer paramInteger) throws Exception;
}
