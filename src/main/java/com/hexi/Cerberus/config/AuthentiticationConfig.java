package com.hexi.Cerberus.config;

import com.hexi.Cerberus.application.access.service.UserService;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class AuthentiticationConfig {

}
