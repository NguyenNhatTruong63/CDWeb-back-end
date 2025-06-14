package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Comment;
import com.example.CDWeb.model.Product;
import com.example.CDWeb.model.User;
import com.example.CDWeb.repository.CommentRepository;
import com.example.CDWeb.repository.ProductRepository;
import com.example.CDWeb.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Comment> getCommentsByProductId(int productId) {
        return commentRepository.findByProduct_Id(productId);
    }

    @Override
    public Comment addComment(Comment comment) {

        return commentRepository.save(comment);
    }
    @Override
    public void deleteCommentById(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
    @Override
    public Comment updateComment(Comment updatedComment) {
        Comment existing = commentRepository.findById(updatedComment.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + updatedComment.getId()));

        existing.setCommentText(updatedComment.getCommentText());
        existing.setRating(updatedComment.getRating());
        // Không nên cập nhật lại user/product vì đây là chỉnh sửa nội dung thôi

        return commentRepository.save(existing);
    }
}

