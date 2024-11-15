package tbeerbower.org.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tbeerbower.org.wsfl.entities.Runner;
import tbeerbower.org.wsfl.repositories.RunnerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/runners")
public class RunnerController {

    @Autowired
    private RunnerRepository runnerRepository;

    // Get all runners
    @GetMapping
    public List<Runner> getAllRunners() {
        return runnerRepository.findAll();
    }

    // Get a runner by ID
    @GetMapping("/{id}")
    public ResponseEntity<Runner> getRunnerById(@PathVariable Integer id) {
        Optional<Runner> runner = runnerRepository.findById(id);
        return runner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new runner
    @PostMapping
    public Runner createRunner(@RequestBody Runner runner) {
        return runnerRepository.save(runner);
    }

    // Update a runner by ID
    @PutMapping("/{id}")
    public ResponseEntity<Runner> updateRunner(@PathVariable Integer id, @RequestBody Runner runnerDetails) {
        return runnerRepository.findById(id).map(runner -> {
            runner.setName(runnerDetails.getName());
            runner.setGender(runnerDetails.getGender());
            Runner updatedRunner = runnerRepository.save(runner);
            return ResponseEntity.ok(updatedRunner);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a runner by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRunner(@PathVariable Integer id) {
        return runnerRepository.findById(id).map(runner -> {
            runnerRepository.delete(runner);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
