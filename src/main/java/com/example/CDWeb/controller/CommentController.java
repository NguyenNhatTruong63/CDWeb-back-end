package com.example.CDWeb.controller;

import com.example.CDWeb.model.Comment;
import com.example.CDWeb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "*")           // Cho phép frontend gọi từ mọi domain
@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest request) {
        Comment comment = new Comment();
        comment.setCommentText(request.getCommentText());
        comment.setRating(request.getRating());
        return ResponseEntity.ok(commentService.addComment(comment, request.getProductId()));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long productId) {
        return ResponseEntity.ok(commentService.getCommentsByProductId(productId));
    }

    // DTO nội bộ
    public static class CommentRequest {
        private Long productId;
        private String commentText;
        private int rating;

        // Getters and setters
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getCommentText() {
            return commentText;
        }

        public void setCommentText(String commentText) {
            this.commentText = commentText;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }
}

