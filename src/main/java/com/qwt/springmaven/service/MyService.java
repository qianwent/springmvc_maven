package com.qwt.springmaven.service;

import com.qwt.springmaven.annotation.CustomService;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.constraints.NotNull;

/**
 * Created by Wentao Qian on 3/13/2018.
 */
@CustomService
public class MyService {

    //@NotNull�ڱ��������κ����ã�ֻ��һ����ʶ���������ĵ�˵������������ط�����Ӧ���ǿա�
    //https://stackoverflow.com/questions/34094039/using-notnull-annotation-in-method-argument
    //��������ϸ˵��
    @Cacheable(value = "userCache", condition = "#caller.length()==3", key = "#p0")
    public String applyService(@NotNull String caller) {
        System.out.println("calling method, not cache...");
        return caller;
    }

}
