package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDTO {
    private int pageSize;
    private int pageNumber;
    private String searchQuery;
    private List<SortParams> sortParams;
}
