package com.qwt.springmaven.controller;

import com.qwt.springmaven.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AnnotationController(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(value = "/annotation", method = RequestMethod.POST)
    @ResponseBody
    public String callMyService() {
        //TODO: for testing purpose, we use null value here. need to change to use request object
        String caller = null;
//        String caller = "test";
        return myService.applyService(caller);
    }

}
