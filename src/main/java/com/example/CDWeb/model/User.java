package com.example.CDWeb.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity  // Đánh dấu đây là một Entity trong database
@Table(name = "users")  // Liên kết với bảng "users" trong DB
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    private Long id;

    @Column(unique = true, nullable = false, length = 50) // Cột "name" không được null, tối đa 50 ký tự
    private String username;

    @Column(unique = true, nullable = false) // Cột "email" phải là duy nhất
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 15)
    private String numberphone;

    @Column(length = 255)
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    // Constructors
    public User() {
    }

    public User(String username, String email, String password, String numberphone, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.numberphone = numberphone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
