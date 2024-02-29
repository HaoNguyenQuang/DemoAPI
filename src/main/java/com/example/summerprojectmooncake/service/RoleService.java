package com.example.summerprojectmooncake.service;

import com.example.summerprojectmooncake.entity.ERole;
import com.example.summerprojectmooncake.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(ERole roleName);
    boolean addRoleForUser(Integer userId,Integer roleId);
}
