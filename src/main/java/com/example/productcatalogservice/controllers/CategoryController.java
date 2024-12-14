package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Category category = new Category();

        category.setId(1L);
        category.setName("Category 1");

        categories.add(category);

        return categories;
    }

    @GetMapping("{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return category;
    }
}
