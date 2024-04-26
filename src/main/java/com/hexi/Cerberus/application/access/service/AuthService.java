package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.access.UserCredentials;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${params.jwt.secret}")
    private String secretKey;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(User user) {
        Claims claims = Jwts
                .claims()
                .setSubject(user.getName())
                .build();
        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parse(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Optional<User> authUser(UserCredentials userCredentials) {
        Optional<User> user = userRepository.findByEmail(userCredentials.email);
        if (user.isEmpty()) return user;
        if (user.get().getPasswordHash().equals(getPasswordHash(userCredentials.getPassword())))
            return Optional.empty();
        return user;
    }

    public String getPasswordHash(String password) {
        return passwordEncoder.encode(password);
    }

}
