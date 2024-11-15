package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.Season;

public interface SeasonRepository extends JpaRepository<Season, Integer> {
}
