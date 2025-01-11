package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMVCTest {

    @MockBean
    @Qualifier("StorageProductService")
    private IProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProducts_RunsSuccessfully() throws Exception {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProduct_name("iPhone14");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProduct_name("Macbook Pro");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        when(productService.getAllProducts()).thenReturn(products);

        ProductDTO productDto1 = new ProductDTO();
        productDto1.setProduct_name(product1.getProduct_name());
        productDto1.setId(product1.getId());

        ProductDTO productDto2 = new ProductDTO();
        productDto2.setId(product2.getId());
        productDto2.setProduct_name(product2.getProduct_name());

        List<ProductDTO> productDTOs = new ArrayList<>();
        productDTOs.add(productDto1);
        productDTOs.add(productDto2);

        // Act and Assert
        mockMvc.perform(get("/v2/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDTOs)));
    }

    @Test
    public void Test_GetProductById_RunsSuccessfully() throws Exception {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProduct_name("iPhone14");

        when(productService.getProductById(1L)).thenReturn(product1);

        ProductDTO productDto = new ProductDTO();
        productDto.setProduct_name(product1.getProduct_name());
        productDto.setId(product1.getId());

        // Act and Assert
        mockMvc.perform(get("/v2/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }
}
