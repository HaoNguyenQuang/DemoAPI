package com.example.summerprojectmooncake.service.impl;

import com.example.summerprojectmooncake.converter.ProductConverter;
import com.example.summerprojectmooncake.entity.Product;
import com.example.summerprojectmooncake.payload.request.ProductRequest;
import com.example.summerprojectmooncake.repository.ProductRepository;
import com.example.summerprojectmooncake.service.CategoryService;
import com.example.summerprojectmooncake.service.ProductService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    private static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/image/product/";
    public static String getImageName(String imageUrl){
        String[] array = imageUrl.split("/");
        return array[array.length-1];
    }
    @Override
    public boolean addProduct(ProductRequest productRequest, MultipartFile imageProduct, HttpServletRequest request) {
        try {
            String originalFileName = imageProduct.getOriginalFilename();

            // Generate random name for the file
            String randomID = UUID.randomUUID().toString();
            String fileNameWithExtension = randomID + originalFileName.substring(originalFileName.lastIndexOf("."));

            // Full Path
            String filePath = uploadDirectory + File.separator + fileNameWithExtension;
            //
            Product product = ProductConverter.toProductEntity(productRequest);
            String imageUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/image/product/" + fileNameWithExtension;
            product.setCategory(categoryService.getCategoryById(productRequest.getCategoryId()));
            product.setImage(imageUrl);
            productRepository.save(product);
            //
            // Create folder if not exists
            File folder = new File(uploadDirectory);
            if (!folder.exists()) {
                folder.mkdir();
            }
            // Copy file
            Files.copy(imageProduct.getInputStream(), Paths.get(filePath));
            System.gc();  //or
            //folder.delete();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProduct(ProductRequest productRequest, MultipartFile imageProduct, HttpServletRequest request) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(productRequest.getId());
            if (optionalProduct.isEmpty()) {
                return false;
            }
            Product existingProduct = optionalProduct.get();

            if (imageProduct != null&& !getImageName(existingProduct.getImage()).equals(imageProduct.getOriginalFilename())) {
                String originalFileName = imageProduct.getOriginalFilename();
                String randomID = UUID.randomUUID().toString();
                String fileNameWithExtension = randomID + originalFileName.substring(originalFileName.lastIndexOf("."));

                String filePath = uploadDirectory + File.separator + fileNameWithExtension;

                File folder = new File(uploadDirectory);
                if (!folder.exists()) {
                    folder.mkdir();
                }

                Files.copy(imageProduct.getInputStream(), Paths.get(filePath));

                String imageUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/image/product/" + fileNameWithExtension;
                existingProduct.setImage(imageUrl);
            }
            existingProduct.setName(productRequest.getName());
            existingProduct.setDescription(productRequest.getDescription());
            existingProduct.setPrice(productRequest.getPrice());
            existingProduct.setCatalog(productRequest.isCatalog());
            productRepository.save(existingProduct);
            System.gc();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        Optional<Product> findProduct = productRepository.findById(productId);
        if(findProduct.isPresent()){
            productRepository.deleteById(productId);
            return true;
        }
        log.error("this product does not exists");
        return false;
    }


    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByNameContaining(name);
    }
}
