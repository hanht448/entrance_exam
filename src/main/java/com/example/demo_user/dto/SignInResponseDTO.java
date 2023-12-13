package com.example.demo_user.dto;

public class SignInResponseDTO {
    private UserResponseDTO user;
    private String token;
    private String refreshToken;


    public SignInResponseDTO(UserResponseDTO userEntity, String token, String refreshToken) {
        this.user = new UserResponseDTO(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getFirstName() + " " + userEntity.getLastName()
        );
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public SignInResponseDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}

