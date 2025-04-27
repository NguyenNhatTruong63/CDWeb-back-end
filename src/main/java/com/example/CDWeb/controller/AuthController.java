package com.example.CDWeb.controller;


import com.example.CDWeb.model.LoginRequest;
import com.example.CDWeb.model.Token;
import com.example.CDWeb.model.User;
import com.example.CDWeb.repository.TokenRepository;
import com.example.CDWeb.service.TokenService;
import com.example.CDWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.CDWeb.serviceImpl.UserServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());

            Token newToken = new Token(user.getUsername(), token, new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
            tokenService.saveToken(newToken);
            // Tạo response chứa thông tin user và token
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Đăng nhập thành công");
            response.put("token", token);

            // Thêm user vào response, chỉ những thông tin cần thiết
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            // thêm thông tin khác nếu cần

            response.put("user", userInfo);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai username hoặc password");
        }
    }


//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
//        // Kiểm tra token hợp lệ trước khi logout
//        if (!tokenService.isTokenValid(token)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(token);
//        }
//
//        // Xóa token khỏi cơ sở dữ liệu
//        tokenService.deleteByUsername(jwtUtil.extractUsername(token)); // Cần một hàm để trích xuất username từ token
//
//        return ResponseEntity.ok(Map.of("message", "Đăng xuất thành công"));
//    }
@PostMapping("/logout")
public ResponseEntity<?> logout(@RequestParam String username) {
    tokenService.deleteByUsername(username);
    return ResponseEntity.ok(Map.of("message", "Đăng xuất thành công"));
}
}
