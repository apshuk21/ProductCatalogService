package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.SearchRequestDTO;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.ISearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/search")
public class SearchController {

    private final ISearchService searchService;

    public SearchController(@Qualifier("JpaSearchService") ISearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public Page<Product> searchProducts(@RequestBody SearchRequestDTO searchRequestDTO) {
        String searchQuery = searchRequestDTO.getSearchQuery();
        int pageSize = searchRequestDTO.getPageSize();
        int pageNumber = searchRequestDTO.getPageNumber();

        return searchService.searchProducts(searchQuery, pageSize, pageNumber);
    }
}
