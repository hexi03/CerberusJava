package com.hexi.Cerberus.adapter.web.rest.auth;

import com.hexi.Cerberus.adapter.web.rest.auth.DTO.AuthResponse;
import com.hexi.Cerberus.adapter.web.rest.auth.DTO.UserDTO;
import com.hexi.Cerberus.application.access.service.AuthService;
import com.hexi.Cerberus.domain.access.UserCredentials;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.infrastructure.adapter.DrivingAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@DrivingAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userCreds) {
        log.info("Try to auth!!!");
        Optional<String> user_token = authService.authUser(new UserCredentials(userCreds.getEmail(), userCreds.getPassword()));
        if (user_token.isPresent()) {
            return ResponseEntity.ok(new AuthResponse(user_token.get()));
        } else
            return ResponseEntity.status(401).build();
    }
}