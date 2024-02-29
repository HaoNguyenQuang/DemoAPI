package com.example.summerprojectmooncake.converter;

import com.example.summerprojectmooncake.entity.Category;
import com.example.summerprojectmooncake.payload.response.CategoryResponse;
import org.springframework.stereotype.Component;

public class CategoryConverter {
    public static CategoryResponse toCategoryResponse(Category category){
        return new CategoryResponse(category.getId(), category.getName());
    }
}
