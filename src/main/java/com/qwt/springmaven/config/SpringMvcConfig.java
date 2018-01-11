package com.qwt.springmaven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

/**
 * This class is also used to replace applicationContext.xml where <mvc:annotation-driven/> is defined
 */
@EnableWebMvc
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * To address IE issues with no-store, replace default cache parameters with one that
     * does not include no-store. See https://github.com/FortAwesome/Font-Awesome/issues/6454
     */
    @Bean
    public WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor interceptor = new WebContentInterceptor();
        CacheControl cacheControl = CacheControl.noCache().mustRevalidate().cachePrivate();
        interceptor.setCacheControl(cacheControl);
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webContentInterceptor());
    }

}
