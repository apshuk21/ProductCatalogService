package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Product;

public interface IProductService {
    Product getProductById(Long id);

    Product addProduct(ProductDTO product);
}
