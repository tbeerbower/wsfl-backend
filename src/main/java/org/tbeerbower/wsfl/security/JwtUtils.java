package org.tbeerbower.wsfl.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

    public static String createJwt(TokenClaims claims, String jwtSecret) {
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setIssuer("org.tbeerbower")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60))) // 1 hour
                .signWith(key)
                .claim("email", claims.email())
                .claim("picture", claims.picture())
                .claim("name", claims.name())
                .claim("roles", claims.roles())
                .compact();
    }

    public static String createJwt(String idToken, TokenClaims claims, String jwtSecret) {
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(idToken)
                .setIssuer("org.tbeerbower")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60))) // 1 hour
                .signWith(key)
                .claim("email", claims.email())
                .claim("picture", claims.picture())
                .claim("name", claims.name())
                .claim("roles", claims.roles())
                .compact();
    }
}
