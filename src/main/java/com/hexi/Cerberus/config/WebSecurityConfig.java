package com.hexi.Cerberus.config;

import com.hexi.Cerberus.application.access.service.UserService;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@Transactional
public class WebSecurityConfig implements WebMvcConfigurer {
    //private JwtRequestFilter jwtRequestFilter;


//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

//    @Autowired
//    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.err.println("SecurityFilterChain: Configuring");
        // Регистрация CorsFilter в ServletContext
        http.authorizeHttpRequests(rmr -> {
                    rmr.anyRequest().permitAll();
//                    rmr.requestMatchers("/secured").authenticated();
//                    rmr.requestMatchers("/info").authenticated();
//                    rmr.requestMatchers("/admin").hasRole("ADMIN");
//                    rmr.anyRequest().permitAll();
                });
        http.csrf(csrfmc -> csrfmc.disable());
        http
                .sessionManagement(smc -> {
                    smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })


                .exceptionHandling(ec -> ec.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.cors(cmr -> cmr.disable());
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        http.addFilterAt(new PromisquousCorsFilter(), CorsFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {


        if (!userRepository.findByEmail("user@abc.ru").isPresent()) {
            userService.createDefaultUser();

        }
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);


        return daoAuthenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.
//        registry.addMapping("/*")
//                .allowedOrigins("*")
//                .allowedMethods("*");
//    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedMethods("*");
//    }



}




