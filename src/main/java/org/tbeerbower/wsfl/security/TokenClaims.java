package org.tbeerbower.wsfl.security;

import org.tbeerbower.wsfl.entities.User;

import java.util.Date;
import java.util.Set;

public record TokenClaims(
        String subject,
        Date expiresAt,
        String audience,
        String issuer,
        String auth
) {
}
