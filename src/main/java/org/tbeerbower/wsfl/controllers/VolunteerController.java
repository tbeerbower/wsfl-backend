package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.Volunteer;
import org.tbeerbower.wsfl.repositories.VolunteerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/volunteers")
public class VolunteerController {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerController(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    // Create a new Volunteer
    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return new ResponseEntity<>(savedVolunteer, HttpStatus.CREATED);
    }

    // Retrieve all Volunteers
    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }

    // Retrieve a single Volunteer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Integer id) {
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        return volunteer
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing Volunteer by ID
    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Integer id, @RequestBody Volunteer volunteerDetails) {
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);

        if (volunteer.isPresent()) {
            Volunteer existingVolunteer = volunteer.get();
            existingVolunteer.setShiftId(volunteerDetails.getShiftId());
            existingVolunteer.setRunnerId(volunteerDetails.getRunnerId());
            Volunteer updatedVolunteer = volunteerRepository.save(existingVolunteer);
            return new ResponseEntity<>(updatedVolunteer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Volunteer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Integer id) {
        if (volunteerRepository.existsById(id)) {
            volunteerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
