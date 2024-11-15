package tbeerbower.org.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tbeerbower.org.wsfl.entities.Season;
import tbeerbower.org.wsfl.repositories.SeasonRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/seasons")
public class SeasonController {

    private final SeasonRepository seasonRepository;

    @Autowired
    public SeasonController(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    // Create a new Season
    @PostMapping
    public ResponseEntity<Season> createSeason(@RequestBody Season season) {
        Season savedSeason = seasonRepository.save(season);
        return new ResponseEntity<>(savedSeason, HttpStatus.CREATED);
    }

    // Retrieve all Seasons
    @GetMapping
    public ResponseEntity<List<Season>> getAllSeasons() {
        List<Season> seasons = seasonRepository.findAll();
        return new ResponseEntity<>(seasons, HttpStatus.OK);
    }

    // Retrieve a single Season by ID
    @GetMapping("/{id}")
    public ResponseEntity<Season> getSeasonById(@PathVariable Integer id) {
        Optional<Season> season = seasonRepository.findById(id);
        return season
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing Season by ID
    @PutMapping("/{id}")
    public ResponseEntity<Season> updateSeason(@PathVariable Integer id, @RequestBody Season seasonDetails) {
        Optional<Season> season = seasonRepository.findById(id);

        if (season.isPresent()) {
            Season existingSeason = season.get();
            existingSeason.setName(seasonDetails.getName());
            existingSeason.setNumScores(seasonDetails.getNumScores());
            existingSeason.setDraftRounds(seasonDetails.getDraftRounds());
            existingSeason.setSupplementalRounds(seasonDetails.getSupplementalRounds());
            Season updatedSeason = seasonRepository.save(existingSeason);
            return new ResponseEntity<>(updatedSeason, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a Season by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Integer id) {
        if (seasonRepository.existsById(id)) {
            seasonRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
