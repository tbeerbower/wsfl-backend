package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);
    Optional<User> findByEmail(String email);
}
