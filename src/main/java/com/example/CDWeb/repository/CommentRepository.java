package com.example.CDWeb.repository;

import com.example.CDWeb.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByProduct_Id(Long productId); // Tìm theo product.id

}
