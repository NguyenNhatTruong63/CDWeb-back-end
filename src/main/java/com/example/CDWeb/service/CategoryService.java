package com.example.CDWeb.service;

import com.example.CDWeb.model.Category;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryById(Integer id);
    Category getCategoryByName(String categoryName);
    void addCategory(Category category);
    boolean exitCategory(String categoryName);
     void deleteCategory(Category category) ;
    Category updateCategory(Integer id, String newCategoryName);
    //  void addCategory(Category category);
}
