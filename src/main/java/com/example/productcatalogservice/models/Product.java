package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel{
    private String product_name;
    private String product_description;
    private String image_url;
    private Double product_price;
    private Category category;
}
