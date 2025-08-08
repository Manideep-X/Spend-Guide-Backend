package com.manideep.spendguide.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String secretKey;
    private final int expiryDuration = 1000 * 60 * 5; // 5 mins validity

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(String username) {        
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryDuration))
                .signWith(getSigningKey()) // For signature we need to sign the secret key with HS256
                .compact(); // This generate the final JWT token
    }

    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date());
    }

    // First need to check if the token's username matches with the userDetails, then if the token expired.
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        return ( getUsername(token).equals(userDetails.getUsername()) 
                && 
                !isTokenExpired(token) );
    }
    

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
