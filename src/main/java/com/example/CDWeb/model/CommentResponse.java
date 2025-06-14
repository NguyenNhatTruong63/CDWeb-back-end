package com.example.CDWeb.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String userName;
    private int productId;
    private String commentText;
    private int rating;
    @JsonFormat(pattern = "dd/MM/yyyy") // định dạng ngày
    private LocalDateTime createdAt;

    public CommentResponse(Long id,String userName, int productId, String commentText, int rating, LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.productId = productId;
        this.commentText = commentText;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
