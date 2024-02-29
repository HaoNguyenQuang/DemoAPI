package com.example.summerprojectmooncake.service.impl;

import com.example.summerprojectmooncake.entity.ERole;
import com.example.summerprojectmooncake.entity.Role;
import com.example.summerprojectmooncake.entity.User;
import com.example.summerprojectmooncake.entity.VerificationToken;
import com.example.summerprojectmooncake.exception.UserAlreadyExistsException;
import com.example.summerprojectmooncake.payload.request.LoginRequest;
import com.example.summerprojectmooncake.payload.request.SignUpRequest;
import com.example.summerprojectmooncake.repository.UserRepository;
import com.example.summerprojectmooncake.repository.VerificationTokenRepository;
import com.example.summerprojectmooncake.service.RoleService;
import com.example.summerprojectmooncake.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public User registerUser(SignUpRequest request) {
        User user = this.findByEmail(request.getEmail());
        if(user!=null){
            throw new UserAlreadyExistsException("User with email "+request.getEmail()+" already exists");
        }
        var newUser = new User();
        newUser.setName(request.getName());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setEmail(request.getEmail());
        newUser.setAddress(request.getAddress());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<String> strRoles = request.getListRoles();
        Set<Role> listRole = new HashSet<>();
        if(strRoles==null){
            Role userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role not found"));
            listRole.add(userRole);
        }
        request.getListRoles().forEach(role -> {
            switch (role) {
                case "user":
                    Role userRole = roleService.findByRoleName(ERole.ROLE_USER).
                            orElseThrow(() -> new RuntimeException("Error: role user is not found"));
                    listRole.add(userRole);
                    break;
                case "admin":
                    Role userRoleAdmin = roleService.findByRoleName(ERole.ROLE_ADMIN).
                            orElseThrow(() -> new RuntimeException("Error: role admin is not found"));
                    listRole.add(userRoleAdmin);
                    break;
            }
        });
        newUser.setListRole(listRole);
        return userRepository.save(newUser);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token,theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token) {
        VerificationToken theToken = tokenRepository.findByToken(token);
        if(theToken==null){
            return "Invalid verification token";
        }
        User user = theToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(theToken.getExpirationTime().getTime()-calendar.getTime().getTime()<=0){
            return "token already expired, please click the link below to receive a new verification link";
        }
        user.setEnable(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        VerificationToken verificationTokenTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }

    @Override
    public boolean checkEmailAndPassword(LoginRequest loginRequest) {
        if(userRepository.existsByEmail(loginRequest.getEmail())){
            User user = userRepository.findByEmail(loginRequest.getEmail());
            if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                return true;
        }
        return false;
    }
}
