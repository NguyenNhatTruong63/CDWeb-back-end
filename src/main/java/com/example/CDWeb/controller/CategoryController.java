package com.example.CDWeb.controller;

import com.example.CDWeb.model.Category;
import com.example.CDWeb.model.User;
import com.example.CDWeb.repository.CategoryRepository;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

}
