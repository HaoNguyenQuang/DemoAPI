package com.example.summerprojectmooncake.payload.request;


import com.example.summerprojectmooncake.entity.Order;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Getter
@Setter
public class SignUpRequest {
    private String name;
    private String phoneNumber;
    @NaturalId(mutable = true)
    private String email;
    private String address;
    private String password;
    private Set<String> listRoles;

    public SignUpRequest(String name, String phoneNumber, String email, String address, String password, Set<String> listRoles) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.listRoles = listRoles;
    }
}