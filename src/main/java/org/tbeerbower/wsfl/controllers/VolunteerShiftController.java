package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.VolunteerShift;
import org.tbeerbower.wsfl.repositories.VolunteerShiftRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/volunteer-shifts")
public class VolunteerShiftController {

    private final VolunteerShiftRepository volunteerShiftRepository;

    @Autowired
    public VolunteerShiftController(VolunteerShiftRepository volunteerShiftRepository) {
        this.volunteerShiftRepository = volunteerShiftRepository;
    }

    // Create a new VolunteerShift
    @PostMapping
    public ResponseEntity<VolunteerShift> createVolunteerShift(@RequestBody VolunteerShift volunteerShift) {
        VolunteerShift savedVolunteerShift = volunteerShiftRepository.save(volunteerShift);
        return new ResponseEntity<>(savedVolunteerShift, HttpStatus.CREATED);
    }

    // Retrieve all VolunteerShifts
    @GetMapping
    public ResponseEntity<List<VolunteerShift>> getAllVolunteerShifts() {
        List<VolunteerShift> volunteerShifts = volunteerShiftRepository.findAll();
        return new ResponseEntity<>(volunteerShifts, HttpStatus.OK);
    }

    // Retrieve a single VolunteerShift by ID
    @GetMapping("/{id}")
    public ResponseEntity<VolunteerShift> getVolunteerShiftById(@PathVariable Integer id) {
        Optional<VolunteerShift> volunteerShift = volunteerShiftRepository.findById(id);
        return volunteerShift
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing VolunteerShift by ID
    @PutMapping("/{id}")
    public ResponseEntity<VolunteerShift> updateVolunteerShift(@PathVariable Integer id, @RequestBody VolunteerShift volunteerShiftDetails) {
        Optional<VolunteerShift> volunteerShift = volunteerShiftRepository.findById(id);

        if (volunteerShift.isPresent()) {
            VolunteerShift existingVolunteerShift = volunteerShift.get();
            existingVolunteerShift.setSeasonId(volunteerShiftDetails.getSeasonId());
            existingVolunteerShift.setRaceId(volunteerShiftDetails.getRaceId());
            existingVolunteerShift.setName(volunteerShiftDetails.getName());
            existingVolunteerShift.setTag(volunteerShiftDetails.getTag());
            existingVolunteerShift.setRaceDefinitionId(volunteerShiftDetails.getRaceDefinitionId());
            VolunteerShift updatedVolunteerShift = volunteerShiftRepository.save(existingVolunteerShift);
            return new ResponseEntity<>(updatedVolunteerShift, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a VolunteerShift by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteerShift(@PathVariable Integer id) {
        if (volunteerShiftRepository.existsById(id)) {
            volunteerShiftRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
