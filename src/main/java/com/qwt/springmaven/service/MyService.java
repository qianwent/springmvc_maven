package com.qwt.springmaven.service;

import com.qwt.springmaven.annotation.CustomService;

import javax.validation.constraints.NotNull;

/**
 * Created by Wentao Qian on 3/13/2018.
 */
@CustomService
public class MyService {

    //@NotNull�ڱ��������κ����ã�ֻ��һ����ʶ���������ĵ�˵������������ط�����Ӧ���ǿա�
    //https://stackoverflow.com/questions/34094039/using-notnull-annotation-in-method-argument
    //��������ϸ˵��
    public String applyService(@NotNull String caller) {
        return caller;
    }

}
