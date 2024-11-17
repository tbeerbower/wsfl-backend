package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.TeamRunner;

public interface TeamRunnerRepository extends JpaRepository<TeamRunner, Integer> {
}
