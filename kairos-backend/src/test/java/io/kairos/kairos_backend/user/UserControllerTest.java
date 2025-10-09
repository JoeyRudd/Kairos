package io.kairos.kairos_backend.user;

import io.kairos.kairos_backend.user.dto.UserRequestDTO;
import io.kairos.kairos_backend.user.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void registerUser_WithValidUser_ShouldReturnRegisteredUser() {
        // set up the test data
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("test@testing.com");
        userRequest.setPassword("test123");

        // set up the expected result
        UserResponseDTO expectedUser = new UserResponseDTO();
        expectedUser.setID(1L);
        expectedUser.setEmail("test@testing.com");

        // when the registerUser method is called, return the expectedUser
        when(userService.registerUser(userRequest)).thenReturn(expectedUser);

        // execute the method
        ResponseEntity<UserResponseDTO> response = userController.registerUser(userRequest);

        // verify that the expectedUser was returned
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
        // ensure that the userService was called with the correct userRequest
        verify(userService).registerUser(userRequest);
    }

    @Test
    void getUserByID_WhenUserExists_ShouldReturnUser(){
        // set up the test data
        long userId = 1L;
        UserResponseDTO expectedUser = new UserResponseDTO();
        expectedUser.setID(1L);
        expectedUser.setEmail("test@testing.com");

        // when the getUserById method is called, return the expectedUser
        when(userService.findUserById(userId)).thenReturn(Optional.of(new User()));
        when(userService.convertToResponseDTO(any())).thenReturn(expectedUser);

        // execute the method
        ResponseEntity<UserResponseDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
        // ensure that the userService was called with the correct userId
        verify(userService).findUserById(userId);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers(){
        // setup fake data
        List<User> users = Arrays.asList(
                CreateTestUser(1L, "bill@gmail.com"),
                CreateTestUser(2L, "john@gmail.com"));

        List<UserResponseDTO> expectedUsers = Arrays.asList(
                CreateTestUserResponseDTO(1L, "bill@gmail.com"),
                CreateTestUserResponseDTO(2L, "john@gmail.com"));

        when(userService.findAllUsers()).thenReturn(users);
        when(userService.convertToResponseDTO(any(User.class)))
                .thenReturn(expectedUsers.get(0))
                .thenReturn(expectedUsers.get(1));

        // execute the method
        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();

        // verify that the expectedUsers were returned
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(expectedUsers, response.getBody());
        // ensure that the userService was called
        verify(userService, times(2)).convertToResponseDTO(any());
    }

    private User CreateTestUser(Long id, String email){
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        return user;
    }

    private UserResponseDTO CreateTestUserResponseDTO(Long id, String email){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setID(id);
        userResponseDTO.setEmail(email);
        return userResponseDTO;
    }
    
}


