package com.example.CDWeb.service;


import com.example.CDWeb.model.Role;
import com.example.CDWeb.model.User;

import java.util.List;
import java.util.Optional;

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
    Optional<User> findByEmail2(String email);
    boolean resetPassword(String username, String newPassword);
    boolean resetPasswordByEmail(String email, String newPassword);
}
