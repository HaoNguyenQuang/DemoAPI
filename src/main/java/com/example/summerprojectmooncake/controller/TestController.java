package com.example.summerprojectmooncake.controller;

import com.example.summerprojectmooncake.jwt.JwtTokenProvider;
import com.example.summerprojectmooncake.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @GetMapping("/all")
    public String allAccess() {
        return "all access";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userAccess(){
        return "user content";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "admin content";
    }
}
