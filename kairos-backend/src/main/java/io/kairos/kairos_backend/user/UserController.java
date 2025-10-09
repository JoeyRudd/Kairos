package io.kairos.kairos_backend.user;
import io.kairos.kairos_backend.user.dto.UserResponseDTO;
import io.kairos.kairos_backend.user.dto.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO createdUser = userService.registerUser(userRequestDTO);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
        return userService.findUserById(id)
                .map(user -> ResponseEntity.ok(userService.convertToResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers().stream()
                .map(userService::convertToResponseDTO)
                .toList();
        return ResponseEntity.ok(users);
    }





}
