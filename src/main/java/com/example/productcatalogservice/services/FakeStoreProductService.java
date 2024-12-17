package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDTO;
import com.example.productcatalogservice.dtos.ProductDTO;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> fakeProductResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}", FakeStoreProductDTO.class, productId);

         if (fakeProductResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeProductResponseEntity.getBody() != null) {
             return from(fakeProductResponseEntity.getBody());
         }

         return null;
    }

    @Override
    public Product addProduct(ProductDTO product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> fakeProductResponseEntity =
                restTemplate.postForEntity("https://fakestoreapi.com/products", product, FakeStoreProductDTO.class);

        var responseBody = fakeProductResponseEntity.getBody();

        if (fakeProductResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && responseBody != null) {
            return from(responseBody);
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO[]> fakeStoreProducts = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);

        if (fakeStoreProducts.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProducts.getBody() != null) {
            for (FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProducts.getBody()) {
                products.add(from(fakeStoreProductDTO));
            }
        }

        return products;
    }



    public Product from(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setProduct_name(fakeStoreProductDTO.getTitle());
        product.setProduct_description(fakeStoreProductDTO.getDescription());
        product.setProduct_price(fakeStoreProductDTO.getPrice());

        Category category = new Category();
        category.setName(fakeStoreProductDTO.getCategory());

        product.setCategory(category);

        return product;
    }
}
