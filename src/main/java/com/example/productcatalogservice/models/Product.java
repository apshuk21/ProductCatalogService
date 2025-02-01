package com.example.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity(name = "Products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseModel{
    private String productName;
    private String productDescription;
    private String imageUrl;
    private Double productPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("products")
    private Category category;
    private Boolean isPrime;
}
