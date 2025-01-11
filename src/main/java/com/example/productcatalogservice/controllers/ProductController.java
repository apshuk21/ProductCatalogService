package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDTO;
import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.State;
import com.example.productcatalogservice.repos.ProductRepo;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v2/products")
public class ProductController {

    @Autowired
    @Qualifier("StorageProductService")
    private IProductService productService;
    @Autowired
    private ProductRepo productRepo;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        /**
         * HttpHeaders is of type MultiValueMap
         */
        HttpHeaders headers = new HttpHeaders();
        List<Product> products = productService.getAllProducts();

        headers.add("my-content", "products found");

        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = from(product);
            productDTOS.add(productDTO);
        }

//        return new ResponseEntity<>(productDTOS, headers, HttpStatus.OK);
        return ResponseEntity.ok().headers(headers).body(productDTOS);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Long id) {
//        try {
//            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//
//            if (id <= 0) {
//                throw new IllegalArgumentException("Invalid product id. Please try with product id > 0");
//            }
//            Product product = productService.getProductById(id);
//            headers.add("success", "Valid product id");
//
//            if (product == null) {
//                return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
//            }
//            ProductDTO productDTO = from(product);
//
//            return new ResponseEntity<>(productDTO, headers, HttpStatus.OK);
//        } catch (IllegalArgumentException exception) {
//            System.out.println("Error message: " + exception.getMessage());
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
//        }

        HttpHeaders headers = new HttpHeaders();

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid product id. Please try with product id > 0");
        }
        headers.add("my-content", "product found");
        Product product = productService.getProductById(id);

        ProductDTO productDTO = from(product);
        return ResponseEntity.ok().headers(headers).body(productDTO);

    }

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
       Product product = productService.addProduct(from(productDTO));
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(from(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") Long id,  @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(id, from(productDTO));
        return ResponseEntity.ok().body(from(product));

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllProducts() {
        productService.deleteAllProducts();
        return ResponseEntity.noContent().build();
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
        product.setCreated_at(LocalDateTime.now());
        product.setUpdated_at(LocalDateTime.now());
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
