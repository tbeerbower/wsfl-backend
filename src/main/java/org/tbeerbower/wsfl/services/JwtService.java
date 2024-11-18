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

        return new TokenClaims(
                claims.get("subject", String.class),
                claims.get("email", String.class),
                claims.get("picture", String.class),
                claims.get("name", String.class),
                claims.get("expiresAt", Date.class),
                claims.get("audience", String.class),
                claims.get("issuer", String.class)
        );
    }
}
