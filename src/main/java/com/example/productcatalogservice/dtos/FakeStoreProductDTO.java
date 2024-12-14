package com.example.productcatalogservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
}
