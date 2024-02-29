package com.example.summerprojectmooncake.controller.guest;
import com.example.summerprojectmooncake.payload.response.CategoryResponse;
import com.example.summerprojectmooncake.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/guest/category")
@CrossOrigin("*")
public class CategoryGuestController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/getAllCategory")
    public ResponseEntity<List<CategoryResponse>> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
}
