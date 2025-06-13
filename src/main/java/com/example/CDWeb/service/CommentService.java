package com.example.CDWeb.service;

import com.example.CDWeb.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByProductId(Long productId);
    Comment addComment(Comment comment, Long productId);
}
