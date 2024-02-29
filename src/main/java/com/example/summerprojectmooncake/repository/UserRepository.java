package com.example.summerprojectmooncake.repository;

import com.example.summerprojectmooncake.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email,String password);
}
