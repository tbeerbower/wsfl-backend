package tbeerbower.org.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tbeerbower.org.wsfl.entities.Matchup;
import tbeerbower.org.wsfl.repositories.MatchupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/matchups")
public class MatchupController {

    @Autowired
    private MatchupRepository matchupRepository;

    @GetMapping
    public List<Matchup> getAllMatchups() {
        return matchupRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matchup> getMatchupById(@PathVariable Integer id) {
        Optional<Matchup> matchup = matchupRepository.findById(id);
        return matchup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Matchup createMatchup(@RequestBody Matchup matchup) {
        return matchupRepository.save(matchup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matchup> updateMatchup(@PathVariable Integer id, @RequestBody Matchup matchupDetails) {
        return matchupRepository.findById(id).map(matchup -> {
            matchup.setSeasonId(matchupDetails.getSeasonId());
            matchup.setRaceId(matchupDetails.getRaceId());
            matchup.setTeamAId(matchupDetails.getTeamAId());
            matchup.setTeamBId(matchupDetails.getTeamBId());
            Matchup updatedMatchup = matchupRepository.save(matchup);
            return ResponseEntity.ok(updatedMatchup);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMatchup(@PathVariable Integer id) {
        return matchupRepository.findById(id).map(matchup -> {
            matchupRepository.delete(matchup);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
