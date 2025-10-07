package io.kairos.kairos_backend.user;


import jakarta.persistence.*;
import java.time.Instant;

@Table(name = "users")
@Entity
public class User {

    @Id // indicates the primary key
    @GeneratedValue(strategy=GenerationType.AUTO) // generates the id automatically
    private long id;
    @Column(unique=true, nullable = false, length = 255)
    private String email;
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected User() {};

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = Instant.now();
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, email='%s']", id, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    // getters
    public long getId() {return id;}
    public String getEmail() {return email;}
    public String getPasswordHash() {return passwordHash;}
    public Instant getCreatedAt() {return createdAt;}

    // setters
    public void setEmail(String email) {this.email = email;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}
    public void setCreatedAt(Instant createdAt) {this.createdAt = createdAt;}
    public void setId(long id) {this.id = id;}

}
