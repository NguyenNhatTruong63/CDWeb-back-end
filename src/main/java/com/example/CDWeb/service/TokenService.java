package com.example.CDWeb.service;

import com.example.CDWeb.model.Token;
import com.example.CDWeb.model.User;

import java.util.Optional;

public interface TokenService {
    Token findByToken(String token);
    void deleteByUsername(String username);
    boolean isTokenValid(String token) ;
    void saveToken(Token token);
}
