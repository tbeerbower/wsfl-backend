package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.RunnerAlias;

public interface RunnerAliasRepository extends JpaRepository<RunnerAlias, Integer> {
}
