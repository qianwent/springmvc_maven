package com.qwt.springmaven.service;

import com.qwt.springmaven.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by Wentao Qian on 1/11/2018.
 */
@Component
public class SampleService implements IService {

    @Override
    @Cacheable(value = "userCache", cacheManager = "sampleCacheManager")
    public User getUser(int id) {
        //TODO need to get user infor from DAO
        System.out.println("first time entering into method");
        User user = new User();
        user.setId(id);
        user.setName("abc" + new Random().nextInt());
        return user;
    }
}
