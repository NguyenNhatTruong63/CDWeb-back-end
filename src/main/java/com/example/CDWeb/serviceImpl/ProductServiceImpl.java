package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.CartItem;
import com.example.CDWeb.model.Category;
import com.example.CDWeb.model.Product;
import com.example.CDWeb.repository.CartItemRepository;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Product> getAllProductsNotNull() {
        return productRepository.findByCategoryIsNotNull(); // hoặc .findAllWithCategory()
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

    @Override
    public void detachCategoryFromProducts(Category category) {
        List<Product> products = category.getProducts();
        for (Product product : products) {
            product.setCategory(null); // bỏ liên kết với danh mục
        }
        productRepository.saveAll(products); // cập nhật lại DB
    }
    @Transactional
    public void deleteProductById(int id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) throw new RuntimeException("Product not found");

        Product product = optional.get();

        // Xóa tất cả CartItem chứa Product này
        cartItemRepository.deleteByProduct(product);

        // Xóa liên kết size và color (nếu có dùng ManyToMany)
        product.getSizes().clear();
        product.getColors().clear();
        productRepository.save(product);

        // Xóa Product
        productRepository.delete(product);
    }
}
