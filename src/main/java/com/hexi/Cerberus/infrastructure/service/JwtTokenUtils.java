package com.hexi.Cerberus.infrastructure.service;

import com.hexi.Cerberus.domain.user.UserID;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenUtils {
    @Value("${params.jwt.secret}")
    private String secret;

    @Value("${params.jwt.lifetime}")
    private String jwtLifetime;

    public String generateToken(UserID userId, UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);
        claims.put("username", userDetails.getUsername());
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + Duration.parse(jwtLifetime).toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId.getId().toString())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).get("username", String.class);
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UserID getPrincipalId(String token) {
        return new UserID(getAllClaimsFromToken(token).getSubject());
    }
}