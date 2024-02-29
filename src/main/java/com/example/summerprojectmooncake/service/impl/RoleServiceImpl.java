package com.example.summerprojectmooncake.service.impl;

import com.example.summerprojectmooncake.entity.ERole;
import com.example.summerprojectmooncake.entity.Role;
import com.example.summerprojectmooncake.entity.User;
import com.example.summerprojectmooncake.exception.NotFoundException;
import com.example.summerprojectmooncake.repository.RoleRepository;
import com.example.summerprojectmooncake.repository.UserRepository;
import com.example.summerprojectmooncake.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<Role> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public boolean addRoleForUser(Integer userId, Integer roleId) {
        User findUser = userRepository.findById(userId).orElse(null);
        if(findUser==null){
            log.error("not found user with this id");
            return false;
        }
        Set<Role> listRole = findUser.getListRole();
        Role findRole = roleRepository.findById(roleId).orElse(null);
        if(findRole==null){
            log.error("not found role with this id");
            return false;
        }
        listRole.add(findRole);
        findUser.setListRole(listRole);
        userRepository.save(findUser);
        return true;
    }
}
