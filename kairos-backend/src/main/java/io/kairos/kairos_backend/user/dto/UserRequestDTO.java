package io.kairos.kairos_backend.user.dto;

public class UserRequestDTO {
    private String email;
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
