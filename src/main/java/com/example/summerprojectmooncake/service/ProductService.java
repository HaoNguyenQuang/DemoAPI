package com.example.summerprojectmooncake.service;

import com.example.summerprojectmooncake.entity.Product;
import com.example.summerprojectmooncake.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {
    boolean addProduct(ProductRequest product, MultipartFile imageProduct, HttpServletRequest request);
    boolean updateProduct(ProductRequest product, MultipartFile imageProduct, HttpServletRequest request);
    boolean deleteProduct(Integer productId);
    Page<Product> getAllProduct(Pageable pageable);
    List<Product> getProductByName(String name);
}
