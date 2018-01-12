package com.qwt.springmaven.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Wentao Qian on 1/12/2018.
 * Spring cache won't work if we don't have CacheManager
 */
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean(name = "sampleCacheManager")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("userCache");
    }
}
