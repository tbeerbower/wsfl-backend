package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.Race;
import org.tbeerbower.wsfl.entities.Result;
import org.tbeerbower.wsfl.repositories.RaceRepository;
import org.tbeerbower.wsfl.repositories.ResultRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/races")
public class RaceController {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private ResultRepository resultRepository;

    // Get all races
    @GetMapping
    public List<Race> getAllRaces() {
        return raceRepository.findAll();
    }

    // Get a race by ID
    @GetMapping("/{id}")
    public ResponseEntity<Race> getRaceById(@PathVariable Integer id) {
        Optional<Race> race = raceRepository.findById(id);
        return race.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get race results by ID
    @GetMapping("/{id}/results")
    public ResponseEntity<List<Result>> getRaceResultsById(@PathVariable Integer id) {
        return ResponseEntity.ok(resultRepository.findByRaceId(id));
    }

    // Create a new race
    @PostMapping
    public Race createRace(@RequestBody Race race) {
        return raceRepository.save(race);
    }

    // Update a race by ID
    @PutMapping("/{id}")
    public ResponseEntity<Race> updateRace(@PathVariable Integer id, @RequestBody Race raceDetails) {
        return raceRepository.findById(id).map(race -> {
            race.setName(raceDetails.getName());
            race.setSeasonId(raceDetails.getSeasonId());
            race.setWeek(raceDetails.getWeek());
            race.setCancelled(raceDetails.isCancelled());
            race.setRaceDefinitionId(raceDetails.getRaceDefinitionId());
            Race updatedRace = raceRepository.save(race);
            return ResponseEntity.ok(updatedRace);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a race by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRace(@PathVariable Integer id) {
        return raceRepository.findById(id).map(race -> {
            raceRepository.delete(race);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
