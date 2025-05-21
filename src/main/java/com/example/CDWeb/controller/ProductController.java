package com.example.CDWeb.controller;

import com.example.CDWeb.model.Product;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }



    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("query") String key) {
        return productService.searchProduct(key);
    }

    @GetMapping("/random")
    public List<Product> getRandomProduct(@RequestParam(value = "limit", defaultValue = "4") int limit) {
        return productService.getRandomProducts(limit);
    }
    


}
