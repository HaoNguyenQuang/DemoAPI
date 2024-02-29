package com.example.summerprojectmooncake.repository;

import com.example.summerprojectmooncake.entity.ERole;
import com.example.summerprojectmooncake.entity.Role;
import com.example.summerprojectmooncake.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(ERole roleName);
}
