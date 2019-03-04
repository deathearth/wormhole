package com.kaistart.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kaistart.auth.domain.Authorization;
import com.kaistart.gateway.support.proto.ProtoMapper;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Repository
public abstract interface AuthorizationMapper extends ProtoMapper<Authorization> {
  
  /**
   * 根据用户id删除信息
   * @param paramInteger
   * @throws Exception
   */
  public abstract void deleteByUserId(Integer paramInteger) throws Exception;
  /**
   * 插入新
   * @param paramList
   * @throws Exception
   */
  public abstract void insertList(List<Authorization> paramList) throws Exception;

  /**
   * 根据用户ID查询认证信息
   * @param paramInteger
   * @return
   * @throws Exception
   */
  public abstract List<Authorization> selectByUserId(Integer paramInteger) throws Exception;
}

