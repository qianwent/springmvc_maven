package com.qwt.springmaven.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Wentao Qian on 3/13/2018.
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomService {
}
