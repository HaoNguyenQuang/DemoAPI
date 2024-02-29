package com.example.summerprojectmooncake.converter;

import com.example.summerprojectmooncake.entity.Product;
import com.example.summerprojectmooncake.payload.request.ProductRequest;

public class ProductConverter {
    public static Product toProductEntity(ProductRequest productRequest){
        return new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice(),
                productRequest.isCatalog()
        );
    }
}
