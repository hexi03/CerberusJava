package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.access.UserCredentials;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
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
import java.util.AbstractMap;
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
        return jwtTokenUtils.generateToken(user.getId(),userDetails);
    }

    public String generateToken(UUIDBasedEntityID id) {
        Optional<User> user = userRepository.findById(new UserID(id));
        if(user.isEmpty()) return null;
        return generateToken(user.get());
    }


    public String getUsernameFromToken(String token) {
        return jwtTokenUtils.getUsername(token);
    }

    public Optional<AbstractMap.SimpleImmutableEntry<UserID,String>> authUser(UserCredentials userCredentials) {
        log.info(String.format("authUser(%s,%s)", userCredentials.getEmail(),userCredentials.getPassword()));
        Optional<User> user = userRepository.findByEmail(userCredentials.getEmail());
        if (user.isEmpty()) return Optional.empty();
        log.info(String.format("DTO пароль пользователя %s: %s", userCredentials.getEmail(), userCredentials.getPassword()));
        if (!passwordEncoder.matches(userCredentials.getPassword(), user.get().getPasswordHash()))
            return Optional.empty();
        return Optional.of(new AbstractMap.SimpleImmutableEntry<UserID,String>(user.get().getId(),generateToken(user.get())));
    }

    public String getPasswordHash(String password) {
        return passwordEncoder.encode(password);
    }

}
