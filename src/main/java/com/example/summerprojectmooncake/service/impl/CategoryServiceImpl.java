package com.example.summerprojectmooncake.service.impl;

import com.example.summerprojectmooncake.converter.CategoryConverter;
import com.example.summerprojectmooncake.entity.Category;
import com.example.summerprojectmooncake.payload.response.CategoryResponse;
import com.example.summerprojectmooncake.repository.CategoryRepository;
import com.example.summerprojectmooncake.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public boolean addCategory(Category category) {
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean updateCategory(Category category) {
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean deleteCategory(Integer categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return true;
        }catch (Exception ex){
            log.error("this category does not exists");
            return false;
        }
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category: categoryList) {
            categoryResponseList.add(CategoryConverter.toCategoryResponse(category));
        }
        return categoryResponseList;
    }
}
