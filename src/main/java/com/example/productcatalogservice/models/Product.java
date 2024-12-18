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
    private Boolean isPrime;

//    @Override
//    public String toString() {
//        return "Product [product_name=" + product_name + ", product_description=" + product_description + ", image_url=" + image_url + ", product_price=" + product_price + ", categoryId=" + category.getId() + ", isPrime=" + isPrime + "]" + ", categoryName=" + category.getName() + ", categoryDescription=" + category.getDescription();
//    }
}
