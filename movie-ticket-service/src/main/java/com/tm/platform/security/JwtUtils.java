package com.tm.platform.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private final String jwtSecret = "SecretKeyForMovieBookingPlatform2026DevProject";
    private final int jwtExpirationMs = 86400000; // 24 hours

    public String generateToken(String email){
       return Jwts.builder()
               .setSubject(email)
               .setIssuedAt(new java.util.Date())
               .setExpiration(new java.util.Date((new java.util.Date()).getTime() + jwtExpirationMs))
               .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret)
               .compact();
    }

    public String getEmailFromToken(String token){
       return Jwts.parser()
               .setSigningKey(jwtSecret)
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
           return false;
        }
    }
}
