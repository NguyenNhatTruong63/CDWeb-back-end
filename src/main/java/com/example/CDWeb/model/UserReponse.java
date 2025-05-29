package com.example.CDWeb.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserReponse {
    private Long id;
    private String username;
    private String email;
    private String numberphone;
    private String address;
    private List<String> roles;

    // Constructors
    public UserReponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.numberphone = user.getNumberphone();
        this.address = user.getAddress();

        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
// Getters and setters (hoặc dùng Lombok @Getter @Setter nếu bạn có)
}
