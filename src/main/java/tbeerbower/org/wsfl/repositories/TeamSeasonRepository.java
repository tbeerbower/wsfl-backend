package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.TeamSeason;

public interface TeamSeasonRepository extends JpaRepository<TeamSeason, Integer> {
}
