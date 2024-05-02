package com.hexi.Cerberus;

import com.hexi.Cerberus.config.AppConfig;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CerberusApplication implements ServletContextAware {


    private ServletContext servletContext;

    public static void main(String[] args) {
        SpringApplication.run(new Class[] { CerberusApplication.class, AppConfig.class}, args);
    }


    public void populateContext() throws ServletException {
        WebAppInitializer webAppInitializer = new WebAppInitializer();
        webAppInitializer.onStartup(servletContext);
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @Bean
    DispatcherServlet dispatcherServlet(WebApplicationContext context) throws ServletException {
        return new DispatcherServlet(context);
    }
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }

//    @Bean
//    public ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }

}
