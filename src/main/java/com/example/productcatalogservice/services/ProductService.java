package com.example.productcatalogservice.services;

import com.example.productcatalogservice.clients.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDTO;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

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

    private FakeStoreProductDTO from(Product product) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(product.getId());
        fakeStoreProductDTO.setTitle(product.getProduct_name());
        fakeStoreProductDTO.setDescription(product.getProduct_description());
        fakeStoreProductDTO.setPrice(product.getProduct_price());
        fakeStoreProductDTO.setImage(product.getImage_url());

        if(product.getCategory() != null) {
            fakeStoreProductDTO.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDTO;
    }
}
