package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDTO;
import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductControllerWithFakeStoreService {

    @Autowired
    @Qualifier("FakeStoreProductService")
    private IProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products) {
            productDTOS.add(from(product));
        }

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDTO>  getProductById(@PathVariable("productId") Long id) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            // Validation
            if (id <= 0) {
                headers.add("my-custom-header", "invalid-id");
                headers.add("my-custom-header", "bad-request");
                throw new IllegalArgumentException("Please try with productId > 0");
            }
            Product product = productService.getProductById(id);
            if (product == null) {
                headers.add("my-custom-header", "product-is-null");
                return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
            }
            headers.add("my-custom-header", "product-found");
            return new ResponseEntity<>(from(product), headers, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw exception;
        }

    }

    @PutMapping("{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") Long id,  @RequestBody ProductDTO productDto) {
        Product product = productService.updateProduct(id, from(productDto));
        return new ResponseEntity<>(from(product), HttpStatus.OK);
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
