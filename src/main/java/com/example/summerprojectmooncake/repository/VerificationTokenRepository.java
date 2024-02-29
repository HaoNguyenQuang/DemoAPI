package com.example.summerprojectmooncake.repository;

import com.example.summerprojectmooncake.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {

    VerificationToken findByToken(String token);
}
