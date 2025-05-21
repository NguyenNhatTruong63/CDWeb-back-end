package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Product;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> searchProduct(String key) {
        if(key == null || key.isEmpty()) return productRepository.findAll();
        return productRepository.findByNameContainingIgnoreCase(key);
    }

    @Override
    public List<Product> getRandomProducts(int limit) {
        return productRepository.findRandomProducts(limit);
    }
}
