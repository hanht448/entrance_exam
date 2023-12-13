package com.example.demo_user.dto;

public class RefreshTokenResponseDTO {
    private String token;
    private String refreshToken;

    public RefreshTokenResponseDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
