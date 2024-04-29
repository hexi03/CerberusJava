package com.hexi.Cerberus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        System.err.println("BCryptPasswordEncoder: Created");
        return new BCryptPasswordEncoder();
    }

}
