package com.example.demo_user.service;

import com.example.demo_user.entity.TokenEntity;
import com.example.demo_user.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo_user.util.JwtTokenUtil.createJwtTokenWithoutUser;


@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    public boolean isValidRefreshToken(String refreshToken) {
        List<TokenEntity> matchingTokens = tokenRepository.findByRefreshToken(refreshToken);
        System.out.println("matchingTokens : " + matchingTokens);
        return !matchingTokens.isEmpty();
    }

    public String generateRefreshToken() {
        return createJwtTokenWithoutUser();
    }

    public void invalidateRefreshToken(String oldRefreshToken, String newRefreshToken) {
        List<TokenEntity> matchingTokens = tokenRepository.findByRefreshToken(oldRefreshToken);

        for (TokenEntity tokenEntity : matchingTokens) {
            tokenEntity.setRefreshToken(newRefreshToken);
            tokenRepository.save(tokenEntity);
        }
    }
}
