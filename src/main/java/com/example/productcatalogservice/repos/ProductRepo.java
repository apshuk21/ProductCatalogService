package com.example.productcatalogservice.repos;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long productId);

    List<Product> findProductByCategoryId(Long categoryId);

    @Query("SELECT p FROM Products p WHERE p.productPrice BETWEEN :lower AND :higher")
    List <Product> findProductByAmountBetween(Double lower, Double higher);

    @Query("SELECT c.name from Products AS p JOIN Categories as c ON p.category.id = c.id WHERE p.id = :productId")
    String findCategoryByProductId(Long productId);

    Page<Product> findProductByProductName(String name, Pageable pageable);

}
