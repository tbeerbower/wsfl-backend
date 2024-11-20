package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.DraftTeam;
import org.tbeerbower.wsfl.repositories.DraftTeamRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/draft-teams")
public class DraftTeamController {

    private final DraftTeamRepository draftTeamRepository;

    @Autowired
    public DraftTeamController(DraftTeamRepository draftTeamRepository) {
        this.draftTeamRepository = draftTeamRepository;
    }

    // Create a new DraftTeam
    @PostMapping
    public ResponseEntity<DraftTeam> createDraftTeam(@RequestBody DraftTeam draftTeam) {
        DraftTeam savedDraftTeam = draftTeamRepository.save(draftTeam);
        return new ResponseEntity<>(savedDraftTeam, HttpStatus.CREATED);
    }

    // Retrieve all DraftTeams
    @GetMapping
    public ResponseEntity<List<DraftTeam>> getAllDraftTeams() {
        List<DraftTeam> draftTeams = draftTeamRepository.findAll();
        return new ResponseEntity<>(draftTeams, HttpStatus.OK);
    }

    // Retrieve a single DraftTeam by ID
    @GetMapping("/{id}")
    public ResponseEntity<DraftTeam> getDraftTeamById(@PathVariable Integer id) {
        Optional<DraftTeam> draftTeam = draftTeamRepository.findById(id);
        return draftTeam
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing DraftTeam by ID
    @PutMapping("/{id}")
    public ResponseEntity<DraftTeam> updateDraftTeam(@PathVariable Integer id, @RequestBody DraftTeam draftTeamDetails) {
        Optional<DraftTeam> draftTeam = draftTeamRepository.findById(id);

        if (draftTeam.isPresent()) {
            DraftTeam existingDraftTeam = draftTeam.get();
            existingDraftTeam.setTeamId(draftTeamDetails.getTeamId());
            existingDraftTeam.setSeasonId(draftTeamDetails.getSeasonId());
            existingDraftTeam.setDraftOrder(draftTeamDetails.getDraftOrder());
            DraftTeam updatedDraftTeam = draftTeamRepository.save(existingDraftTeam);
            return new ResponseEntity<>(updatedDraftTeam, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a DraftTeam by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDraftTeam(@PathVariable Integer id) {
        if (draftTeamRepository.existsById(id)) {
            draftTeamRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
