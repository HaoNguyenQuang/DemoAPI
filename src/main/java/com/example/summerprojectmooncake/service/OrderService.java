package com.example.summerprojectmooncake.service;

import com.example.summerprojectmooncake.payload.request.OrderDetailRequest;
import com.example.summerprojectmooncake.payload.request.OrderRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    boolean addOrderAndOrderDetails(OrderRequest orderRequest, List<OrderDetailRequest> ListOrderDetailRequest, HttpServletRequest request);
}
