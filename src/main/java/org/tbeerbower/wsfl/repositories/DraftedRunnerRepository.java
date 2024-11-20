package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.DraftedRunner;

public interface DraftedRunnerRepository extends JpaRepository<DraftedRunner, Integer> {
}
