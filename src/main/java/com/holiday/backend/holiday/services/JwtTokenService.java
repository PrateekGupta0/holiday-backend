package com.holiday.backend.holiday.services;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;


@Component
public class JwtTokenService {

    private static final String SECRET_KEY = "your-secret-key";

    public String generateToken( String userId) {

        return Jwts.builder()
                .setSubject(userId)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String parseToken(String token) {

        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return jws.getBody().getSubject();
        }catch (JwtException e){

            return "FAILED";
        }
    }
}
