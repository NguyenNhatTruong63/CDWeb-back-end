package com.example.CDWeb.controller;

import com.example.CDWeb.model.*;
import com.example.CDWeb.repository.CategoryRepository;
import com.example.CDWeb.repository.TokenRepository;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.CategoryService;
import com.example.CDWeb.service.ProductService;
import com.example.CDWeb.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProductService productService;
    @GetMapping
    public List<CategoryResponse> getAllCategory() {
        return categoryService.getAllCategory().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getCategory()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) {
        // Kiểm tra xem header có chứa token hay không
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }

        // Nếu token hợp lệ, gọi dịch vụ và trả về Category
        Category category = categoryService.getCategoryById(id);

        // Kiểm tra nếu category tồn tại, nếu không trả về NotFound
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Trả về category và mã trạng thái OK (200)
        return ResponseEntity.ok(category);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CategoryRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }

        Category category = new Category();

        category.setCategory(request.getCategory());
        try {
            categoryService.addCategory(category);

            return ResponseEntity.ok(new CategoryResponse(category));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    @DeleteMapping("/delete/{categoryName}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryName, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token");
        }

        String token = authorizationHeader.substring(7);
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }

        Category category = categoryService.getCategoryByName(categoryName);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục");
        }


        // 1. Set category của các product về null trước
        for (Product product : category.getProducts()) {
            product.setCategory(null);
        }


        productService.detachCategoryFromProducts(category);

        // 3. Xóa category
        categoryService.deleteCategory(category);

        return ResponseEntity.ok("Xóa danh mục thành công");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id,
                                            @RequestBody CategoryRequest request,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token");
        }

        String token = authorizationHeader.substring(7);
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }

        try {
            Category updated = categoryService.updateCategory(id, request.getCategory());
            return ResponseEntity.ok(new CategoryResponse(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
