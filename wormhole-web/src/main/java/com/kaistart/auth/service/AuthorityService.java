package com.kaistart.auth.service;

import com.kaistart.auth.domain.Resource;
import java.util.List;
/**
 * 权限服务接口
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
public interface AuthorityService {
  /**
   * 获取资源列表
   * @return
   * @throws Exception
   */
  List<Resource> findResourceList()throws Exception;
}
