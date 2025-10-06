package io.kairos.kairos_backend.user.dto;

import io.kairos.kairos_backend.user.User;

import java.time.Instant;

public class UserResponseDTO {
    private Long id;
    private String email;
    private Instant createdAt;

    // default constructor
    public UserResponseDTO() {};

    // constructor with parameters
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }

    // getters
    public Long getID() {return id;}
    public String getEmail() {return email;}
    public Instant getCreatedAt() {return createdAt;}

    // setters
    public void setID(Long id) {this.id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setCreatedAt(Instant createdAt) {this.createdAt = createdAt;}
}
