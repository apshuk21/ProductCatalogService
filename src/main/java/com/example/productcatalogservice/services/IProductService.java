package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, Product product);

    Product addProduct(Product product);

    void deleteProduct(Long id);

    void deleteAllProducts();
}
