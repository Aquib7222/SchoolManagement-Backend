// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.security.Key;
// import java.util.Date;
// import java.util.function.Function;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;

// @Service
// public class JwtService {

//     @Value("${jwt.secret}")
//     private String secretKey;

//     @Value("${jwt.expiration}")
//     private long expiration;

//     // Convert secretKey to HMAC-SHA signing key
//     private Key getSigningKey() {
//         byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//         return Keys.hmacShaKeyFor(keyBytes);
//     }

//     // Generate Token
//     public String generateToken(UserDetails userDetails) {
//         return Jwts.builder()
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                 .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     // Extract email (username)
//     public String extractEmail(String token) {
//         return extractClaim(token, Claims::getSubject);
//     }

//     // Extract all claims
//     public Claims extractAllClaims(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(getSigningKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }

//     // Extract specific claim
//     public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//         Claims claims = extractAllClaims(token);
//         return claimsResolver.apply(claims);
//     }

//     // Validate Token
//     public boolean isTokenValid(String token, UserDetails userDetails) {
//         String email = extractEmail(token);
//         return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
//     }

//     // Check expiration
//     private boolean isTokenExpired(String token) {
//         return extractAllClaims(token).getExpiration().before(new Date());
//     }
// }


package com.schoolmanagement.schoolmanagementwebsite.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    // Convert secret to HMAC-SHA signing key
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate Token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username/email
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract all claims
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract specific claim
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validate Token
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String email = extractEmail(token);

        return email.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    // Check expiration
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
}