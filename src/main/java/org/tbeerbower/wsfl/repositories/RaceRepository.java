package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.Race;

public interface RaceRepository extends JpaRepository<Race, Integer> {
}
