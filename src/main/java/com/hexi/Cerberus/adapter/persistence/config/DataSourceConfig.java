package com.hexi.Cerberus.adapter.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
//@ConditionalOnProperty(
//        prefix = "params",
//        value = "persistence",
//        havingValue = "spring-data-jpa",
//        matchIfMissing = true
//)
//@ConfigurationProperties(prefix = "params.adapter.datasource")
public class DataSourceConfig{

//    @Autowired
//    Environment env;
    @Value( "${params.adapter.datasource.username}" )
    private volatile String username;
    @Value( "${params.adapter.datasource.password}" )
    private volatile String password;
    @Value( "${params.adapter.datasource.driverClassName}" )
    private String driverClassName;
    @Value( "${params.adapter.datasource.jdbcUrl}" )
    private String jdbcUrl;



    @Bean
    public DataSource dataSource() {
//        String username = env.getProperty("params.adapter.datasource.username");
//        String password = env.getProperty("params.adapter.datasource.password");
//        String driverClassName = env.getProperty("params.adapter.datasource.driverClassName");
//        String jdbcUrl = env.getProperty("params.adapter.datasource.jdbcUrl");

        System.err.println(username);
        System.err.println(password);

        HikariConfig conf = new HikariConfig();


        conf.setDriverClassName(driverClassName);
        conf.setJdbcUrl(jdbcUrl);
        conf.setUsername(username);
        conf.setPassword(password);

        return new HikariDataSource(conf);
    }

}
