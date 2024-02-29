package com.example.summerprojectmooncake.service;

import com.example.summerprojectmooncake.entity.Category;
import com.example.summerprojectmooncake.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Integer categoryId);
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(Integer categoryId);
    List<CategoryResponse> getAllCategory();
}
