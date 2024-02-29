package com.example.summerprojectmooncake.controller.admin;

import com.example.summerprojectmooncake.common.TokenFunction;
import com.example.summerprojectmooncake.entity.User;
import com.example.summerprojectmooncake.payload.request.AuthorizationRequest;
import com.example.summerprojectmooncake.payload.request.ProductRequest;
import com.example.summerprojectmooncake.payload.response.MessageResponse;
import com.example.summerprojectmooncake.service.RoleService;
import com.example.summerprojectmooncake.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/super_admin/role")
@CrossOrigin("*")
public class RoleAdminController {
    @Autowired
    private RoleService roleService;
    @PostMapping(value = "/addRoleForUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRoleForUser(@RequestBody AuthorizationRequest authRequest, HttpServletRequest request) {
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkInsert = roleService.addRoleForUser(authRequest.getUserId(), authRequest.getRoleId());
            if (checkInsert)
                return ResponseEntity.ok(new MessageResponse("add role for user successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("add role for user failed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
}
