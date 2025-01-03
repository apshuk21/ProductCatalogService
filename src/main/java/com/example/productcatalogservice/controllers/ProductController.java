package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDTO;
import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.State;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v2/products")
public class ProductController {

    @Autowired
    @Qualifier("StorageProductService")
    private IProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products) {
            productDTOS.add(from(product));
        }

        return productDTOS;
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
       Product product = productService.addProduct(from(productDTO));
       return from(product);
    }

    private ProductDTO from(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setProduct_name(product.getProduct_name());
        productDTO.setProduct_description(product.getProduct_description());
        productDTO.setProduct_price(product.getProduct_price());

        if (product.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(product.getCategory().getId());
            categoryDTO.setName(product.getCategory().getName());

            productDTO.setCategory(categoryDTO);
        }

        return productDTO;
    }

    private Product from(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setCreated_at(new Date());
        product.setUpdated_at(new Date());
        product.setState(State.ACTIVE);
        product.setProduct_name(productDTO.getProduct_name());
        product.setProduct_description(productDTO.getProduct_description());
        product.setProduct_price(productDTO.getProduct_price());

        if (productDTO.getCategory() != null) {
            CategoryDTO categoryDTO = productDTO.getCategory();
            Category category = new Category();

            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());

            product.setCategory(category);
        }
        System.out.println("Product: " + product);
        return product;
    }
}
