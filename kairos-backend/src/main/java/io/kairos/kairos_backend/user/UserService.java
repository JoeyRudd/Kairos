package io.kairos.kairos_backend.user;

import io.kairos.kairos_backend.user.dto.UserRequestDTO;
import io.kairos.kairos_backend.user.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserRequestDTO userRequest) {
        // create a new user entity
        User user = new User();
        user.setEmail(userRequest.getEmail());
        // handle password
        user.setPasswordHash(userRequest.getPassword());
        user.setCreatedAt(Instant.now());
        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    public UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setID(user.getId());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }

    Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
