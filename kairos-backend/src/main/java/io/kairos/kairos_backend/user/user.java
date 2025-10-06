package io.kairos.kairos_backend.user;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class user {

    @Id // indicates the primary key
    @GeneratedValue(strategy=GenerationType.AUTO) // generates the id automatically
    private long id;
    @Column(unique=true, nullable = false, length = 255)
    private String userName;
    @Column(unique=true, nullable = false, length = 255)
    private String email;
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected user() {};

    public user(String userName) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.createdAt = Instant.now();
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, userName='%s']", id, userName);
    }

    // getters
    public long getId() {return id;}
    public String getEmail() {return email;}
    public String getPasswordHash() {return passwordHash;}
    public Instant getCreatedAt() {return createdAt;}
    public String getUserName() {return userName;}

    // setters
    public void setEmail(String email) {this.email = email;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}

}
