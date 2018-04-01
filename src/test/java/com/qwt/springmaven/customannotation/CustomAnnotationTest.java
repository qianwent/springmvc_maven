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
@WebAppConfiguration//�ٴ���ȷ��spring unit test�У���������������AppConfig��
// ���л�������Ϊ�����@EnableWebMvcҲ��Ҫ�������ʵ�ϲ���
// �鿴SampleServiceTest��������ӻ�ȡ������Ϣ
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
