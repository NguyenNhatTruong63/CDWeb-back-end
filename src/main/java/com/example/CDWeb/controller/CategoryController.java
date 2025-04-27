package com.example.CDWeb.controller;

import com.example.CDWeb.model.Category;
import com.example.CDWeb.model.User;
import com.example.CDWeb.repository.CategoryRepository;
import com.example.CDWeb.repository.TokenRepository;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.CategoryService;
import com.example.CDWeb.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
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


}
