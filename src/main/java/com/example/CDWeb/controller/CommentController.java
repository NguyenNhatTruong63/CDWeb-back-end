package com.example.CDWeb.controller;

import com.example.CDWeb.model.*;
import com.example.CDWeb.service.CommentService;
import com.example.CDWeb.service.ProductService;
import com.example.CDWeb.service.TokenService;
import com.example.CDWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*")           // Cho phép frontend gọi từ mọi domain
@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request,@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }
        User user = userService.findById(request.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Product product = productService.getProductById(request.getProductId());
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setProduct(product);
        comment.setCommentText(request.getCommentText());
        comment.setRating(request.getRating());
        Comment saved = commentService.addComment(comment);
        CommentResponse commentResponse = new CommentResponse(
                saved.getId(),
                saved.getUser().getUsername(),
                saved.getProduct().getId(),
                saved.getCommentText(),
                saved.getRating(),
                saved.getCreatedAt()
        );
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable int productId) {
        List<Comment> comments = commentService.getCommentsByProductId(productId);
        comments.sort((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()));

        List<CommentResponse> response = comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getUser().getUsername(),
                        comment.getProduct().getId(),
                        comment.getCommentText(),
                        comment.getRating(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
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
            commentService.deleteCommentById(id);
            return ResponseEntity.ok().build(); // HTTP 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Không có token hoặc token không hợp lệ
        }

        // Lấy token từ header
        String token = authorizationHeader.substring(7); // Loại bỏ phần "Bearer " ở đầu

        // Kiểm tra token trước khi thực hiện các thao tác
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Token không hợp lệ
        }
        Comment comment = commentService.getCommentById(id);
        if (comment == null) return ResponseEntity.notFound().build();

        comment.setCommentText(request.getCommentText());
        comment.setRating(request.getRating());

        Comment updated = commentService.updateComment(comment);
        CommentResponse response = new CommentResponse(
                updated.getId(),
                updated.getUser().getUsername(),
                updated.getProduct().getId(),
                updated.getCommentText(),
                updated.getRating(),
                updated.getCreatedAt()
        );
        return ResponseEntity.ok(response);
    }

}

