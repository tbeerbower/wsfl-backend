package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
