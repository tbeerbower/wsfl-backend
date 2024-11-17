package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.TeamSeason;

public interface TeamSeasonRepository extends JpaRepository<TeamSeason, Integer> {
}
