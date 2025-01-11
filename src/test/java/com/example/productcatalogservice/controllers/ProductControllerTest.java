package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @MockBean
    @Qualifier("StorageProductService")
    private IProductService productService;

    @Autowired
    private ProductController productController;

    @Test
    @DisplayName("get product with id 1 will run ok")
    public void Test_GetAllProducts_ResultInListOfProductDTO() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<ProductDTO>> productDTOs = productController.getAllProducts();

        // Assert
        assertNotNull(productDTOs);
    }

    @Test
    public void Test_GetProductById_ResultInProductDTO() {
        // Arrange
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(new Product());

        // Act
        ResponseEntity<ProductDTO> response = productController.getProductById(productId);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotEquals(productId, response.getBody().getId());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void Test_GetProductByInvalidId_ThrowsResponseStatusException() {
        // Arrange
        Long productIdOne = 0L;
        Long productIdTwo = -1L;

        // Act and Assert
        assertThrows((ResponseStatusException.class), () -> productController.getProductById(productIdOne));
        assertThrows((ResponseStatusException.class), () -> productController.getProductById(productIdTwo));
        verify(productService, times(0)).getProductById(productIdOne);
        verify(productService, times(0)).getProductById(productIdTwo);
    }

    @Test
    public void Test_GetProductByInvalidId_ThrowsResponseStatusException_WithCorrectMessage() {
        // Arrange
        Long productIdOne = 0L;
        Long productIdTwo = -1L;

        // Act
        ResponseStatusException exceptionOne = assertThrows(ResponseStatusException.class, () -> productController.getProductById(productIdOne));
        ResponseStatusException exceptionTwo = assertThrows(ResponseStatusException.class, () -> productController.getProductById(productIdTwo));

        // Assert
        assertEquals(exceptionOne.getReason(), exceptionTwo.getReason());
        assertEquals("Invalid product id. Please try with product id > 0", exceptionOne.getReason());
        verify(productService, times(0)).getProductById(productIdOne);
        verify(productService, times(0)).getProductById(productIdTwo);
    }

}