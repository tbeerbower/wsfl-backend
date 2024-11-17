package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
