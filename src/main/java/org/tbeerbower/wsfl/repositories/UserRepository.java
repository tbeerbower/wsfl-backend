package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
