package tbeerbower.org.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tbeerbower.org.wsfl.entities.Result;
import tbeerbower.org.wsfl.repositories.ResultRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    // Get all results or filter by runnerId or raceId
    @GetMapping
    public ResponseEntity<List<Result>> getAllResults(
            @RequestParam(value = "runnerId", required = false) Integer runnerId,
            @RequestParam(value = "raceId", required = false) Integer raceId) {
        List<Result> results;

        if (runnerId != null) {
            results = resultRepository.findByRunnerId(runnerId);
        } else if (raceId != null) {
            results = resultRepository.findByRaceId(raceId);
        } else {
            results = resultRepository.findAll();
        }

        return ResponseEntity.ok(results);
    }

    // Get a specific result by ID
    @GetMapping("/{id}")
    public ResponseEntity<Result> getResultById(@PathVariable Integer id) {
        Optional<Result> result = resultRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a new result
    @PostMapping
    public ResponseEntity<Result> createResult(@RequestBody Result result) {
        Result savedResult = resultRepository.save(result);
        return ResponseEntity.ok(savedResult);
    }

    // Update an existing result
    @PutMapping("/{id}")
    public ResponseEntity<Result> updateResult(@PathVariable Integer id, @RequestBody Result resultDetails) {
        Optional<Result> existingResult = resultRepository.findById(id);

        if (existingResult.isPresent()) {
            Result result = existingResult.get();
            result.setRunnerId(resultDetails.getRunnerId());
            result.setPlaceGender(resultDetails.getPlaceGender());
            result.setPlaceOverall(resultDetails.getPlaceOverall());
            result.setTime(resultDetails.getTime());
            result.setRace(resultDetails.getRace());
            Result updatedResult = resultRepository.save(result);
            return ResponseEntity.ok(updatedResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a result
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Integer id) {
        Optional<Result> existingResult = resultRepository.findById(id);
        if (existingResult.isPresent()) {
            resultRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
