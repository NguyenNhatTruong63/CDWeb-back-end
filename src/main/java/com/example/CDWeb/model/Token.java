package com.example.CDWeb.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String token;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    // Constructor, getters, and setters
    public Token() {
    }

    public Token(String username, String token, Date expirationDate) {
        this.username = username;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
