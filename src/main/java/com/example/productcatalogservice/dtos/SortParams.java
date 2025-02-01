package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParams {
    private SortType sortType;
    private String sortBy;
}
