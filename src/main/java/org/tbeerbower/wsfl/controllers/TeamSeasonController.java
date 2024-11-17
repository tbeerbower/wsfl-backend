package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.TeamSeason;
import org.tbeerbower.wsfl.repositories.TeamSeasonRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/teamseasons")
public class TeamSeasonController {

    private final TeamSeasonRepository teamSeasonRepository;

    @Autowired
    public TeamSeasonController(TeamSeasonRepository teamSeasonRepository) {
        this.teamSeasonRepository = teamSeasonRepository;
    }

    // Create a new TeamSeason
    @PostMapping
    public ResponseEntity<TeamSeason> createTeamSeason(@RequestBody TeamSeason teamSeason) {
        TeamSeason savedTeamSeason = teamSeasonRepository.save(teamSeason);
        return new ResponseEntity<>(savedTeamSeason, HttpStatus.CREATED);
    }

    // Retrieve all TeamSeasons
    @GetMapping
    public ResponseEntity<List<TeamSeason>> getAllTeamSeasons() {
        List<TeamSeason> teamSeasons = teamSeasonRepository.findAll();
        return new ResponseEntity<>(teamSeasons, HttpStatus.OK);
    }

    // Retrieve a single TeamSeason by ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamSeason> getTeamSeasonById(@PathVariable Integer id) {
        Optional<TeamSeason> teamSeason = teamSeasonRepository.findById(id);
        return teamSeason
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing TeamSeason by ID
    @PutMapping("/{id}")
    public ResponseEntity<TeamSeason> updateTeamSeason(@PathVariable Integer id, @RequestBody TeamSeason teamSeasonDetails) {
        Optional<TeamSeason> teamSeason = teamSeasonRepository.findById(id);

        if (teamSeason.isPresent()) {
            TeamSeason existingTeamSeason = teamSeason.get();
            existingTeamSeason.setTeamId(teamSeasonDetails.getTeamId());
            existingTeamSeason.setSeasonId(teamSeasonDetails.getSeasonId());
            existingTeamSeason.setDraftOrder(teamSeasonDetails.getDraftOrder());
            TeamSeason updatedTeamSeason = teamSeasonRepository.save(existingTeamSeason);
            return new ResponseEntity<>(updatedTeamSeason, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a TeamSeason by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamSeason(@PathVariable Integer id) {
        if (teamSeasonRepository.existsById(id)) {
            teamSeasonRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
