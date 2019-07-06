package com.qwt.springmaven.controller;

import com.qwt.springmaven.service.MyService;
import com.qwt.springmaven.service.MyService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Wentao Qian on 4/1/2018.
 */
@Controller
public class AnnotationController {

    private MyService myService;

    private MyService2 myService2;

    @Autowired
    public AnnotationController(MyService myService, MyService2 myService2) {
        this.myService = myService;
        this.myService2 = myService2;
    }

    @RequestMapping(value = "/annotation", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String callMyService(String caller) {
        //if testing exception, then do not pass caller.
        return myService.applyService(caller);
        //TODO: need common web response, and global exception handler
    }

    @RequestMapping(value = "/annotation2", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String callMyService2(String caller) {
        return myService2.applyService(caller);
    }
}
