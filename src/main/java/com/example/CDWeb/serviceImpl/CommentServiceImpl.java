package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Comment;
import com.example.CDWeb.model.Product;
import com.example.CDWeb.repository.CommentRepository;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Comment> getCommentsByProductId(Long productId) {
        return commentRepository.findByProduct_Id(productId);
    }

    @Override
    public Comment addComment(Comment comment, Long productId) {
        Product product = productRepository.findById(productId.intValue())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        comment.setProduct(product);
        return commentRepository.save(comment);
    }
}

