package com.example.CDWeb.controller;

import com.example.CDWeb.model.*;
import com.example.CDWeb.repository.ColorRepository;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.repository.SizeRepository;
import com.example.CDWeb.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private ColorService colorService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
    @GetMapping("/notnull")
    public List<Product> getAllProductsNotNull() {
        return productService.getAllProductsNotNull();
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



    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("query") String key) {
        return productService.searchProduct(key);
    }

    @GetMapping("/random")
    public List<Product> getRandomProduct(@RequestParam(value = "limit", defaultValue = "4") int limit) {
        return productService.getRandomProducts(limit);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable int id,
            @RequestBody ProductRequest request,
            @RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token hoặc token không hợp lệ");
        }

        String token = authorizationHeader.substring(7);
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm");
        }

        Product product = optionalProduct.get();
        product.setName(request.name);
        product.setPrice(request.price);
        product.setDescription(request.description);

        // Gán danh mục
        Category category = categoryService.getCategoryByName(request.category);
        if (category == null) {
            return ResponseEntity.badRequest().body("Danh mục không tồn tại");
        }
        product.setCategory(category);

        // Tìm Size theo value (kiểu int)
        List<Size> sizeList = sizeService.getSizesByValues(request.sizes);
        if (sizeList.size() != request.sizes.size()) {
            return ResponseEntity.badRequest().body("Một hoặc nhiều size không hợp lệ");
        }
        product.setSizes(sizeList);

        // Tìm Color theo name
        List<Color> colorList = colorService.getColorsByNames(request.colors);
        if (colorList.size() != request.colors.size()) {
            return ResponseEntity.badRequest().body("Một hoặc nhiều màu không hợp lệ");
        }
        product.setColors(colorList);

        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id,@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token hoặc token không hợp lệ");
        }

        String token = authorizationHeader.substring(7);
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok("Xóa sản phẩm thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
