package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
}
