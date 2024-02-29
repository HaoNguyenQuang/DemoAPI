package com.example.summerprojectmooncake.service;

import com.example.summerprojectmooncake.entity.Product;
import com.example.summerprojectmooncake.payload.request.OrderDetailRequest;

import java.util.List;

public interface OrderDetailService {
    boolean addToCart(OrderDetailRequest cartRequest);
    List<Product> getProductsInCartByProductId(Integer[] productsId);
}
