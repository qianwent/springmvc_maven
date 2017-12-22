package com.qwt.springmaven.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This class is also used to replace applicationContext.xml where <mvc:annotation-driven/> is defined
 */
@EnableWebMvc
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
}
