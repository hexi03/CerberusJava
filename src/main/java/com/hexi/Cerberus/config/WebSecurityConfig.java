package com.hexi.Cerberus.config;

import com.hexi.Cerberus.application.access.service.UserService;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.infrastructure.service.JwtTokenUtils;
import jakarta.servlet.Filter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    PromisquousCorsFilter promisquousCorsFilter;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    private RequestAttributeSecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();


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
//        http.authorizeHttpRequests(rmr -> {
//                    rmr.requestMatchers("/api/report/**").authenticated();
//                    rmr.anyRequest().permitAll();
////                    rmr.requestMatchers("/info").authenticated();
////                    rmr.requestMatchers("/admin").hasRole("ADMIN");
////                    rmr.anyRequest().permitAll();
//                });
        http.csrf(csrfmc -> csrfmc.disable());
        http
                .sessionManagement(smc -> {
                    smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        http.exceptionHandling(ec -> ec.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.cors(cmr -> cmr.disable());
        http.httpBasic(httpBasic -> {
                httpBasic.disable();
                });
        http.logout(logout -> logout.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //http.addFilterAt(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        //http.addFilterBefore(jwtRequestFilter, AnonymousAuthenticationFilter.class);
        http.addFilterAfter(jwtRequestFilter(securityContextRepository), SecurityContextHolderFilter.class);
        http.addFilterAt(promisquousCorsFilter, CorsFilter.class);
        http.securityContext(sec -> sec.disable());
        //http.anonymous(anon -> anon.disable());
        return http.build();
    }

    private Filter jwtRequestFilter(SecurityContextRepository repository) {
        JwtRequestFilter filter = new JwtRequestFilter();
        filter.setSecurityContextRepository(repository);
        filter.setJwtTokenUtils(jwtTokenUtils);
        return filter;
    }

    @Bean
    @Transactional
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
    public AuthenticationManager authenticationManager() throws Exception {
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




