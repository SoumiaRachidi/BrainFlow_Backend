package com.brainflow.brainflow.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // WARNING: for development only. Move to secure config in production.
    private final String secret = "ReplaceThisWithAProperVeryLongSecretKeyForJwtSigning-ChangeMe";
    private final Key key;
    private final long expirationMillis = 24 * 60 * 60 * 1000L; // 24 hours

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject, List<String> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String subject) {
        return generateToken(subject, new ArrayList<>());
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    public List<String> extractRoles(String token) {
        Claims claims = parseClaims(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List) {
            List<?> raw = (List<?>) rolesObj;
            List<String> result = new ArrayList<>();
            for (Object o : raw) {
                result.add(String.valueOf(o));
            }
            return result;
        }
        return new ArrayList<>();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !extractExpiration(token).before(new Date()));
    }
}