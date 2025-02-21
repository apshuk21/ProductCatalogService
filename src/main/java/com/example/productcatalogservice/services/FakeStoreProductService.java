package com.example.productcatalogservice.services;

import com.example.productcatalogservice.clients.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDTO;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements IProductService {

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

    @Override
    public Product getProductById(Long productId) {
       FakeStoreProductDTO fakeStoreProductDTO = fakeStoreApiClient.getFakeStoreProductById(productId);

       if (fakeStoreProductDTO != null) {
           return from(fakeStoreProductDTO);
       }

       return null;
    }


    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDTO> fakeStoreProductDTOS = fakeStoreApiClient.getAllFakeStoreProducts();
        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOS) {
            Product product = from(fakeStoreProductDTO);
            products.add(product);
        }

        return products;
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        FakeStoreProductDTO fakeStoreProductDTO = fakeStoreApiClient.updateFakeStoreProductById(productId, from(product));

        if (fakeStoreProductDTO != null) {
            return from(fakeStoreProductDTO);
        }

        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> addProducts(List<Product> products) {
        return List.of();
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public void deleteAllProducts() {

    }


    public Product from(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setProductName(fakeStoreProductDTO.getTitle());
        product.setProductDescription(fakeStoreProductDTO.getDescription());
        product.setProductPrice(fakeStoreProductDTO.getPrice());

        Category category = new Category();
        category.setName(fakeStoreProductDTO.getCategory());

        product.setCategory(category);

        return product;
    }

    private FakeStoreProductDTO from(Product product) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(product.getId());
        fakeStoreProductDTO.setTitle(product.getProductName());
        fakeStoreProductDTO.setDescription(product.getProductDescription());
        fakeStoreProductDTO.setPrice(product.getProductPrice());
        fakeStoreProductDTO.setImage(product.getImageUrl());

        if(product.getCategory() != null) {
            fakeStoreProductDTO.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDTO;
    }
}
