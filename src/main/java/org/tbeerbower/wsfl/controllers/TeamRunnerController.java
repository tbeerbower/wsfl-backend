package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.TeamRunner;
import org.tbeerbower.wsfl.repositories.TeamRunnerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/teamrunners")
public class TeamRunnerController {

    private final TeamRunnerRepository teamRunnerRepository;

    @Autowired
    public TeamRunnerController(TeamRunnerRepository teamRunnerRepository) {
        this.teamRunnerRepository = teamRunnerRepository;
    }

    // Create a new TeamRunner
    @PostMapping
    public ResponseEntity<TeamRunner> createTeamRunner(@RequestBody TeamRunner teamRunner) {
        TeamRunner savedTeamRunner = teamRunnerRepository.save(teamRunner);
        return new ResponseEntity<>(savedTeamRunner, HttpStatus.CREATED);
    }

    // Retrieve all TeamRunners
    @GetMapping
    public ResponseEntity<List<TeamRunner>> getAllTeamRunners() {
        List<TeamRunner> teamRunners = teamRunnerRepository.findAll();
        return new ResponseEntity<>(teamRunners, HttpStatus.OK);
    }

    // Retrieve a single TeamRunner by ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamRunner> getTeamRunnerById(@PathVariable Integer id) {
        Optional<TeamRunner> teamRunner = teamRunnerRepository.findById(id);
        return teamRunner
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing TeamRunner by ID
    @PutMapping("/{id}")
    public ResponseEntity<TeamRunner> updateTeamRunner(@PathVariable Integer id, @RequestBody TeamRunner teamRunnerDetails) {
        Optional<TeamRunner> teamRunner = teamRunnerRepository.findById(id);

        if (teamRunner.isPresent()) {
            TeamRunner existingTeamRunner = teamRunner.get();
            existingTeamRunner.setRunnerId(teamRunnerDetails.getRunnerId());
            existingTeamRunner.setDraftOrder(teamRunnerDetails.getDraftOrder());
            existingTeamRunner.setActive(teamRunnerDetails.isActive());
            existingTeamRunner.setTeamSeasonId(teamRunnerDetails.getTeamSeasonId());
            TeamRunner updatedTeamRunner = teamRunnerRepository.save(existingTeamRunner);
            return new ResponseEntity<>(updatedTeamRunner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a TeamRunner by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamRunner(@PathVariable Integer id) {
        if (teamRunnerRepository.existsById(id)) {
            teamRunnerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
