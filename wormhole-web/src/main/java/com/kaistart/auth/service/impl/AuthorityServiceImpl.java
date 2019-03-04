package com.kaistart.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaistart.auth.domain.Authorization;
import com.kaistart.auth.domain.Permission;
import com.kaistart.auth.domain.Resource;
import com.kaistart.auth.domain.User;
import com.kaistart.auth.service.AuthorityService;
import com.kaistart.auth.service.AuthorizationService;
import com.kaistart.auth.service.PermissionService;
import com.kaistart.auth.service.ResourceService;
import com.kaistart.gateway.common.exception.GatewayException;

/**
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private PermissionService permissionService;

  @Autowired
  private AuthorizationService authorizationService;

  @Override
  public List<Resource> findResourceList() throws Exception {
    Map<String, Object> map = new HashMap<String, Object>(8);
    Set<Integer> set = new HashSet<Integer>();

    List<Integer> roleIds = new ArrayList<Integer>();
    List<Integer> resourceIds = new ArrayList<Integer>();

    User user = (User) SecurityUtils.getSubject().getPrincipal();
    if(user == null){
      throw new GatewayException(401, "未登录");
    }
    List<Authorization> authorizationList = this.authorizationService.selectByUserId(user.getId());
    for (Authorization authorization : authorizationList) {
      roleIds.add(authorization.getRoleId());
    }
    map.put("roleIds", roleIds);

    List<Permission> permissionList = this.permissionService.selectList(map);
    for (Permission permission : permissionList) {
      resourceIds.add(permission.getResId());
    }
    set.addAll(resourceIds);
    resourceIds.clear();
    resourceIds.addAll(set);

    map.clear();
    map.put("resourceIds", resourceIds);

    List<Resource> resourceList = this.resourceService.selectList(map);

    return resourceList;
  }
}
