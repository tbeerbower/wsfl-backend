package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.RaceDefinition;

public interface RaceDefinitionRepository extends JpaRepository<RaceDefinition, Integer> {
}
