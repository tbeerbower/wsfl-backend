package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.Race;

public interface RaceRepository extends JpaRepository<Race, Integer> {
}
