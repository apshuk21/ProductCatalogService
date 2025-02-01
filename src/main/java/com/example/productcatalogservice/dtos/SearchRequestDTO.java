package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {
    private int pageSize;
    private int pageNumber;
    private String searchQuery;
}
