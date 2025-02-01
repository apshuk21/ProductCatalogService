package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.SortParams;
import com.example.productcatalogservice.dtos.SortType;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "JpaSearchService")
@RequiredArgsConstructor
public class JpaSearchService implements ISearchService {

    private final ProductRepo productRepo;

    @Override
    public Page<Product> searchProducts(String searchQuery, int pageSize, int pageNumber, List<SortParams> sortParams) {

        Optional<Sort> sortOptional = sortParams.stream().map(sortParam -> {
            Sort.Direction direction = sortParam.getSortType() == SortType.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
            return Sort.by(direction, sortParam.getSortBy());
        }).reduce(Sort::and);

        Sort sort = sortOptional.orElse(Sort.unsorted());
        return productRepo.findProductByProductName(searchQuery, PageRequest.of(pageNumber, pageSize, sort));
    }
}
