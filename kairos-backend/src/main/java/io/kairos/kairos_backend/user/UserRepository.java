package io.kairos.kairos_backend.user;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Boolean existsBy(String email);
}
