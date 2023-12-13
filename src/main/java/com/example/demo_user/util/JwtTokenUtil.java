package com.example.demo_user.util;

import com.example.demo_user.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtil {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String createJwtToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());

        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Timestamp.valueOf(expiresAt))
                .signWith(KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public static String createJwtTokenWithoutUser() {
        return Jwts.builder().signWith(KEY, SignatureAlgorithm.HS512).compact();
    }
}
