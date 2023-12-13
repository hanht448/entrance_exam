package com.example.demo_user.entity;


import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @Column(name = "refreshToken", length = 250, nullable = false)
    private String refreshToken;

    @Column(name = "expiresIn", length = 64)
    private String expiresIn;


    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
