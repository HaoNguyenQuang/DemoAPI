package com.example.summerprojectmooncake.controller.admin;

import com.example.summerprojectmooncake.common.TokenFunction;
import com.example.summerprojectmooncake.payload.request.ProductRequest;
import com.example.summerprojectmooncake.payload.response.MessageResponse;
import com.example.summerprojectmooncake.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin/product")
@CrossOrigin("*")
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@RequestPart("product") ProductRequest product,
                                        @RequestPart("imageProduct") MultipartFile image, HttpServletRequest request) {
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkInsert = productService.addProduct(product, image, request);
            if (checkInsert)
                return ResponseEntity.ok(new MessageResponse("add product successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("add product failed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
    @PutMapping(value = "/updateProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestPart("product") ProductRequest product,
                                        @RequestPart("imageProduct") MultipartFile image, HttpServletRequest request) {
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkUpdate = productService.updateProduct(product, image, request);
            if (checkUpdate)
                return ResponseEntity.ok(new MessageResponse("update product successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("update product failed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
    @DeleteMapping(value = "/deleteProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Integer productId,HttpServletRequest request) {
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkInsert = productService.deleteProduct(productId);
            if (checkInsert)
                return ResponseEntity.ok(new MessageResponse("delete product successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("product does not exist"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
}
