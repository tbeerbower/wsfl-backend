package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.RunnerAlias;

public interface RunnerAliasRepository extends JpaRepository<RunnerAlias, Integer> {
}
