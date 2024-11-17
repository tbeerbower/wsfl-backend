package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.RaceDefinition;
import org.tbeerbower.wsfl.repositories.RaceDefinitionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/race-definitions")
public class RaceDefinitionController {

    @Autowired
    private RaceDefinitionRepository raceDefinitionRepository;

    // Get all race definitions
    @GetMapping
    public List<RaceDefinition> getAllRaceDefinitions() {
        return raceDefinitionRepository.findAll();
    }

    // Get a race definition by ID
    @GetMapping("/{id}")
    public ResponseEntity<RaceDefinition> getRaceDefinitionById(@PathVariable Integer id) {
        Optional<RaceDefinition> raceDefinition = raceDefinitionRepository.findById(id);
        return raceDefinition.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new race definition
    @PostMapping
    public RaceDefinition createRaceDefinition(@RequestBody RaceDefinition raceDefinition) {
        return raceDefinitionRepository.save(raceDefinition);
    }

    // Update a race definition by ID
    @PutMapping("/{id}")
    public ResponseEntity<RaceDefinition> updateRaceDefinition(@PathVariable Integer id, @RequestBody RaceDefinition raceDefinitionDetails) {
        return raceDefinitionRepository.findById(id).map(raceDefinition -> {
            raceDefinition.setName(raceDefinitionDetails.getName());
            raceDefinition.setShortName(raceDefinitionDetails.getShortName());
            raceDefinition.setSmallIcon(raceDefinitionDetails.getSmallIcon());
            RaceDefinition updatedRaceDefinition = raceDefinitionRepository.save(raceDefinition);
            return ResponseEntity.ok(updatedRaceDefinition);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a race definition by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRaceDefinition(@PathVariable Integer id) {
        return raceDefinitionRepository.findById(id).map(raceDefinition -> {
            raceDefinitionRepository.delete(raceDefinition);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
