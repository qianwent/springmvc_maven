package com.qwt.springmaven.customannotation;

import com.qwt.springmaven.config.AppConfig;
import com.qwt.springmaven.service.MyService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Wentao Qian on 3/13/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AppConfig.class)
@WebAppConfiguration//再次明确在spring unit test中，如果引入了上面的AppConfig，
// 运行环境会认为里面的@EnableWebMvc也需要激活，而事实上不用
// 查看SampleServiceTest里面的链接获取更多信息
public class CustomAnnotationTest {

    @Autowired
    MyService myService;

    //to make AOP working, we need to use @EnableAspectJAutoProxy in config class, it's very important!
    @Test(expected = IllegalArgumentException.class)
    public void test() {
        String caller = null;
        myService.applyService(caller);
    }

}
