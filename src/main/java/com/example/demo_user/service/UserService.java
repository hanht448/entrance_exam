package com.example.demo_user.service;

import com.example.demo_user.dto.UserResponseDTO;
import com.example.demo_user.entity.UserEntity;


public interface UserService {
    UserResponseDTO signUpUser(String email, String password, String firstName, String lastName);

    UserEntity authenticateUser(String email, String password);

    void saveRefreshToken(int id, String refreshToken);

    void removeRefreshTokens();
}