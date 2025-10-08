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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_WithValidUser_ShouldReturnCreatedUser() {
        // set up the test data
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("test@testing.com");
        userRequest.setPassword("test123");

        // set up the expected result
        UserResponseDTO expectedUser = new UserResponseDTO();
        expectedUser.setID(1L);
        expectedUser.setEmail("test@testing.com");

        // when the createUser method is called, return the expectedUser
        when(userService.createUser(userRequest)).thenReturn(expectedUser);

        // execute the method
        ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);

        // verify that the expectedUser was returned
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
        verify(userService).createUser(userRequest);
    }



    
}
