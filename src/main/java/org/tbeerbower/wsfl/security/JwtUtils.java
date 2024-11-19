package org.tbeerbower.wsfl.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.tbeerbower.wsfl.entities.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

    public static String createJwt(User user, String jwtSecret) {
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("org.tbeerbower")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60))) // 1 hour
                .signWith(key)
                .claim("auth", user.getRoles())
                .compact();
    }
}
