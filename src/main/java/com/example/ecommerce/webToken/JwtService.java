package com.example.ecommerce.webToken;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final String SECRET = "k2M8fJFGaKs/6nVl7KRNNaC7I1qv5Gmj2Nfj1vhiuqU=";
    private static final long ACCESS_TOKEN_VALIDITY = TimeUnit.MINUTES.toMillis(24*60);
    private static final long REFRESH_TOKEN_VALIDITY = TimeUnit.MINUTES.toMillis(24*7*60);

    public String generateAccessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_TOKEN_VALIDITY);
    }

    // Utility Token functions
    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);

        return claims.getSubject();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);

        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    public Claims getClaims(String jwt) {
        try {
            return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();   
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT Token");
        }
    }
    // Utility Token functions

    // Private Methods
    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private String generateToken(String subject, Long validity) {
        Map<String, String> claims = new HashMap<>();
        claims.put("test", "khanthmue.tech");

        return Jwts
                    .builder()
                    .claims(claims)
                    .subject(subject)
                    .issuedAt(Date.from(Instant.now()))
                    .expiration(Date.from(Instant.now().plusMillis(validity)))
                    .signWith(generateKey(), Jwts.SIG.HS256)
                    .compact();
    }
}
