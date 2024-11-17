package org.tbeerbower.wsfl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tbeerbower.wsfl.entities.VolunteerShift;

public interface VolunteerShiftRepository extends JpaRepository<VolunteerShift, Integer> {
}
