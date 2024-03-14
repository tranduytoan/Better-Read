package dbmsforeveread.foreveread.repository;

import dbmsforeveread.foreveread.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}