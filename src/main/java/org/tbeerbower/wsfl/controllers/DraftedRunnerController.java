package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.DraftedRunner;
import org.tbeerbower.wsfl.repositories.DraftedRunnerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/drafted-runners")
public class DraftedRunnerController {

    private final DraftedRunnerRepository draftedRunnerRepository;

    @Autowired
    public DraftedRunnerController(DraftedRunnerRepository draftedRunnerRepository) {
        this.draftedRunnerRepository = draftedRunnerRepository;
    }

    // Create a new TeamRunner
    @PostMapping
    public ResponseEntity<DraftedRunner> createTeamRunner(@RequestBody DraftedRunner draftedRunner) {
        DraftedRunner savedDraftedRunner = draftedRunnerRepository.save(draftedRunner);
        return new ResponseEntity<>(savedDraftedRunner, HttpStatus.CREATED);
    }

    // Retrieve all TeamRunners
    @GetMapping
    public ResponseEntity<List<DraftedRunner>> getAllTeamRunners() {
        List<DraftedRunner> draftedRunners = draftedRunnerRepository.findAll();
        return new ResponseEntity<>(draftedRunners, HttpStatus.OK);
    }

    // Retrieve a single TeamRunner by ID
    @GetMapping("/{id}")
    public ResponseEntity<DraftedRunner> getTeamRunnerById(@PathVariable Integer id) {
        Optional<DraftedRunner> teamRunner = draftedRunnerRepository.findById(id);
        return teamRunner
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing TeamRunner by ID
    @PutMapping("/{id}")
    public ResponseEntity<DraftedRunner> updateTeamRunner(@PathVariable Integer id, @RequestBody DraftedRunner draftedRunnerDetails) {
        Optional<DraftedRunner> teamRunner = draftedRunnerRepository.findById(id);

        if (teamRunner.isPresent()) {
            DraftedRunner existingDraftedRunner = teamRunner.get();
            existingDraftedRunner.setRunnerId(draftedRunnerDetails.getRunnerId());
            existingDraftedRunner.setDraftOrder(draftedRunnerDetails.getDraftOrder());
            existingDraftedRunner.setActive(draftedRunnerDetails.isActive());
            existingDraftedRunner.setTeamSeasonId(draftedRunnerDetails.getTeamSeasonId());
            DraftedRunner updatedDraftedRunner = draftedRunnerRepository.save(existingDraftedRunner);
            return new ResponseEntity<>(updatedDraftedRunner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a TeamRunner by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamRunner(@PathVariable Integer id) {
        if (draftedRunnerRepository.existsById(id)) {
            draftedRunnerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
