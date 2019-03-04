package com.kaistart.auth.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaistart.auth.domain.Resource;
import com.kaistart.auth.mapper.ResourceMapper;
import com.kaistart.auth.service.ResourceService;
import com.kaistart.gateway.support.proto.ProtoMapper;
import com.kaistart.gateway.support.proto.ProtoServiceImpl;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class ResourceServiceImpl extends ProtoServiceImpl<Resource> implements ResourceService {
  @javax.annotation.Resource
  private ResourceMapper resourceMapper;

  @Override
  protected ProtoMapper<Resource> getMapper() {
    return this.resourceMapper;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void deleteNode(Integer id) throws Exception {
    this.resourceMapper.delete(id);
    this.resourceMapper.deleteChildrenByPID(id);
  }
}
