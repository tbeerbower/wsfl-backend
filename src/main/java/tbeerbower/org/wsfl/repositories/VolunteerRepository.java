package tbeerbower.org.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbeerbower.org.wsfl.entities.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
}
