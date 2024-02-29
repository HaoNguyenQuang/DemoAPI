package com.example.summerprojectmooncake.payload.request;

import lombok.Data;

@Data
public class AuthorizationRequest {
    private int userId;
    private int roleId;
}
