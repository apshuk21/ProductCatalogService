package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);

    Product addProduct(ProductDTO product);

    List<Product> getAllProducts();
}
