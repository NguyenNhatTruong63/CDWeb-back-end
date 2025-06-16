package com.example.CDWeb.service;


import com.example.CDWeb.model.Role;
import com.example.CDWeb.model.User;

import java.util.List;

public interface UserService {
    void register(User user);
    void deleteUserById(long id);
    User findByUsername(String username);
    User createUser(User user);
    List<User> getAllUser();
    User getUserById(String id);
    User findById(long id);
    List<Role> getRolesFromNames(List<String> roleNames);
    boolean  existsByUsername(String username);
    void addUser(User user);
    void saveUser(User user);
}
