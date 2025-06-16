package com.example.CDWeb.repository;

import com.example.CDWeb.model.CartItem;
import com.example.CDWeb.model.Color;
import com.example.CDWeb.model.Product;
import com.example.CDWeb.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
    CartItem findByCartIdAndProductIdAndSizeIdAndColorId(Long cartId, int productId, Long sizeId, Long colorId);
    void deleteByProduct(Product product);
}