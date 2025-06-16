package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Category;
import com.example.CDWeb.repository.CategoryRepository;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getCategoryByCategory(categoryName);
    }

    @Override
    public void addCategory(Category category) {
        if (categoryRepository.existsByCategory(category.getCategory()) ){
            throw new IllegalArgumentException("Đã có danh mục này");
        } else
            categoryRepository.save(category)   ;

    }

    @Override
    public boolean exitCategory(String categoryName) {
        return categoryRepository.existsByCategory(categoryName);
    }
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
    @Override
    public Category updateCategory(Integer id, String newCategoryName) {
        Category category = getCategoryById(id);
        if (category == null) {
            throw new RuntimeException("Không tìm thấy danh mục với ID: " + id);
        }

        // Kiểm tra nếu tên mới trùng với danh mục khác
        if (exitCategory(newCategoryName) && !category.getCategory().equalsIgnoreCase(newCategoryName)) {
            throw new RuntimeException("Tên danh mục đã tồn tại");
        }

        category.setCategory(newCategoryName);
        return categoryRepository.save(category);
    }
}
