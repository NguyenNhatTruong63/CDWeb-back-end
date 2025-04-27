package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.controller.JwtUtil;
import com.example.CDWeb.model.Token;
import com.example.CDWeb.repository.TokenRepository;
import com.example.CDWeb.repository.UserRepository;
import com.example.CDWeb.service.TokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Transactional
    public void deleteByUsername(String username) {
        tokenRepository.deleteByUsername(username);
    }

    @Override
    public boolean isTokenValid(String token) {
        Token storedToken = tokenRepository.findByToken(token);
        if (storedToken != null && !jwtUtil.isTokenExpired(storedToken.getToken())) {
            return true;
        }
        return false;
    }

    @Override
    public void saveToken(Token token) {
      tokenRepository.save(token);
    }
}
