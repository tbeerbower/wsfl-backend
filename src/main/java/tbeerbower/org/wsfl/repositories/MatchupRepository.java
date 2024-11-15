package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.Matchup;

public interface MatchupRepository extends JpaRepository<Matchup, Integer> {
}
