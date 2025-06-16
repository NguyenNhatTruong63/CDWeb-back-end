package com.example.CDWeb.service;

import com.example.CDWeb.model.Category;
import com.example.CDWeb.model.Product;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);

    List<Product> searchProduct(String key);

    List<Product> getRandomProducts(int limit);
    void detachCategoryFromProducts(Category category);
    void deleteProductById(int id);

     List<Product> getAllProductsNotNull();;
}
