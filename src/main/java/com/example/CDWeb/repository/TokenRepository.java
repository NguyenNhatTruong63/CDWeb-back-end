package com.example.CDWeb.repository;

import com.example.CDWeb.model.Product;
import com.example.CDWeb.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
    void deleteByUsername(String username);
}
