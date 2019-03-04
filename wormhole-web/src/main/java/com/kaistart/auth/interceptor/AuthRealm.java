package com.kaistart.auth.interceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.kaistart.auth.domain.Authorization;
import com.kaistart.auth.domain.Permission;
import com.kaistart.auth.domain.Resource;
import com.kaistart.auth.domain.User;
import com.kaistart.auth.service.AuthorizationService;
import com.kaistart.auth.service.PermissionService;
import com.kaistart.auth.service.ResourceService;
import com.kaistart.auth.service.UserService;

/**
 * @author frendy
 * @date 2019年2月21日 下午7:57:32
 */
public class AuthRealm extends AuthorizingRealm {
  private static final Logger logger = LoggerFactory.getLogger(AuthRealm.class);

  @Autowired
  private UserService userService;
  @Autowired
  private PasswordService passwordService;
  @Autowired
  private AuthorizationService authorizationService;
  @Autowired
  private PermissionService permissionService;
  @Autowired
  private ResourceService resourceService;

  @Override
  @SuppressWarnings("unchecked")
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> doGetAuthorizationInfo");
    if (principalCollection == null) {
      throw new AuthorizationException("principalCollection is null");
    }
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    try {
      Map<String, Object> map = new HashMap<String, Object>(8);
      List<Integer> roleIds = new ArrayList<Integer>();
      User user = (User) principalCollection.fromRealm(getName()).iterator().next();
      map.put("userId", user.getId());
      for (Authorization authorization : this.authorizationService.selectList(map)) {
        roleIds.add(authorization.getRoleId());
      }
      List<Integer> resourceIds = new ArrayList<Integer>();
      map.clear();
      map.put("roleIds", roleIds);
      Iterator<?> iterator = permissionService.selectList(map).iterator();
      while (iterator.hasNext()) {
        resourceIds.add(((Permission) (iterator.next())).getResId());
      }
      Object set = new HashSet<Integer>(resourceIds);
      resourceIds.clear();
      resourceIds.addAll((Collection<Integer>) set);

      map.clear();
      map.put("resourceIds", resourceIds);
      List<Resource> resources = this.resourceService.selectList(map);
      for (Resource resource : resources) {
        if (!StringUtils.isBlank(resource.getUri())) {
          info.addStringPermission(resource.getUri());
        }
      }
    } catch (Exception e) {
      logger.error("authorization fail. reason is {}", e);
    }
    return info;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
    User user = null;
    UsernamePasswordToken authToken = (UsernamePasswordToken) token;
    try {
      user = this.userService.selectByName(authToken.getUsername());
    } catch (Exception e) {
      logger.error("query user error. reason is: {}", e);
      throw new AuthenticationException("user not exist");
    }
    String plainPwd = String.valueOf(authToken.getPassword());
    String encryptPwd = user.getPassword();
    if (!this.passwordService.passwordsMatch(plainPwd, encryptPwd)) {
      throw new IncorrectCredentialsException();
    }
    return new SimpleAuthenticationInfo(user, authToken.getPassword(), getName());
  }
}
