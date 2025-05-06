package com.example.CDWeb.service;

import com.example.CDWeb.model.Cart;
import com.example.CDWeb.model.CartItem;

import java.util.List;

public interface CartService {
    Cart getCartById(int id);
    Cart getCartByUserId(Long userId);
    List<CartItem> findByCartId(Long cartId);
}
