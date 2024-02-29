package com.example.summerprojectmooncake.converter;

import com.example.summerprojectmooncake.entity.Order;
import com.example.summerprojectmooncake.payload.request.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
public class OrderConverter {
    public static Order toOrderEntity(OrderRequest orderRequest){
        return new Order(
                orderRequest.getAddress(),
                new Date(),
                orderRequest.getReceiver(),
                orderRequest.getPhoneNumber(),
                orderRequest.getOrderStatus()
        );
    }
}
