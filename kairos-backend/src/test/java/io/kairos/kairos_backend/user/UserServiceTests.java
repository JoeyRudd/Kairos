package io.kairos.kairos_backend.user;

import io.kairos.kairos_backend.user.dto.UserRequestDTO;
import io.kairos.kairos_backend.user.dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// enabke mockito for JUnit 5
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    // mock the user repository
    @Mock
    private UserRepository userRepository;

    // inject mocks into the user service
    @InjectMocks
    private UserService userService;

    private User testUser1, testUser2, testUser3;
    private UserRequestDTO testUserRequest1, testUserRequest2, testUserRequest3;
    private UserResponseDTO testUserResponse1, testUserResponse2, testUserResponse3;

    @BeforeEach
    void setup(){
        // setup test users
        testUser1 = createTestUser(1L, "user1@gmail.com", "pass123");
        testUser2 = createTestUser(2L, "user2@gmail.com", "pass123");
        testUser3 = createTestUser(3L, "user3@gmail.com", "pass123");

        // setup test user requests
        testUserRequest1 = createTestUserRequest("user1@gmail.com", "pass123");
        testUserRequest2 = createTestUserRequest("user2@gmail.com", "pass123");
        testUserRequest3 = createTestUserRequest("user3@gmail.com", "pass123");

        // setup test user responses
        testUserResponse1 = createTestUserResponse(1L, "user1@gmail.com", testUser1.getCreatedAt());
        testUserResponse2 = createTestUserResponse(2L, "user2@gmail.com", testUser2.getCreatedAt());
        testUserResponse3 = createTestUserResponse(3L, "user3@gmail.com", testUser3.getCreatedAt());

    }

    @Test
    void createUser_WhenUserIsValid_ShouldReturnCreatedUser() {
        // arrange - mock the repository to return the test user
        when(userRepository.save(any(User.class))).thenReturn(testUser1);

        // act - call the create user method
        UserResponseDTO createdUser = userService.createUser(testUserRequest1);

        // assert - verify the user was created and returned correctly
        assertNotNull(createdUser);
        assertEquals(testUserResponse1.getEmail(), createdUser.getEmail());
        assertEquals(testUserResponse1.getID(), createdUser.getID());
        assertNotNull(createdUser.getCreatedAt());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void convertToResponseDTO_WhenUserIsValid_ShouldReturnCorrectResponseDTO(){
        // arrange
        UserResponseDTO expectedResponse = testUserResponse1;

        // act
        UserResponseDTO actualResponse = userService.convertToResponseDTO(testUser1);

        // assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getID(), actualResponse.getID());
        assertNotNull(actualResponse.getCreatedAt());
    }

    @Test
    public void testPasswordHashing(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "pass123";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // verify
        assertTrue(passwordEncoder.matches(rawPassword, hashedPassword));
        assertFalse(passwordEncoder.matches("wrongPassword", hashedPassword));
    }

    private User createTestUser(Long id, String email, String password){
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setCreatedAt(java.time.Instant.now());
        return user;
    }

    private UserRequestDTO createTestUserRequest(String email, String password){
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        return userRequest;
    }
    private UserResponseDTO createTestUserResponse(Long id, String email, Instant createdAt){
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setID(id);
        userResponse.setEmail(email);
        userResponse.setCreatedAt(createdAt);
        return userResponse;
    }





}
