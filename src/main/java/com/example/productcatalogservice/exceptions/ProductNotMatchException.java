package com.example.productcatalogservice.exceptions;

public class ProductNotMatchException extends RuntimeException {
    public ProductNotMatchException(String message) {
        super(message);
    }
}
