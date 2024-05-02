package com.hexi.Cerberus;

import com.hexi.Cerberus.config.AppConfig;
import com.hexi.Cerberus.config.WebSecurityConfig;
import jakarta.servlet.*;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.annotation.Configurations;
import org.springframework.boot.context.annotation.UserConfigurations;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.EnumSet;
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        // Optionally also set maxFileSize, maxRequestSize, fileSizeThreshold
        registration.setMultipartConfig(new MultipartConfigElement(""));
    }
}
//public class Initializer implements WebApplicationInitializer {
//
//    public static final String DEFAULT_FILTER_NAME = "springSecurityFilterChain";
//    @Override
//    public void onStartup(ServletContext servletContext)
//    {
//
//        System.err.println("onStartup!!!!!!!!!!!!!!!");
//        AnnotationConfigWebApplicationContext ctx
//                = getContext();
//
//
//        ctx.register(UserConfigurations.class);
//
//        // Sets ContextLoaderListener to servletContext
//        servletContext.addListener(new ContextLoaderListener(ctx));
//
//        // Passes servlet context to context instance
//        ctx.setServletContext(servletContext);
//
//
//        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy(DEFAULT_FILTER_NAME);
//
//        FilterRegistration.Dynamic registration = servletContext.addFilter(DEFAULT_FILTER_NAME, springSecurityFilterChain);
//        Assert.state(registration != null, () -> "Duplicate Filter registration for '" + DEFAULT_FILTER_NAME
//                + "'. Check to ensure the Filter is only configured once. ");
//        registration.setAsyncSupported(true);
//        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC, DispatcherType.FORWARD,
//                DispatcherType.INCLUDE);
//        registration.addMappingForUrlPatterns(dispatcherTypes, false, "/*");
//    }
//    private AnnotationConfigWebApplicationContext getContext() {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.register(com.hexi.Cerberus.config.AppConfig.class);
//        context.refresh();
//
//        return context;
//    }
//
//}