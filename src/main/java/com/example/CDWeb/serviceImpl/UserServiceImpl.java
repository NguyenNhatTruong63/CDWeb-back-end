package com.example.CDWeb.serviceImpl;


import com.example.CDWeb.model.Cart;
import com.example.CDWeb.model.Role;
import com.example.CDWeb.model.User;
import com.example.CDWeb.repository.CartRepository;
import com.example.CDWeb.repository.RoleRepository;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("register.email");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("register.username");
        } else {
            Role userRole = roleRepository.findByName("USER");
            user.setRoles(Set.of(userRole));

            User savedUser = userRepository.save(user);

            Cart cart = new Cart();
            cart.setUserId(savedUser.getId());
            cartRepository.save(cart);
        }
    }
    @Override
    public void addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("register.email");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("register.username");
        } else {
            User savedUser = userRepository.save(user);

            Cart cart = new Cart();
            cart.setUserId(savedUser.getId());
            cartRepository.save(cart);
        }
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public void deleteUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        userRepository.delete(user);
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

    @Override
    public User findById(long id) {
        return userRepository.findById(Long.valueOf(id)).orElse(null);
    }
    public List<Role>  getRolesFromNames(List<String> roleNames) {
        return roleNames.stream()
                .map(name -> {
                    Role role = roleRepository.findByName(name);
                    if (role == null) {
                        throw new RuntimeException("Không tìm thấy role: " + name);
                    }
                    return role;
                })
                .collect(Collectors.toList());
    }
    @Override
    public  boolean  existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    @Override
    public Optional<User> findByEmail2(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public boolean resetPassword(String username, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = optionalUser.get();

        if (user.getPassword().equals(newPassword)) {
            return false; // Mật khẩu mới trùng mật khẩu cũ
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return true;
    }
    public boolean resetPasswordByEmail(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy user");
        }

        User user = userOptional.get();
        if (user.getPassword().equals(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu mới trùng với mật khẩu cũ!");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

}