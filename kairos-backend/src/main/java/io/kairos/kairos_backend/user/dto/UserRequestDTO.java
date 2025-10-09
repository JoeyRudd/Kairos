package io.kairos.kairos_backend.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Min(value = 8, message = "Password must be minimum of 8 characters")
    private String password;

    // default constructor
    public UserRequestDTO() {};

    // constructor with parameters
    public UserRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getters
    public String getEmail() {return email;}
    public String getPassword() {return password;}

    // setters
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
}
