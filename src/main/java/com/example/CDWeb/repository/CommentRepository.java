package com.example.CDWeb.repository;

import com.example.CDWeb.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProduct_Id(int productId); // Tìm theo product.id
    void deleteById(Long id);
    boolean existsById(Long id);
}
