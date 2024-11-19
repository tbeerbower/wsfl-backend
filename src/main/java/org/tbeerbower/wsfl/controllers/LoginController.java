package org.tbeerbower.wsfl.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.tbeerbower.wsfl.entities.User;
import org.tbeerbower.wsfl.repositories.UserRepository;
import org.tbeerbower.wsfl.security.JwtUtils;
import org.tbeerbower.wsfl.security.TokenClaims;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Generate JWT
        Set<String> roles = Set.of(user.getRoles().split(","));

        TokenClaims tokenClaims = new TokenClaims(
                null,
                user.getEmail(),
                null,
                user.getName(),
                new Date(),
                null,
                "org.tbeerbower",
                roles
        );

        String jwt = JwtUtils.createJwt(tokenClaims, jwtSecret);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }


    // DTO for login request
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters
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

    // DTO for login response
    public static class LoginResponse {
        private String jwt;

        public LoginResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }
    }
}
