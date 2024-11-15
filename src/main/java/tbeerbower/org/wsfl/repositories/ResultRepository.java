package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbeerbower.org.wsfl.entities.Result;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByRunnerId(Integer runnerId);
    List<Result> findByRaceId(Integer raceId);
}
