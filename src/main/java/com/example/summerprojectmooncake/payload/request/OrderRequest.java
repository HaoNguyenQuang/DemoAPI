package com.example.summerprojectmooncake.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String address;
    private Date createdDate = new Date();
    private String receiver;
    private String phoneNumber;
    private Integer orderStatus;
}
