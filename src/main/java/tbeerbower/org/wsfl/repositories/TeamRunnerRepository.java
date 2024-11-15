package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.TeamRunner;

public interface TeamRunnerRepository extends JpaRepository<TeamRunner, Integer> {
}
