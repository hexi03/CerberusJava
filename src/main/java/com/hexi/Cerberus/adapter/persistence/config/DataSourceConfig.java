package com.hexi.Cerberus.adapter.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "params.datasource")
public class DataSourceConfig extends HikariConfig {

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(this);
    }

}
