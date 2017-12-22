package com.qwt.springmaven.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Wentao Qian on 12/22/2017.
 * The main AppConfig configuration class
 * doesn't do anything but hits Spring on where to look for its components through @ComponentScan annotation.
 * This class is used to replace applicationContext.xml where <context:component-scan/> is defined
 */
@Configuration
@ComponentScan(basePackages = "com.qwt.springmaven")
public class AppConfig {
}
