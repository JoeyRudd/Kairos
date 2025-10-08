package io.kairos.kairos_backend.user;

import org.junit.jupiter.api.BeforeEach;
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

    // test users
    private User testUser1;
    private User testUser2;
    private User testUser3;

    // generate test users
    @BeforeEach
    void setup(){
        // create common data for tests
        testUser1 = CreateTestUser("user1@gmail.com");
        testUser2 = CreateTestUser("user2@gmail.com");
        testUser3 = CreateTestUser("user3@gmail.com");
        entityManager.persistAndFlush(testUser1);
        entityManager.persistAndFlush(testUser2);
        entityManager.persistAndFlush(testUser3);
    }

    @Test
    void findUserByEmail_WhenUserExists_ShouldReturnUser(){
        // call method to find the user by email
        Optional<User> foundUser = userRepository.findUserByEmail(testUser1.getEmail());

        // assert that the user was found
        assertTrue(foundUser.isPresent());
        assertEquals(testUser1.getId(), foundUser.get().getId());
        assertEquals(foundUser.get().getEmail(), testUser1.getEmail());
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
        // call method to check if the user exists by email
        Boolean exists = userRepository.existsByEmail(testUser1.getEmail());

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
