package com.kaistart.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kaistart.auth.domain.Permission;
import com.kaistart.gateway.support.proto.ProtoMapper;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Repository
public abstract interface PermissionMapper extends ProtoMapper<Permission> {
  /**
   * 根据角色id查询权限
   * @param paramInteger
   * @return
   * @throws Exception
   */
  public abstract List<Permission> selectByRoleId(Integer paramInteger) throws Exception;

  /**
   * 根据橘色ID删除信息
   * @param paramInteger
   * @throws Exception
   */
  public abstract void deleteByRoleId(Integer paramInteger) throws Exception;

  /**
   * 插入数据
   * @param paramList
   * @throws Exception
   */
  public abstract void insertList(List<Permission> paramList) throws Exception;
}
