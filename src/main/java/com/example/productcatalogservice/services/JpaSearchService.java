package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "JpaSearchService")
@RequiredArgsConstructor
public class JpaSearchService implements ISearchService {

    private final ProductRepo productRepo;

    @Override
    public Page<Product> searchProducts(String searchQuery, int pageSize, int pageNumber) {
        return productRepo.findProductByProductName(searchQuery, PageRequest.of(pageNumber, pageSize));
    }
}
