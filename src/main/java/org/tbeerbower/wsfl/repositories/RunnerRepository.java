package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.Runner;

public interface RunnerRepository extends JpaRepository<Runner, Integer> {
}
