package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("StorageProductService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepo productRepo;


    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepo.findProductById(id);

        return product.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }
}
