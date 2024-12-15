package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String product_name;
    private String product_description;
    private String image_url;
    private Double product_price;
    private CategoryDTO category;
}
