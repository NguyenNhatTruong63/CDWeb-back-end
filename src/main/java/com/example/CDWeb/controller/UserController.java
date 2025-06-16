package com.example.CDWeb.controller;

import com.example.CDWeb.model.*;
import com.example.CDWeb.repository.RoleRepository;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.TokenService;
import com.example.CDWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleRepository roleRepository;
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

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user,@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }
        try {
            userService.register(user);
            return ResponseEntity.ok("Đăng ký thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/add2")
    public ResponseEntity<?> addUser2(@RequestBody UserRequest request,
                                      @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ hoặc thiếu.");
        }

        String token = authorizationHeader.substring(7);
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ.");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setEmail(request.getEmail());
        newUser.setNumberphone(request.getNumberphone());
        newUser.setAddress(request.getAddress());

        Set<Role> roles = request.getRoles().stream().map(roleName -> {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                throw new RuntimeException("Không tìm thấy role: " + roleName);
            }
            return role;
        }).collect(Collectors.toSet());

        newUser.setRoles(roles);

        try {
            userService.addUser(newUser);
            return ResponseEntity.ok(new UserReponse(newUser));
        } catch (IllegalArgumentException e) {
            // Nếu service ném lỗi, trả về 409 Conflict và thông báo lỗi
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
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
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7);
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Tìm user theo ID
       User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

       // Cập nhật thông tin
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setNumberphone(request.getNumberphone());
        user.setAddress(request.getAddress());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword()); // nên mã hoá nếu cần
        }

        // Cập nhật roles nếu có
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = request.getRoles().stream().map(roleName -> {
                Role role = roleRepository.findByName(roleName);
                if (role == null) {
                    throw new RuntimeException("Không tìm thấy role: " + roleName);
                }
                return role;
            }).collect(Collectors.toSet());
            user.setRoles(roles);
        }

        // Lưu lại

        try {
            userService.saveUser(user);
            return ResponseEntity.ok(new UserReponse(user));
        } catch (IllegalArgumentException e) {
            // Nếu service ném lỗi, trả về 409 Conflict và thông báo lỗi
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Lỗi");
        }
    }


}
