package io.kairos.kairos_backend.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmail_WhenUserExists_ShouldReturnUser(){
        // create user data
        User user = CreateTestUser("test@123.com");
        User savedUser = entityManager.persistAndFlush(user);

        // call method to find the user by email
        Optional<User> foundUser = userRepository.findUserByEmail(user.getEmail());

        // assert that the user was found
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals(foundUser.get().getEmail(), user.getEmail());
    }

    @Test
    void findUserByEmail_WhenUserDoesNotExists_ShouldReturnEmpty(){
        // call method to find user by email
        Optional<User> foundUser = userRepository.findUserByEmail("mock@123.com");

        // assert that the user was not found
        assertFalse(foundUser.isPresent());
    }

    @Test
    void existsByEmail_WhenUserExists_ShouldReturnTrue(){
        // create user data
        User user = CreateTestUser("test@456.com");
        entityManager.persistAndFlush(user);

        // call method to check if the user exists by email
        Boolean exists = userRepository.existsByEmail(user.getEmail());

        // assert that the user was found
        assertTrue(exists);
    }

    @Test
    void existsByEmail_WhenUserDoesNotExists_ShouldReturnFalse(){
        // call method to check if the user exists by email
        Boolean exists = userRepository.existsByEmail("mock@mail.com");
        assertFalse(exists);
    }

    private User CreateTestUser(String email){
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash("testPassword123");
        user.setCreatedAt(java.time.Instant.now());
        return user;
    }


}
