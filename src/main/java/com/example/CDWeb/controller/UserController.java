package com.example.CDWeb.controller;

import com.example.CDWeb.model.User;
import com.example.CDWeb.model.UserReponse;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.TokenService;
import com.example.CDWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<UserReponse> getAllUser() {
        List<User> users = userService.getAllUser();
        return users.stream()
                .map(UserReponse::new)
                .collect(Collectors.toList());    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
  //  @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu
        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok("Xóa user thành công");
    }

}
