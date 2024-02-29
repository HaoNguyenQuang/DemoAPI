package com.example.summerprojectmooncake.service;

import com.example.summerprojectmooncake.entity.User;
import com.example.summerprojectmooncake.entity.VerificationToken;
import com.example.summerprojectmooncake.payload.request.LoginRequest;
import com.example.summerprojectmooncake.payload.request.SignUpRequest;

public interface UserService {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    User saveOrUpdate(User user);

    User registerUser(SignUpRequest request);

    void saveUserVerificationToken(User theUser, String token);

    String validateToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
    boolean checkEmailAndPassword(LoginRequest loginRequest);
}
