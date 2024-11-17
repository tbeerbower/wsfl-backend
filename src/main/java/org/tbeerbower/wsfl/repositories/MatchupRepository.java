package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.Matchup;

public interface MatchupRepository extends JpaRepository<Matchup, Integer> {
}
