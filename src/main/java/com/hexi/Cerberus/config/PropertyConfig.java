package com.hexi.Cerberus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.web.context.ConfigurableWebEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertyConfig {

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
//
//        return pspc;
//    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer createPropertyConfigurer(ConfigurableEnvironment environment)
//            throws IOException {
//        PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
//
//        ClassPathResource resource = new ClassPathResource(Paths.get("application.properties").toString());
//        result.setLocation(resource);
//        Properties properties;
//        try (InputStream is = resource.getInputStream()) {
//            properties = new Properties();
//            properties.load(is);
//
//            environment.getPropertySources().addFirst(new PropertiesPropertySource("customProperties", properties));
//        }
//
//        String username = properties.getProperty("params.adapter.datasource.username");
//        String password = environment.getProperty("params.adapter.datasource.password");
//        String driverClassName = environment.getProperty("params.adapter.datasource.driverClassName");
//        String jdbcUrl = environment.getProperty("params.adapter.datasource.jdbcUrl");
//
//        System.err.println(username);
//        System.err.println(password);
//        System.err.println(driverClassName);
//        System.err.println(jdbcUrl);
//
//
//        return result;
//    }
}

