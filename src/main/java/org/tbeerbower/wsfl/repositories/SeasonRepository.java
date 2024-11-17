package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.Season;

public interface SeasonRepository extends JpaRepository<Season, Integer> {
}
