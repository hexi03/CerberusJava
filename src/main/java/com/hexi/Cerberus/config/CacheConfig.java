package com.hexi.Cerberus.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;

//@EnableCaching
@Configuration
public class CacheConfig {

//    @Primary
//    @Bean("cacheManager")
//    public CompositeCacheManager cacheManager() {
//        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
//        compositeCacheManager.setFallbackToNoOpCache(true);
//        compositeCacheManager.setCacheManagers(Collections.singleton(ehCacheManager()));
//        return compositeCacheManager;
//    }
//
//    @Bean("ehCacheManager")
//    public EhCacheCacheManager ehCacheManager() {
//        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
//        ehCacheCacheManager.setCacheManager(getCustomCacheManager());
//        return ehCacheCacheManager;
//    }
//
//    private CacheManager getCustomCacheManager() {
//        CacheManager cacheManager = CacheManager.create(getEhCacheConfiguration());
//        cacheManager.setName("custom_eh_cache");
//        cacheManager.addCache(createCache("users"));
//        cacheManager.addCache(createCache("roles"));
//        return cacheManager;
//    }
//
//    private Cache createCache(String cacheName) {
//        CacheConfiguration cacheConfig = new CacheConfiguration(cacheName, 1000)
//                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
//                .eternal(false)
//                .timeToLiveSeconds(3600)
//                .timeToIdleSeconds(3600);
//        return new Cache(cacheConfig);
//    }
//
//    private net.sf.ehcache.config.Configuration getEhCacheConfiguration() {
//        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
//        DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
//        diskStoreConfiguration.setPath("java.io.tmpdir");
//        configuration.addDiskStore(diskStoreConfiguration);
//        return configuration;
//    }

}