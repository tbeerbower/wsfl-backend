package org.tbeerbower.wsfl.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tbeerbower.wsfl.services.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        log.debug("Processing request to: {}", requestURI);

        // Clear any existing authentication
        SecurityContextHolder.clearContext();

        // If there's no auth header, continue the chain without authentication
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No valid Authorization header found, continuing chain");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authHeader.substring(7);
            log.debug("Attempting to validate JWT token");

            TokenClaims claims = jwtService.validateToken(jwt);
            log.debug("JWT token validated successfully for user: {}", claims.subject());

            Set<String> roles = Set.of(claims.auth().split(","));

            // Convert roles to GrantedAuthorities
            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.toUpperCase()))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(claims, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Set authentication in SecurityContext with roles: {}", authorities);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT validation failed", e);
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token: " + e.getMessage());
        }
    }
}