package com.example.CDWeb.service;

import com.example.CDWeb.model.Category;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryById(Integer id);
}
