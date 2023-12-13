package com.example.demo_user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.xml.crypto.Data;
import java.util.Date;
import java.time.LocalDateTime;

public class UserResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String displayName;


    // Constructors
    public UserResponseDTO() {
        // Default constructor
    }

    public UserResponseDTO(String firstName, String lastName, String email, String displayName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}