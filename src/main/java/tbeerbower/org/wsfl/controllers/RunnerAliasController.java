package tbeerbower.org.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tbeerbower.org.wsfl.entities.RunnerAlias;
import tbeerbower.org.wsfl.repositories.RunnerAliasRepository;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/runnerAliases")
public class RunnerAliasController {

    private final RunnerAliasRepository runnerAliasRepository;

    @Autowired
    public RunnerAliasController(RunnerAliasRepository runnerAliasRepository) {
        this.runnerAliasRepository = runnerAliasRepository;
    }

    // Create a new RunnerAlias
    @PostMapping
    public ResponseEntity<RunnerAlias> createRunnerAlias(@RequestBody RunnerAlias runnerAlias) {
        RunnerAlias savedRunnerAlias = runnerAliasRepository.save(runnerAlias);
        return new ResponseEntity<>(savedRunnerAlias, HttpStatus.CREATED);
    }

    // Retrieve all RunnerAliases
    @GetMapping
    public ResponseEntity<List<RunnerAlias>> getAllRunnerAliases() {
        List<RunnerAlias> runnerAliases = runnerAliasRepository.findAll();
        return new ResponseEntity<>(runnerAliases, HttpStatus.OK);
    }

    // Retrieve a single RunnerAlias by ID
    @GetMapping("/{id}")
    public ResponseEntity<RunnerAlias> getRunnerAliasById(@PathVariable Integer id) {
        Optional<RunnerAlias> runnerAlias = runnerAliasRepository.findById(id);
        return runnerAlias
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing RunnerAlias by ID
    @PutMapping("/{id}")
    public ResponseEntity<RunnerAlias> updateRunnerAlias(@PathVariable Integer id, @RequestBody RunnerAlias runnerAliasDetails) {
        Optional<RunnerAlias> runnerAlias = runnerAliasRepository.findById(id);

        if (runnerAlias.isPresent()) {
            RunnerAlias existingRunnerAlias = runnerAlias.get();
            existingRunnerAlias.setName(runnerAliasDetails.getName());
            existingRunnerAlias.setRunnerId(runnerAliasDetails.getRunnerId());
            RunnerAlias updatedRunnerAlias = runnerAliasRepository.save(existingRunnerAlias);
            return new ResponseEntity<>(updatedRunnerAlias, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a RunnerAlias by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRunnerAlias(@PathVariable Integer id) {
        if (runnerAliasRepository.existsById(id)) {
            runnerAliasRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
