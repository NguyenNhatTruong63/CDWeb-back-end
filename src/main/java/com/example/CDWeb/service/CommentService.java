package com.example.CDWeb.service;

import com.example.CDWeb.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByProductId(int productId);
    Comment addComment(Comment comment);
    void deleteCommentById(Long id);
    Comment getCommentById(Long id);
    Comment updateComment(Comment comment); // <- Thêm dòng này
}
