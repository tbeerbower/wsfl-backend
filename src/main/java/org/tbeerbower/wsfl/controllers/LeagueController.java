package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tbeerbower.wsfl.entities.League;
import org.tbeerbower.wsfl.entities.League;
import org.tbeerbower.wsfl.repositories.LeagueRepository;
import org.tbeerbower.wsfl.repositories.LeagueRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueController(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    // Create a new League
    @PostMapping
    public ResponseEntity<League> createLeague(@RequestBody League league) {
        League savedLeague = leagueRepository.save(league);
        return new ResponseEntity<>(savedLeague, HttpStatus.CREATED);
    }

    // Retrieve all Leagues
    @GetMapping
    public ResponseEntity<List<League>> getAllLeagues() {
        List<League> leagues = leagueRepository.findAll();
        return new ResponseEntity<>(leagues, HttpStatus.OK);
    }

    // Retrieve a single League by ID
    @GetMapping("/{id}")
    public ResponseEntity<League> getLeagueById(@PathVariable Integer id) {
        Optional<League> league = leagueRepository.findById(id);
        return league
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing League by ID
    @PutMapping("/{id}")
    public ResponseEntity<League> updateLeague(@PathVariable Integer id, @RequestBody League leagueDetails) {
        Optional<League> league = leagueRepository.findById(id);

        if (league.isPresent()) {
            League existingLeague = league.get();
            existingLeague.setName(leagueDetails.getName());
            existingLeague.setOwnerId(leagueDetails.getOwnerId());
            League updatedLeague = leagueRepository.save(existingLeague);
            return new ResponseEntity<>(updatedLeague, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a League by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable Integer id) {
        if (leagueRepository.existsById(id)) {
            leagueRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
