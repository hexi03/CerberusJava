package com.hexi.Cerberus.config;

import com.hexi.Cerberus.domain.access.BehavioredAclPermissionEvaluator;
import com.hexi.Cerberus.domain.access.BehavioredPermissionFactory;
import com.hexi.Cerberus.domain.access.BehavioredPermissionGrantingStrategy;
import com.hexi.Cerberus.infrastructure.security.AclSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@Configuration
// @EnableCaching
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfiguration {

    // method security config wired in aclPermissionEvaluator
    protected MethodSecurityExpressionHandler createExpressionHandler(AclPermissionEvaluator evaluator) {
        final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(evaluator);
        return expressionHandler;
    }

    // ======= ACL configurations =======

    /**
     * AclPermissionEvaluator needs a aclService
     * @return
     */
    @Bean
    public AclPermissionEvaluator aclPermissionEvaluator(AclService service) {
        final BehavioredAclPermissionEvaluator aclPermissionEvaluator = new BehavioredAclPermissionEvaluator(service);
        return aclPermissionEvaluator;
    }





    /**
     * Define JDBC ACL service
     * These 2 simple SQL queries are MySQL specific, so if need supporting multiple databases,
     * you might want to put these in configuration, not in code.
     * @return
     */
    @Bean
    public JdbcMutableAclService aclService(DataSource ds, LookupStrategy lookupStrategy, AclCache aclCache) {
        AclSupport.makeMigrations(ds);

        final JdbcMutableAclService service = new JdbcMutableAclService(ds, lookupStrategy, aclCache);
        // Those two line for MySQL only
        service.setClassIdentityQuery("SELECT nextval('acl_sid_id_seq');");
        service.setSidIdentityQuery("SELECT nextval('acl_class_id_seq');");
        service.setAclClassIdSupported(true);

        return service;
    }

    // lookup strategy
    @Bean
    public LookupStrategy lookupStrategy(DataSource ds, AclCache aclCache, AclAuthorizationStrategy authStrategy, PermissionGrantingStrategy grantingStrategy) {
        BasicLookupStrategy strategy = new BasicLookupStrategy(ds, aclCache, authStrategy, grantingStrategy);
        strategy.setAclClassIdSupported(true);
        return strategy;
    }

    // cache
//    @Bean
//    public AclCache aclCache() {
//        final EhCacheFactoryBean factoryBean = new EhCacheFactoryBean();
//        final EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
//        cacheManager.setAcceptExisting(true);
//        cacheManager.setCacheManagerName(CacheManager.getInstance().getName());
//        cacheManager.afterPropertiesSet();
//
//        factoryBean.setName("aclCache");
//        factoryBean.setCacheManager(cacheManager.getObject());
//        factoryBean.setMaxBytesLocalHeap("16M");
//        factoryBean.setMaxEntriesLocalHeap(0L);
//        factoryBean.afterPropertiesSet();
//        return new EhCacheBasedAclCache(factoryBean.getObject(), permissionGrantingStrategy(), aclAuthorizationStrategy());
//
//    }



    @Bean(name = { "defaultAclCache", "aclCache" })
    protected AclCache defaultAclCache(org.springframework.cache.CacheManager springCacheManager, AclAuthorizationStrategy authStrategy, PermissionGrantingStrategy grantingStrategy ) {
        org.springframework.cache.Cache cache =
                springCacheManager.getCache("acl_cache");
        return new SpringCacheBasedAclCache(cache,
                grantingStrategy,
                authStrategy);
    }

    // which deals with access administrative methods
    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ADMIN"));
    }

    // this allows us to actually customize the decision to grant a permission (or not) based on the ACL entry
    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new BehavioredPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public PermissionFactory permissionFactory() {
        return new BehavioredPermissionFactory();
    }


}
