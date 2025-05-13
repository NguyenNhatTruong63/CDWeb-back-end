package com.example.CDWeb.controller;

import com.example.CDWeb.model.Color;
import com.example.CDWeb.model.Product;
import com.example.CDWeb.model.ProductResponse;
import com.example.CDWeb.model.Size;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

//    @GetMapping("/{id}")
//    public Product getProductById(@PathVariable Integer id) {
//        return productService.getProductById(id);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id); // hoặc productRepo.findById(id).orElseThrow()
        ProductResponse response = convertToResponse(product) ;
        return ResponseEntity.ok(response);
    }
    public ProductResponse convertToResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setImg(product.getImg());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory().getCategory());
        dto.setAdditionalImages(product.getAdditionalImages());

        // Lấy danh sách size (bao gồm id và name)
        List<Map<String, Object>> sizes = product.getSizes()
                .stream()
                .map(size -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", size.getId());
                    map.put("value", size.getValue()); // hoặc size.getName() nếu có
                    return map;
                })
                .collect(Collectors.toList());
        dto.setSizes(sizes); // nhớ chỉnh kiểu trong ProductResponse

        // Lấy danh sách màu (bao gồm id và name)
        List<Map<String, Object>> colors = product.getColors()
                .stream()
                .map(color -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", color.getId());
                    map.put("name", color.getName());
                    return map;
                })
                .collect(Collectors.toList());
        dto.setColors(colors); // nhớ chỉnh kiểu trong ProductResponse

        return dto;
    }

}
