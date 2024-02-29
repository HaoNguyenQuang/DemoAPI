package com.example.summerprojectmooncake.controller.guest;

import com.example.summerprojectmooncake.entity.Product;
import com.example.summerprojectmooncake.payload.response.CategoryResponse;
import com.example.summerprojectmooncake.payload.response.MessageResponse;
import com.example.summerprojectmooncake.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/guest/orderDetail")
public class OrderDetailGuestController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping(value = "/getProductsByProductsId")
    public ResponseEntity<?> getProductsByProductsId(@RequestBody Integer[] productsId) {
        if (productsId == null || productsId.length == 0) {
            return ResponseEntity.ok(new MessageResponse(404, "productsId is null"));
        }
        return ResponseEntity.ok(orderDetailService.getProductsInCartByProductId(productsId));
    }
}
