package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.access.UserCredentials;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.service.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.BCDSAPrivateKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupService userGroupService;
    private final JwtTokenUtils jwtTokenUtils;





    public String generateToken(User user) {
        UserDetails userDetails = userGroupService.getUserDetails(user);
        return jwtTokenUtils.generateToken(userDetails);
    }

    public String generateToken(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) return null;
        return generateToken(user.get());
    }


    public String getUsernameFromToken(String token) {
        return jwtTokenUtils.getUsername(token);
    }

    public Optional<String> authUser(UserCredentials userCredentials) {
        log.info(String.format("authUser(%s,%s)", userCredentials.getEmail(),userCredentials.getPassword()));
        Optional<User> user = userRepository.findByEmail(userCredentials.getEmail());
        if (user.isEmpty()) return Optional.empty();
        if (user.get().getPasswordHash().equals(getPasswordHash(userCredentials.getPassword())))
            return Optional.empty();
        return Optional.of(generateToken(user.get()));
    }

    public String getPasswordHash(String password) {
        return passwordEncoder.encode(password);
    }

}
