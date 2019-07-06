package com.qwt.springmaven.service;

import com.qwt.springmaven.annotation.CustomService;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.constraints.NotNull;

/**
 * Created by Wentao Qian on 3/13/2018.
 */
@CustomService
public class MyService {

    //@NotNull在本身并不起任何作用，只是一个标识，类似于文档说明，表明这个地方“不应该是空”
    //https://stackoverflow.com/questions/34094039/using-notnull-annotation-in-method-argument
    //这里有详细说明
    @Cacheable(value = "userCache", condition = "#caller.length()==3", key = "#p0")
    public String applyService(@NotNull String caller) {
        System.out.println("calling method, not cache...");
        return caller;
    }

}
