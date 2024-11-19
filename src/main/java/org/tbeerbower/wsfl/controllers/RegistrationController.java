package org.tbeerbower.wsfl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.User;
import org.tbeerbower.wsfl.repositories.UserRepository;

import java.util.Optional;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {

        // Check if email already exists
        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getEmail());
        if (existingUserByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        // Create new user
        User newUser = new User(
                request.getName(), null, request.getEmail(),
                passwordEncoder.encode(request.getPassword()), "ROLE_USER", true);

        // Save user
        User createdUser = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // DTO for registration request body
    public static class RegistrationRequest {
        private String name;
        private String email;
        private String password;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
