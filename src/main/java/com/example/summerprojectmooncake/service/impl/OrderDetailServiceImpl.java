package com.example.summerprojectmooncake.service.impl;

import com.example.summerprojectmooncake.entity.Order;
import com.example.summerprojectmooncake.entity.OrderDetail;
import com.example.summerprojectmooncake.entity.Product;
import com.example.summerprojectmooncake.exception.NotFoundException;
import com.example.summerprojectmooncake.payload.request.OrderDetailRequest;
import com.example.summerprojectmooncake.repository.OrderDetailRepository;
import com.example.summerprojectmooncake.repository.OrderRepository;
import com.example.summerprojectmooncake.repository.ProductRepository;
import com.example.summerprojectmooncake.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public boolean addToCart(OrderDetailRequest orderDetailRequest) {
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setQuantity(orderDetailRequest.getQuantity());
        Order order = orderRepository.findById(orderDetailRequest.getOrderId()).orElse(null);
        if(order==null){
            log.error("this order id does not exists: "+orderDetailRequest.getOrderId());
            return false;
        }
        orderDetail.setOrder(order);
        Product product = productRepository.findById(orderDetailRequest.getProductId()).orElse(null);
        if(product==null){
            log.error("this product id does not exists: "+orderDetailRequest.getProductId());
            return false;
        }
        orderDetail.setProduct(product);
        orderDetailRepository.save(orderDetail);
        return true;
    }

    @Override
    public List<Product> getProductsInCartByProductId(Integer[] productsId) {
        List<Product> listProduct = new ArrayList<>();
        for (int productId: productsId){
            listProduct.add(productRepository.findById(productId).orElse(null));
        }
        return listProduct;
    }
}
