package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.Runner;

public interface RunnerRepository extends JpaRepository<Runner, Integer> {
}
