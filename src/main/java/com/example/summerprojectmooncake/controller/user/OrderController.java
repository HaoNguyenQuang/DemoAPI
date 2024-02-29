package com.example.summerprojectmooncake.controller.user;

import com.example.summerprojectmooncake.common.TokenFunction;
import com.example.summerprojectmooncake.payload.request.OrderDetailRequest;
import com.example.summerprojectmooncake.payload.request.OrderRequest;
import com.example.summerprojectmooncake.payload.response.MessageResponse;
import com.example.summerprojectmooncake.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestPart("orderRequest") OrderRequest orderRequest,
                      @RequestPart("listOrderDetailRequest") List<OrderDetailRequest> listOrderDetailRequest, HttpServletRequest request){
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkInsert = orderService.addOrderAndOrderDetails(orderRequest, listOrderDetailRequest, request);
            if (checkInsert)
                return ResponseEntity.ok(new MessageResponse("add order successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("add order failed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
}
