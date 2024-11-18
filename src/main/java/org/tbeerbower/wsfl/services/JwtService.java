package org.tbeerbower.wsfl.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tbeerbower.wsfl.security.TokenClaims;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// JwtService.java
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public TokenClaims validateToken(String token) {
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Extract roles, defaulting to an empty set if not present
        Set<String> roles = Optional.ofNullable((List<String>) claims.get("roles"))
                .map(HashSet::new)
                .orElse(new HashSet<>());

        return new TokenClaims(
                claims.get("subject", String.class),
                claims.get("email", String.class),
                claims.get("picture", String.class),
                claims.get("name", String.class),
                claims.get("expiresAt", Date.class),
                claims.get("audience", String.class),
                claims.get("issuer", String.class),
                roles
        );
    }
}
