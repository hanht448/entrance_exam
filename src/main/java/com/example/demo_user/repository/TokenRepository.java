package com.example.demo_user.repository;

import com.example.demo_user.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    TokenEntity findByUserId(Integer userId);
    List<TokenEntity> findAllByRefreshTokenIsNotNull();
    List<TokenEntity> findByRefreshToken(String refreshToken);
}
