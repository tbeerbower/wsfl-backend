package org.tbeerbower.wsfl.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

public class GoogleTokenVerifier {
    private static final String GOOGLE_ISSUER = "https://accounts.google.com";
    private final String clientId;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public GoogleTokenVerifier(String clientId, RestTemplate restTemplate) {
        this.clientId = clientId;
        this.restTemplate = restTemplate;
    }

    public record TokenClaims(
            String subject,
            String email,
            String picture,
            String name,
            Date expiresAt,
            String audience,
            String issuer
    ) {}

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

            // Extract claims
            return new TokenClaims(
                    jwt.getSubject(),
                    jwt.getClaim("email").asString(),
                    jwt.getClaim("picture").asString(),
                    jwt.getClaim("name").asString(),
                    jwt.getExpiresAt(),
                    jwt.getAudience().get(0),
                    jwt.getIssuer()
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