package com.qwt.springmaven.service;

import com.qwt.springmaven.annotation.CustomService;

import javax.validation.constraints.NotNull;

/**
 * Created by Wentao Qian on 3/13/2018.
 */
@CustomService
public class MyService {

    public String applyService(@NotNull String caller) {
        return caller;
    }

}
