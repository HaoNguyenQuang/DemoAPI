package com.example.summerprojectmooncake.payload.request;

import com.example.summerprojectmooncake.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private boolean catalog; //1: bánh nướng, 0: bánh dẻo
    private Integer categoryId;

    public ProductRequest(String name, String description, Integer price, boolean catalog, Integer categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.catalog = catalog;
        this.categoryId = categoryId;
    }
}
