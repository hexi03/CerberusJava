package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserGroupService {
    @Autowired
    UserRepository userRepository;

    public Optional<org.springframework.security.core.userdetails.User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return Optional.empty();
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                    user.get().getName(),
                    user.get().getPasswordHash(),
                    user.get().getGroups().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
            );

        return Optional.of(userDetails);

    }

    public UserDetails getUserDetails(User user) {
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getName(),
                        user.getPasswordHash(),
                        user.getGroups().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
                );

        return userDetails;

    }
}
