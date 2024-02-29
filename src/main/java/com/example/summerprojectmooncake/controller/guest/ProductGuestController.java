package com.example.summerprojectmooncake.controller.guest;

import com.example.summerprojectmooncake.entity.Product;
import com.example.summerprojectmooncake.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/guest/product")
public class ProductGuestController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/findProduct")
    public ResponseEntity<List<Product>> findProductByContainName(@RequestParam(value = "productName") String productName) {
        return ResponseEntity.ok(productService.getProductByName(productName));
    }
    @GetMapping("/getAllProduct")
    public ResponseEntity<Map<String, Object>> getAllProduct(@RequestParam(name = "pageNumber",defaultValue = "0") int pageNumber
            ,@RequestParam(name = "pageSize",defaultValue = "3") int pageSize) {
        Pageable page = PageRequest.of(pageNumber,pageSize);
        Page<Product> productPage =productService.getAllProduct(page);
        Map<String, Object> data = new HashMap<>();
        data.put("products",productPage.getContent());
        data.put("total",productPage.getSize());
        data.put("totalItems",productPage.getTotalElements());
        data.put("totalPages",productPage.getTotalPages());
        return ResponseEntity.ok(data);
    }
}
