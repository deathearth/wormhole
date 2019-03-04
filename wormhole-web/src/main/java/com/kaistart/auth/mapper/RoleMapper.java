package com.kaistart.auth.mapper;

import org.springframework.stereotype.Repository;

import com.kaistart.auth.domain.Role;
import com.kaistart.gateway.support.proto.ProtoMapper;

/**角色
 * @author fendyguo
 * @date 2018年09月13日 下午7:26:45
 */
@Repository
public abstract interface RoleMapper extends ProtoMapper<Role> {
}
