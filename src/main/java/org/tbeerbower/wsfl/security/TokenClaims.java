package org.tbeerbower.wsfl.security;

import java.util.Date;

public record TokenClaims(
        String subject,
        String email,
        String picture,
        String name,
        Date expiresAt,
        String audience,
        String issuer
) {
}
