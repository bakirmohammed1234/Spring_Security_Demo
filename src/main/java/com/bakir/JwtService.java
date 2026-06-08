package com.bakir;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    public static final String SECRET ="mySuperSecretKeyForJwtAuthentication2026SpringBoot";

    public String generateToken(String username){

        return Jwts.builder().
                setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .addClaims(new HashMap<>())
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key  getSignedKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Claims  verifySignatureAndExtracteAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return verifySignatureAndExtracteAllClaims(token).getSubject();
    }
    public Date getExpiration (String token) {
        return verifySignatureAndExtracteAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
