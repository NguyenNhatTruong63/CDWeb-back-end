package com.example.CDWeb.controller;


import com.example.CDWeb.model.*;
import com.example.CDWeb.repository.TokenRepository;
import com.example.CDWeb.service.CartService;
import com.example.CDWeb.service.TokenService;
import com.example.CDWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.CDWeb.serviceImpl.UserServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("Đăng ký thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());

            Token newToken = new Token(user.getUsername(), token, new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
            tokenService.saveToken(newToken);
            // Tạo response chứa thông tin user và token


            Cart cart = cartService.getCartByUserId(user.getId());
            List<CartItem> cartItems = cartService.findByCartId(cart.getId());

            // Map sản phẩm từ cartItem
            List<Map<String, Object>> products = cartItems.stream().map(item -> {
                Map<String, Object> productInfo = new HashMap<>();
                Product product = item.getProduct();
                Size size = item.getSize();
                Color color = item.getColor();

                productInfo.put("id", product.getId());
                productInfo.put("name", product.getName());
                productInfo.put("quantity", item.getQuantity());
                productInfo.put("img", product.getImg());
                productInfo.put("price", product.getPrice());
                // Size
                Map<String, Object> sizeInfo = new HashMap<>();
                sizeInfo.put("id", size.getId());
                sizeInfo.put("value", size.getValue()); // ví dụ name: "42" hoặc "XL"
                productInfo.put("size", sizeInfo);

                // Color
                Map<String, Object> colorInfo = new HashMap<>();
                colorInfo.put("id", color.getId());
                colorInfo.put("name", color.getName()); // ví dụ: "Red", "Black"
                productInfo.put("color", colorInfo);

                return productInfo;
            }).toList();

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
            response.put("cart", Map.of(
                    "idUser", user.getId(),
                    "products", products
            ));
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
