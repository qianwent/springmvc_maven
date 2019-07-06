package com.qwt.springmaven.service;

import com.qwt.springmaven.annotation.CustomService;
import org.springframework.cache.annotation.CacheEvict;

/**
 * Created by Wentao Qian on 2/3/2019.
 */
@CustomService
public class MyService2 {

    @CacheEvict(value = "userCache", key = "#p0")
    public String applyService(String caller) {
        System.out.println("evicting cache...");
        return caller;
    }

}
