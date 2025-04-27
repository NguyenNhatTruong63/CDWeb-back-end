package com.example.CDWeb.controller;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 tiếng
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean validateToken(String token) {
        try {
            // Logic validate token (kiểm tra token hợp lệ, chưa hết hạn, v.v...)
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date()); // Kiểm tra token có hết hạn chưa
        } catch (JwtException e) {
            return false; // Token không hợp lệ
        }
    }

    public String decodeJwt(String token) {
        // Thay thế các ký tự Base64Url thành Base64 chuẩn
        String base64Url = token.replace("-", "+").replace("_", "/");

        // Giải mã Base64
        byte[] decodedBytes = Base64.getDecoder().decode(base64Url);
        return new String(decodedBytes);
    }

    public static void main(String[] args) {
        String username = "Tam";
        JwtUtil jwtUtil = new JwtUtil();
        String token =   jwtUtil.generateToken(username);
        String username2 = jwtUtil.extractUsername(token);
        System.out.println(username2);
    }

}
