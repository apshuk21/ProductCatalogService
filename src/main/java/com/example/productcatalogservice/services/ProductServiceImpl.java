package com.example.productcatalogservice.services;

import com.example.productcatalogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatalogservice.exceptions.ProductNotFoundException;
import com.example.productcatalogservice.exceptions.ProductNotMatchException;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.CategoryRepo;
import com.example.productcatalogservice.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("StorageProductService")
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;


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
        Optional<Category> categoryOptional = categoryRepo.findByName(product.getCategory().getName());

        if (categoryOptional.isEmpty()) {
            // Create new Category
            Category category = categoryRepo.save(product.getCategory());
            product.setCategory(category);
        } else {
            product.setCategory(categoryOptional.get());
        }

        return productRepo.save(product);
    }

    @Override
    public List<Product> addProducts(List<Product> products) {
        for (Product product : products) {
            // Check if category already exists
            Optional<Category> categoryOptional = categoryRepo.findByName(product.getCategory().getName());

            if (categoryOptional.isPresent()) {
                product.setCategory(categoryOptional.get());
            } else {
               Category newCategory = categoryRepo.save(product.getCategory());
               product.setCategory(newCategory);
            }
        }

        return productRepo.saveAll(products);
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
