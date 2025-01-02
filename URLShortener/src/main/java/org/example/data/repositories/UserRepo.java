package org.example.data.repositories;

import org.example.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
}
