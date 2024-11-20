package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.DraftTeam;

public interface DraftTeamRepository extends JpaRepository<DraftTeam, Integer> {
}
