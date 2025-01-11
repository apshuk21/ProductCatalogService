package com.example.productcatalogservice.services;

import com.example.productcatalogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatalogservice.exceptions.ProductNotFoundException;
import com.example.productcatalogservice.exceptions.ProductNotMatchException;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("StorageProductService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepo productRepo;


    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepo.findProductById(id);

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }

        return product.get();
    }

    @Override
    public List<Product> getAllProducts () {
        List<Product> products = productRepo.findAll();

        if (products.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }

        return products;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> productInDB = productRepo.findProductById(id);

        if (productInDB.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }


        if (!Objects.equals(product.getId(), productInDB.get().getId())) {
            throw new ProductNotMatchException("Product details are not matching");
        }

        return productRepo.save(product);
    }

    @Override
    public Product addProduct(Product product) {
        Optional<Product> productInDB = productRepo.findProductById(product.getId());

        if (productInDB.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exists");
        }

        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> productInDB = productRepo.findProductById(id);

        if (productInDB.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }

        productRepo.delete(productInDB.get());
    }

    @Override
    public void deleteAllProducts() {
        productRepo.deleteAll();
    }


}
