package com.hexi.Cerberus.application.access.service;

import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFactory userFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<org.springframework.security.core.userdetails.User> user = userGroupService.findByEmail(username); //email as username
        if (user.isEmpty()) new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        );

        return user.get();
    }


    public void createDefaultUser() {
        User user = userFactory.from("user", "user@abc.ru", "password");
        userRepository.save(user);
        //user.setRoles(List.of(roleService.getUserRole()));
    }

}