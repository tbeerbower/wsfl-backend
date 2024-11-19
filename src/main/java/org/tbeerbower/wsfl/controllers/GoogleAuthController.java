package org.tbeerbower.wsfl.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.tbeerbower.wsfl.dtos.GoogleCode;
import org.tbeerbower.wsfl.entities.User;
import org.tbeerbower.wsfl.repositories.UserRepository;
import org.tbeerbower.wsfl.security.JwtUtils;
import org.tbeerbower.wsfl.security.TokenClaims;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@RestController
@PreAuthorize("permitAll()")
@CrossOrigin
@RequestMapping("/api/auth/google")
public class GoogleAuthController {
    private static final String GOOGLE_ISSUER = "https://accounts.google.com";

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

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> handleGoogleAuth(@RequestBody GoogleCode body, HttpServletResponse response) {
        try {
            String authorizationCode = body.getCode();

            // Exchange the authorization code for tokens
            String tokenEndpoint = "https://oauth2.googleapis.com/token";

            Map<String, String> tokenRequest = Map.of(
                    "client_id", clientId,
                    "client_secret", clientSecret,
                    "code", authorizationCode,
                    "redirect_uri", redirectUri,
                    "grant_type", "authorization_code"
            );

            Map<String, Object> tokenResponse = restTemplate.postForObject(tokenEndpoint, tokenRequest, Map.class);

            // Handle tokens (e.g., validate, parse, create session)
            String idToken = (String) tokenResponse.get("id_token");
            String accessToken = (String) tokenResponse.get("access_token");

            // Step 2: Verify ID token

            try {
                // Step 3: Create JWT
                TokenClaims claims = verifyAndExtractClaims(idToken);
                String jwt = JwtUtils.createJwt(idToken, claims, jwtSecret);

                // Step 4: Return JWT to frontend
                return ResponseEntity.ok().body(Map.of("jwt", jwt, "accessToken", accessToken));

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }

    public TokenClaims verifyAndExtractClaims(String idToken) throws Exception {
        try {
            // Decode the token without verification first to get the key ID
            DecodedJWT unverifiedJwt = JWT.decode(idToken);
            String keyId = unverifiedJwt.getKeyId();

            // Fetch Google's public keys
            String url = "https://www.googleapis.com/oauth2/v3/certs";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            JsonNode jwks = objectMapper.readTree(response.getBody());

            // Find the matching key
            RSAPublicKey publicKey = null;
            for (JsonNode key : jwks.get("keys")) {
                if (key.get("kid").asText().equals(keyId)) {
                    publicKey = parsePublicKey(
                            key.get("n").asText(),
                            key.get("e").asText()
                    );
                    break;
                }
            }

            if (publicKey == null) {
                throw new JWTVerificationException("Matching public key not found");
            }

            // Verify the token
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            Verification verification = JWT.require(algorithm)
                    .withIssuer(GOOGLE_ISSUER)
                    .withAudience(clientId)
                    .acceptLeeway(5); // 5 seconds leeway for clock skew

            DecodedJWT jwt = verification.build().verify(idToken);

            String email = jwt.getClaim("email").asString();
            String name = jwt.getClaim("name").asString();

            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                // Create user
                user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword("na");
                user.setActive(true);
                user.setRoles("USER");
                userRepository.save(user);
            }
            Set<String> roles = Set.of(user.getRoles().split(","));

            // Extract claims
            return new TokenClaims(
                    jwt.getSubject(),
                    email,
                    jwt.getClaim("picture").asString(),
                    name,
                    jwt.getExpiresAt(),
                    jwt.getAudience().getFirst(),
                    jwt.getIssuer(),
                    roles
            );
        } catch (JWTVerificationException e) {
            throw new Exception("Token verification failed: " + e.getMessage(), e);
        }
    }


    private RSAPublicKey parsePublicKey(String modulus, String exponent) throws Exception {
        byte[] nBytes = Base64.getUrlDecoder().decode(modulus);
        byte[] eBytes = Base64.getUrlDecoder().decode(exponent);

        java.math.BigInteger n = new java.math.BigInteger(1, nBytes);
        java.math.BigInteger e = new java.math.BigInteger(1, eBytes);

        java.security.spec.RSAPublicKeySpec spec = new java.security.spec.RSAPublicKeySpec(n, e);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) factory.generatePublic(spec);
    }

}
