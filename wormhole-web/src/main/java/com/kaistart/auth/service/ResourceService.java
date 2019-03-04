package com.kaistart.auth.service;

import com.kaistart.auth.domain.Resource;
import com.kaistart.gateway.support.proto.ProtoService;

/**
 * 资源业务接口
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
public abstract interface ResourceService extends ProtoService<Resource> {
  /**
   * 删除资源节点
   * @param paramInteger
   * @throws Exception
   */
  public abstract void deleteNode(Integer paramInteger) throws Exception;
}
