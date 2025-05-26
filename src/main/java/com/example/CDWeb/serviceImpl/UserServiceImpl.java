package com.example.CDWeb.serviceImpl;


import com.example.CDWeb.model.Cart;
import com.example.CDWeb.model.User;
import com.example.CDWeb.repository.CartRepository;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void register(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("register.email");
        }else if(userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("register.username");

        }else{
            userRepository.save(user);
            User savedUser = userRepository.save(user);

            Cart cart = new Cart();
            cart.setUserId(savedUser.getId()); // Gắn userId
            cartRepository.save(cart);
        }


    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(Long.valueOf(id)).orElse(null);
    }

}