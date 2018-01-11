package com.qwt.springmaven.service;

import com.qwt.springmaven.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by Wentao Qian on 1/11/2018.
 */
@Component
public class SampleService implements IService {

    @Override
    public User getUser() {
        //TODO need to get user infor from DAO
        User user = new User();
        user.setId(1);
        user.setName("abc");
        return user;
    }
}
