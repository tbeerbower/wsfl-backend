package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.RaceDefinition;

public interface RaceDefinitionRepository extends JpaRepository<RaceDefinition, Integer> {
}
