package io.kairos.kairos_backend.user;

import io.kairos.kairos_backend.user.dto.UserRequestDTO;
import io.kairos.kairos_backend.user.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserRequestDTO userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // create a new user entity
        User user = new User();
        user.setEmail(userRequest.getEmail());
        // handle password
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPasswordHash(hashedPassword);

        user.setCreatedAt(Instant.now());
        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    // method to veify password during login
    public boolean verifyPassword(String rawPassowrd, String hashedPassword) {
        return passwordEncoder.matches(rawPassowrd, hashedPassword);
    }

    public UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setID(user.getId());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
