package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    @Transactional
    public void testFetchTypes() {
        Optional<Category> category = categoryRepo.findById(12L);
        category.ifPresent(value -> System.out.println(value.getName()));
        System.out.println("--------------------------------------------");
//        if (category.isPresent()) {
//            List<Product> products = category.get().getProducts();
//
//            for(Product product : products) {
//                System.out.println(product.getProduct_name());
//            }
//        }
    }

}