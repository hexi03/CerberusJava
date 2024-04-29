package com.hexi.Cerberus;

import com.hexi.Cerberus.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class CerberusApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[] { CerberusApplication.class, AppConfig.class}, args);
    }

}
