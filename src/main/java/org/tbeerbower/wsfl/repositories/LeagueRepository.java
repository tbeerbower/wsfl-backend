package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.League;

public interface LeagueRepository extends JpaRepository<League, Integer> {
}
