package com.example.CDWeb.service;

import com.example.CDWeb.model.Cart;
import com.example.CDWeb.model.CartItem;
import com.example.CDWeb.model.Product;

import java.util.List;

public interface CartService {
    void saveCart(Cart cart);
    Cart getCartById(int id);
    Cart getCartByUserId(Long userId);
    List<CartItem> findByCartId(Long cartId);
    CartItem findByCartIdAndProductIdAndSizeIdAndColorId(Long cartId, int productId, Long sizeId, Long colorId);
    void saveItem(CartItem cartItem);
    void deleteItem(CartItem cartItem);
    void deleteCartItemsByProduct(Product product);
}
