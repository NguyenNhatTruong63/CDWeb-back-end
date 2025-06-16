package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Cart;
import com.example.CDWeb.model.CartItem;
import com.example.CDWeb.model.Product;
import com.example.CDWeb.repository.CartItemRepository;
import com.example.CDWeb.repository.CartRepository;
import com.example.CDWeb.repository.CategoryRepository;
import com.example.CDWeb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(int id) {
        return null;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public List<CartItem> findByCartId(Long cartId){
      return   cartItemRepository.findByCartId(cartId);
    }

    @Override
    public CartItem findByCartIdAndProductIdAndSizeIdAndColorId(Long cartId, int productId, Long sizeId, Long colorId) {
        return cartItemRepository.findByCartIdAndProductIdAndSizeIdAndColorId(cartId, productId, sizeId, colorId)  ;
    }

    @Override
    public void saveItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
    @Override
    public void deleteCartItemsByProduct(Product product) {
        cartItemRepository.deleteByProduct(product);
    }

}
