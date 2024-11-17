package tbeerbower.org.wsfl.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tbeerbower.org.wsfl.dtos.GoogleCode;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/auth/google")
public class GoogleAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<?> handleGoogleAuth(@RequestBody GoogleCode body, HttpServletResponse response) {
        try {
            String authorizationCode = body.getCode();

            // Exchange the authorization code for tokens
            RestTemplate restTemplate = new RestTemplate();
            String tokenEndpoint = "https://oauth2.googleapis.com/token";

            Map<String, String> tokenRequest = Map.of(
                    "client_id", "32904200865-06fav6dc33cs4judtp1abrt10373ldsj.apps.googleusercontent.com",
                    "client_secret", "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",
                    "code", authorizationCode,
                    "redirect_uri", "http://localhost:8080",
                    "grant_type", "authorization_code"
            );

            Map<String, Object> tokenResponse = restTemplate.postForObject(tokenEndpoint, tokenRequest, Map.class);

            // Handle tokens (e.g., validate, parse, create session)
            String idToken = (String) tokenResponse.get("id_token");
            String accessToken = (String) tokenResponse.get("access_token");

            // Step 2: Verify ID token
            boolean isTokenValid = verifyIdToken(idToken);
            if (!isTokenValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token");
            }

            // Step 3: Create JWT
            String jwt = createJwt(idToken);

            // Step 4: Return JWT to frontend
            return ResponseEntity.ok().body(Map.of("jwt", jwt, "accessToken", accessToken));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }

    private boolean verifyIdToken(String idToken) {
        try {
            String url = "https://www.googleapis.com/oauth2/v3/certs";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

            JsonNode jwks = objectMapper.readTree(response.getBody());
            // Parse and validate the ID token using Google's public keys (logic omitted for brevity)

            // TODO: Validate token claims such as expiration, audience, and issuer

            return true; // Placeholder, replace with actual validation logic
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String createJwt(String idToken) {
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(idToken)
                .setIssuer("tbeerbower.org")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60))) // 1 hour
                .signWith(key)
                .compact();
    }
}
