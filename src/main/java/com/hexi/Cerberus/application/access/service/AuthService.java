package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.access.UserCredentials;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecretKey secretKey;

    public String generateToken(User user) {
        Claims claims = Jwts
                .claims()
                .setSubject(user.getName())
                .build();
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
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
        if (user.get().getPasswordHash().equals(getPasswordHash(userCredentials.getPassword()))) return Optional.empty();
        return user;
    }

    public String getPasswordHash(String password){
        return passwordEncoder.encode(password);
    }

}
