package com.wom.customactivityapi.services;

import com.wom.customactivityapi.config.VaultConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private VaultConfiguration vaultConfiguration;

    @Value("${security_jwt_expiration_time}")
    private long jwtExpiration;

    @Value("${security_jwt_secretKey}")
    private String securityJwtSecretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims) {
        return buildToken(extraClaims, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        System.out.println(vaultConfiguration.getSecurityJwtSecretKey());
        //String secretKey = vaultConfiguration.getSecurityJwtSecretKey();
		String secretKey = "0ptEOWMM9CTDID7dSbbOyuXBEXLhNeafnvrtS5KPBVrJQZ1d0dFaQe8_XvzBnxOJ-npD5yORe3S9Pt1EKeRGl1wCVVTYEPVc0L3IOpcuvFPjfpg1hw_E8wIkknyGvCq4Zu76QAwXiDi1kNafBexz2iXhEYXwk_pNusgHFCRmluBpjY9FKhj96nUwS7O-Ahjy_A3aKIcnG2I8Io-eHNWpVKVAsUvDeXB1TYZIzXtPrdmrVlwbsXdpV3adB31CaA2";
        System.out.println("securityJwtSecretKey VAULT: " + secretKey);
        byte[] keyBytes = securityJwtSecretKey.getBytes(StandardCharsets.UTF_8); 
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
