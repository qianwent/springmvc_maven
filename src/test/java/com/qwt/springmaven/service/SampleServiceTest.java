package com.qwt.springmaven.service;

import com.qwt.springmaven.config.AppConfig;
import com.qwt.springmaven.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Wentao Qian on 1/11/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AppConfig.class)
//why need this annotation?
//https://stackoverflow.com/questions/21516683/java-lang-illegalargumentexception-a-servletcontext-is-required-to-configure-de
@WebAppConfiguration
public class SampleServiceTest {

    //this is one way to test spring cache
    @Autowired
    IService iService;
    //if we use @InjectMocks here, then the User objects created are different
//    @InjectMocks
//    SampleService iService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkUserCache() throws Exception {
        User user1 = iService.getUser(1);
        User user2 = iService.getUser(1);
        User user3 = iService.getUser(3);
        System.out.println(user1.getName());
        System.out.println(user2.getName());
        System.out.println(user3.getName());
        Assert.assertEquals(user1, user2);
        Assert.assertEquals(user1.getName(), user2.getName());
        Assert.assertNotEquals(user1, user3);
    }

}