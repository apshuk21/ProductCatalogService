package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatalogservice.exceptions.ProductNotFoundException;
import com.example.productcatalogservice.exceptions.ProductNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("app-status", exception.getMessage())
                .build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException exception) {
//        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("app-status", exception.getMessage())
                .body(exception.getMessage());
    }

    @ExceptionHandler(ProductNotMatchException.class)
    public ResponseEntity<String> handleProductNotMatchException(ProductNotMatchException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("app-status", exception.getMessage())
                .body(exception.getMessage());
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<String> handleProductAlreadyExistsException(ProductAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("app-status", exception.getMessage())
                .body(exception.getMessage());
    }

    // Generic exception handler for any other exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("app-status", "An unexpected error occured")
                .build();
    }

}
