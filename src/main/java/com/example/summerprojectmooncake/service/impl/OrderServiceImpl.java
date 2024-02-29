package com.example.summerprojectmooncake.service.impl;

import com.example.summerprojectmooncake.common.TokenFunction;
import com.example.summerprojectmooncake.converter.OrderConverter;
import com.example.summerprojectmooncake.entity.Order;
import com.example.summerprojectmooncake.entity.User;
import com.example.summerprojectmooncake.payload.request.OrderDetailRequest;
import com.example.summerprojectmooncake.payload.request.OrderRequest;
import com.example.summerprojectmooncake.repository.OrderRepository;
import com.example.summerprojectmooncake.repository.UserRepository;
import com.example.summerprojectmooncake.service.OrderDetailService;
import com.example.summerprojectmooncake.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean addOrderAndOrderDetails(OrderRequest orderRequest, List<OrderDetailRequest> listOrderDetailRequest, HttpServletRequest request) {
        Order order = OrderConverter.toOrderEntity(orderRequest);
        String emailUser = TokenFunction.getEmailFromJwt(request);
        if(emailUser==null){
            log.error("get email from token has problem");
            return false;
        }
        User user = userRepository.findByEmail(emailUser);
        order.setUser(user);
        Order saveOrder = orderRepository.save(order);
        for (OrderDetailRequest orderDetailRequest:
             listOrderDetailRequest) {
            orderDetailRequest.setOrderId(saveOrder.getId());
            boolean checkInsertOrderDetail = orderDetailService.addToCart(orderDetailRequest);
            if(!checkInsertOrderDetail)
                return false;
        }
        return true;
    }
}

