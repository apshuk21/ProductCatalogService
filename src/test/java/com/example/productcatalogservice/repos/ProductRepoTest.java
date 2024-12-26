package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void testQuery() {
        List<Product> products = productRepo.findProductByAmountBetween(200D, 500D);
        for (Product product : products) {
            System.out.println(product.getProduct_name() + " " + product.getProduct_price());
        }
    }

    @Test
    public void findCategoryNameByProductId() {
        String categoryName = productRepo.findCategoryByProductId(1L);
        System.out.println("Category Name: " + categoryName);
    }

    @Test
    public void findProductById() {
        Optional<Product> productOptional = productRepo.findProductById(15L);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            System.out.println(product.getProduct_name() + " " + product.getProduct_price());

            Category category = product.getCategory();
            System.out.println("Category name: " + category.getName());
        }
    }
}