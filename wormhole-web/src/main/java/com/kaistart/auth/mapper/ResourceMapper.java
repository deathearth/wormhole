package com.kaistart.auth.mapper;

import org.springframework.stereotype.Repository;

import com.kaistart.auth.domain.Resource;
import com.kaistart.gateway.support.proto.ProtoMapper;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Repository
public abstract interface ResourceMapper extends ProtoMapper<Resource> {
  /**
   * 根据父节点删除子节点信息
   * @param paramLong
   * @throws Exception
   */
  public abstract void deleteChildrenByPID(int paramLong) throws Exception;
}
