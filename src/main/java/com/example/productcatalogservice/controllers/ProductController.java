package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();

        product.setId(1L);
        product.setProduct_name("Goggles");
        product.setProduct_description("The Goggles");
        product.setProduct_price(3.0);

        products.add(product);

        return products;
    }

    @GetMapping("{productId}")
    public Product getProductById(@PathVariable("productId") Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return product;
    }
}
