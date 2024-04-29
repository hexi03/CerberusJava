package com.hexi.Cerberus.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hexi.Cerberus")
@PropertySource("classpath:application.properties")
@Slf4j
public class AppConfig {

//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setPrefix("classpath:/templates/");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML");
//        return resolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setTemplateResolver(templateResolver());
//        engine.addDialect(new LayoutDialect(new GroupingStrategy()));
//        return engine;
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        registry.viewResolver(resolver);
//    }


    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
                .getHandlerMethods();
        map.forEach((key, value) -> log.info("{} {}", key, value));
    }
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }

//    @Bean
//    WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> enableDefaultServlet() {
//        return (factory) -> factory.setRegisterDefaultServlet(true);
//    }

}
