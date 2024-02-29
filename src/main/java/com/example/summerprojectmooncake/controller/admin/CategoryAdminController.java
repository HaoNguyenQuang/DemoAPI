package com.example.summerprojectmooncake.controller.admin;

import com.example.summerprojectmooncake.common.TokenFunction;
import com.example.summerprojectmooncake.entity.Category;
import com.example.summerprojectmooncake.payload.response.MessageResponse;
import com.example.summerprojectmooncake.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/category")
public class CategoryAdminController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/addCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody Category category, HttpServletRequest request){
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkInsert = categoryService.addCategory(category);
            if (checkInsert)
                return ResponseEntity.ok(new MessageResponse("add category successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("add category failed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
    @PutMapping("/updateCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, HttpServletRequest request){
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkUpdate = categoryService.updateCategory(category);
            if (checkUpdate)
                return ResponseEntity.ok(new MessageResponse("update category successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("update category failed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
    @DeleteMapping("/deleteCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@RequestParam("categoryId") Integer categoryId, HttpServletRequest request){
        if (TokenFunction.checkTokenValid(request)) {
            boolean checkDelete = categoryService.deleteCategory(categoryId);
            if (checkDelete)
                return ResponseEntity.ok(new MessageResponse("delete category successfully"));
            return ResponseEntity.badRequest().body(new MessageResponse("delete category failed"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("token not valid"));
    }
}
