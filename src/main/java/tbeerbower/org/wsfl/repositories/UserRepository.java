package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
