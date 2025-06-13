package com.example.CDWeb.service;


import com.example.CDWeb.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {
    void register(User user);
    void deleteUserById(long id);
    User findByUsername(String username);
    User createUser(User user);
    List<User> getAllUser();
    User getUserById(String id);
}
