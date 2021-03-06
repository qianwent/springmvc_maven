package com.qwt.springmaven.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

/**
 * Created by Wentao Qian on 12/22/2017.
 * The main AppConfig configuration class
 * doesn't do anything but hits Spring on where to look for its components through @ComponentScan annotation.
 * This class is used to replace applicationContext.xml where <context:component-scan/> is defined
 */
@Configuration
@ComponentScan(basePackages = "com.qwt.springmaven",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, value = {FWComponentScanFilter.class})//TODO: this is important to let Spring know to scan custom annotations
        })
//TODO: need to clean up this project, pom, comments...
@EnableAspectJAutoProxy//TODO: this is so important to make aspectj working. But is it the good place to put this annotation?
public class AppConfig {
}
